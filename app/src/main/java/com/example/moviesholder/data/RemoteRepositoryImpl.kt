package com.example.moviesholder.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmsDb
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.FilmRepository
import io.reactivex.Flowable
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

    var pagingConfig : PagingConfig

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



//        TODO("Not yet implemented")
    }


    fun findFilmByIdRetrofit(id: Int): FilmsDb.FilmDbModel? {
        return database.filmListDao().getFilmByIdRetrofit(id)
    }


    fun saveFilm(film: Film): Single<Unit>? {

//        val e = findFilmByIdRetrofit(film.idRetrofit)
//        Log.i("MyFilm", e.toString())
//        if (e == null){
            val copy = film.copy(idRoom = 0,  isFavorite = true)
            val favoriteFilmDb = MapperFilm.mapFilmToFilmDbModel(copy)
            return database.filmListDao().saveFavoriteFilm(favoriteFilmDb)
//        }
//        return null
    }


    override fun saveFavoriteFilm(film: Film) {
        saveFilm(film)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe()
    }

    override fun deleteFilm(film: Film) {
        database.filmListDao().deleteFilm(film.idRoom)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

}