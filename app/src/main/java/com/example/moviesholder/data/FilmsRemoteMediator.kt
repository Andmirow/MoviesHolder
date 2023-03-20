package com.example.moviesholder.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.moviesholder.data.retrofit.FilmApi
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmsDb
import com.example.moviesholder.domain.MovieFilter
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FilmsRemoteMediator @Inject constructor(
    private val service: FilmApi,
    private val database: AppDatabase
) : RemoteMediator<Int, FilmsDb.FilmDbModel>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, FilmsDb.FilmDbModel>): MediatorResult {
//

        val page = when (loadType) {
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

        try {

            val response = service.getFilmListCor(
                        MovieFilter.token,
                        MovieFilter.search,
                        MovieFilter.field,
                        MovieFilter.sortedField,
                        MovieFilter.sortedType,
                        page.toString(),
                        MovieFilter.limit)

            val data = MapperFilm.mapFilmsRetroToFilmsDb(response)


            database.withTransaction {
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
            }

            return MediatorResult.Success(endOfPaginationReached = data.endOfPage)
        }
        catch (e: Exception) {
            return MediatorResult.Error(e)
    }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, FilmsDb.FilmDbModel>): FilmsDb.FilmRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.filmsKeysDao().remoteKeysByMovieId(repo.id_Retrofit)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, FilmsDb.FilmDbModel>): FilmsDb.FilmRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            database.filmsKeysDao().remoteKeysByMovieId(movie.id_Retrofit)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, FilmsDb.FilmDbModel>): FilmsDb.FilmRemoteKeys? {
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