package com.example.moviesholder.data

import android.util.Log
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

        Log.i("MyResult", "loadSingle${loadType.name}")
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                        Log.i("MyResult", "LoadType.REFRESH ${remoteKeys.toString()}")
                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")
                        Log.i("MyResult", "LoadType.PREPEND ${remoteKeys.prevKey}")
                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")
                        Log.i("MyResult", "LoadType.APPEND ${remoteKeys.toString()}")
                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if (page == INVALID_PAGE) {
                    Log.i("MyResult", "INVALID_PAGE ${page}")
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    Log.i("MyResult", "INVALID_PAGE_else ${page}")

                    service.getFilmList(
                        MovieFilter.token,
                        MovieFilter.search,
                        MovieFilter.field,
                        MovieFilter.sortedField,
                        MovieFilter.sortedType,
                        page.toString(),
                        MovieFilter.limit)
                        .map {
                            Log.i("MyResult", "service.getFilmList_map_1 $it")
                            MapperFilm.mapFilmsRetroToFilmsDb(it)
                        }
                        .map {
                            Log.i("MyResult", "service.getFilmList_map_2 $it")
                            insertToDb(page, loadType, it)
                        }
                        .map<MediatorResult> {
                            Log.i("MyResult", "service.getFilmList_map_3 ${it.endOfPage}")
                            MediatorResult.Success(endOfPaginationReached = it.endOfPage)
                        }
                        .onErrorReturn {
                            Log.i("MyResult", "onErrorReturn ${it}")
                            MediatorResult.Error(it)
                        }
                }

            }
            .onErrorReturn { MediatorResult.Error(it) }
    }


    @Suppress("DEPRECATION")
    private fun insertToDb(page: Int, loadType: LoadType, data: FilmsDb): FilmsDb {

        Log.i("MyResult", "insertToDb ${data.toString()}")

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
        Log.i("MyResult", "getRemoteKeyClosestToCurrentPosition ${state}")

        val res = state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id_Retrofit?.let { id ->
                database.filmsKeysDao().remoteKeysByMovieId(id)
            }


        }

        Log.i("MyResult", "getRemoteKeyClosestToCurrentPosition ${res}")
        return res


    }



    companion object {
        const val INVALID_PAGE = -1
    }
}