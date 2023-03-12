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

        val position = params.key ?: 1
        return try {
            database.filmListDao().selectFavoriteFilms().run {
                LoadResult.Page(
                    data = this,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (position == this.size) null else position + 1
                )
            }
        }catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FilmsDb.FilmDbModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }
}