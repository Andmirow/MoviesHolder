package com.example.moviesholder.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.example.moviesholder.data.retrofit.FilmApi
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmDbModel
import com.example.moviesholder.data.room.database.FilmRemoteKeys
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class FilmsRxRemoteMediator(
    private val service: FilmApi,
    private val database: AppDatabase
) : RxRemoteMediator<Int, FilmDbModel>() {

    override fun loadSingle(loadType: LoadType, state: PagingState<Int, FilmDbModel>): Single<MediatorResult> {
        TODO()
//        return Single.just(loadType)
//            .subscribeOn(Schedulers.io())
//            .map {
//                when (it) {
//                    LoadType.REFRESH -> {
//                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//
//                        remoteKeys?.nextKey?.minus(1) ?: 1
//                    }
//                    LoadType.PREPEND -> {
//                        val remoteKeys = getRemoteKeyForFirstItem(state)
//                            ?: throw InvalidObjectException("Result is empty")
//
//                        remoteKeys.prevKey ?: INVALID_PAGE
//                    }
//                    LoadType.APPEND -> {
//                        val remoteKeys = getRemoteKeyForLastItem(state)
//                            ?: throw InvalidObjectException("Result is empty")
//
//                        remoteKeys.nextKey ?: INVALID_PAGE
//                    }
//                }
//            }
//            .flatMap { page ->
//                if (page == INVALID_PAGE) {
//                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
//                } else {
//                    service.popularMovieRx(
//                        apiKey = apiKey,
//                        page = page,
//                        language = locale.language)
//                        .map { mapper.transform(it, locale) }
//                        .map { insertToDb(page, loadType, it) }
//                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.endOfPage) }
//                        .onErrorReturn { MediatorResult.Error(it) }
//                }
//
//            }
//            .onErrorReturn { MediatorResult.Error(it) }
    }


//    @Suppress("DEPRECATION")
//    private fun insertToDb(page: Int, loadType: LoadType, data: Movies): Movies {
//        database.beginTransaction()
//
//        try {
//            if (loadType == LoadType.REFRESH) {
//                database.movieRemoteKeysRxDao().clearRemoteKeys()
//                database.moviesRxDao().clearMovies()
//            }
//
//            val prevKey = if (page == 1) null else page - 1
//            val nextKey = if (data.endOfPage) null else page + 1
//            val keys = data.movies.map {
//                Movies.MovieRemoteKeys(movieId = it.movieId, prevKey = prevKey, nextKey = nextKey)
//            }
//            database.movieRemoteKeysRxDao().insertAll(keys)
//            database.moviesRxDao().insertAll(data.movies)
//            database.setTransactionSuccessful()
//
//        } finally {
//            database.endTransaction()
//        }
//
//        return data
//    }


    private fun getRemoteKeyForLastItem(state: PagingState<Int, FilmDbModel>): FilmRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.filmsKeysDao().remoteKeysByMovieId(repo.id_Retrofit)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, FilmDbModel>): FilmRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            database.filmsKeysDao().remoteKeysByMovieId(movie.id_Retrofit)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, FilmDbModel>): FilmRemoteKeys? {
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