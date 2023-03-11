package com.example.moviesholder.data

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmsDb
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException
import javax.inject.Inject


class GetFilmsPagingSource @Inject constructor(
    private val database: AppDatabase
    ) : PagingSource<Int, FilmsDb.FilmDbModel>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmsDb.FilmDbModel> {

//        val position = params.key ?: 1
//
//        return database.filmListDao().selectFavoriteFilms()
//            .subscribeOn(Schedulers.io())
//            .map {
//                toLoadResult(it, position) }
//            .onErrorReturn {LoadResult.Error(it)  }

        TODO()
    }


    private fun toLoadResult(data: List<FilmsDb.FilmDbModel>, position: Int): LoadResult<Int, FilmsDb.FilmDbModel> {
        return LoadResult.Page(
            data = data,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.size) null else position + 1
        )
    }



    override fun getRefreshKey(state: PagingState<Int, FilmsDb.FilmDbModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }
}