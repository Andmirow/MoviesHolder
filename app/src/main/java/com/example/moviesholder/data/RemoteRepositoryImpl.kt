package com.example.moviesholder.data

import android.content.Context
import androidx.paging.*
import androidx.paging.rxjava2.flowable
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmsDb
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.FilmRepository
import dagger.assisted.AssistedInject
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class RemoteRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val remoteMediator: FilmsRxRemoteMediator,
    private val getFilmsPagingSource : GetFilmsPagingSource
    ) : FilmRepository {

    lateinit var pagingConfig : PagingConfig

    init {
        pagingConfig = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 60,
            prefetchDistance = 5,
            initialLoadSize = 40)
    }




    override fun getFilms(): Flowable<PagingData<FilmsDb.FilmDbModel>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.filmListDao().selectAll() }
        ).flowable
    }


    override fun getFavoriteFilms(): Flowable<PagingData<FilmsDb.FilmDbModel>> {
        return Pager(
            config = pagingConfig,
            //remoteMediator = remoteMediator,
            pagingSourceFactory = { getFilmsPagingSource }
        ).flowable
    }



    @Suppress("DEPRECATION")
    override fun refresh() {

        database.beginTransaction()
        try {
            database.filmListDao().clearMovies()
            database.setTransactionSuccessful()
        } finally {
            database.endTransaction()
        }



//        database.filmListDao().clearMovies()
//            .subscribeOn(Schedulers.io())
//            .map {
//                toLoadResult(it, position) }
//            .onErrorReturn { PagingSource.LoadResult.Error(it)  }
//
//
//        TODO("Not yet implemented")
    }


    override fun saveFavoriteFilm(film: Film) {

        val copy = film.copy(idRoom = 0,  isFavorite = true)
        val favoriteFilmDb = MapperFilm.mapFilmToFilmDbModel(copy)

        database.filmListDao().saveFavoriteFilm(favoriteFilmDb)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }







    override fun deleteFilm(film: Film) {
        database.filmListDao().deleteFilm(film.idRoom)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }




}