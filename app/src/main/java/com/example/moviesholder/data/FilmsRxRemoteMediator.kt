package com.example.moviesholder.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.example.moviesholder.data.retrofit.FilmApi
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmRemoteKeys
import com.example.moviesholder.data.room.database.FilmsDb
import com.example.moviesholder.domain.MovieFilter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FilmsRxRemoteMediator @Inject constructor(
    private val service: FilmApi,
    private val database: AppDatabase
) : RxRemoteMediator<Int, FilmsDb.FilmDbModel>() {

    override fun loadSingle(loadType: LoadType, state: PagingState<Int, FilmsDb.FilmDbModel>): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)

                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    service.getFilmList(
                        MovieFilter.token,
                        MovieFilter.search,
                        MovieFilter.field,
                        MovieFilter.sortedField,
                        MovieFilter.sortedType,
                        page.toString(),
                        MovieFilter.limit)
                        .map { MapperFilm.mapFilmsRetroToFilmsDb(it) }
                        .map { insertToDb(page, loadType, it) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.endOfPage) }
                        .onErrorReturn { MediatorResult.Error(it) }
                }

            }
            .onErrorReturn { MediatorResult.Error(it) }
    }


    @Suppress("DEPRECATION")
    private fun insertToDb(page: Int, loadType: LoadType, data: FilmsDb): FilmsDb {
        database.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                database.filmsKeysDao().clearRemoteKeys()
                database.filmListDao().clearMovies()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.endOfPage) null else page + 1
            val keys = data.movies.map {
                FilmsDb.FilmRemoteKeys(id = it.id_Retrofit, prevKey = prevKey, nextKey = nextKey)
            }
            database.filmsKeysDao().insertAll(keys)
            database.filmListDao().insertAll(data.movies)
            database.setTransactionSuccessful()

        } finally {
            database.endTransaction()
        }

        return data
    }


    private fun getRemoteKeyForLastItem(state: PagingState<Int, FilmsDb.FilmDbModel>): FilmsDb.FilmRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.filmsKeysDao().remoteKeysByMovieId(repo.id_Retrofit)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, FilmsDb.FilmDbModel>): FilmsDb.FilmRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            database.filmsKeysDao().remoteKeysByMovieId(movie.id_Retrofit)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, FilmsDb.FilmDbModel>): FilmsDb.FilmRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id_Retrofit?.let { id ->
                database.filmsKeysDao().remoteKeysByMovieId(id)
            }
        }
    }



    companion object {
        const val INVALID_PAGE = -1
    }
}