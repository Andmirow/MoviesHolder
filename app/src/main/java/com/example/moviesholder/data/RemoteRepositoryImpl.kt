package com.example.moviesholder.data

import androidx.paging.*
import androidx.paging.rxjava2.flowable
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmsDb
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.FilmRepository
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class RemoteRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val remoteMediator: FilmsRemoteMediator,
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

    override fun getFilms(): Flow<PagingData<FilmsDb.FilmDbModel>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.filmListDao().selectAll() }
        ).flow
    }


    override fun getFavoriteFilms(): Flow<PagingData<FilmsDb.FilmDbModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { getFilmsPagingSource }
        ).flow
    }


    override suspend fun refresh() {
        database.filmListDao().clearMovies()
    }


    override suspend fun saveFavoriteFilm(film: Film) {
        val copy = film.copy(idRoom = 0, isFavorite = true)
        val favoriteFilmDb = MapperFilm.mapFilmToFilmDbModel(copy)
        database.filmListDao().saveFavoriteFilm(favoriteFilmDb)
    }


    override suspend fun deleteFilm(film: Film) {
        database.filmListDao().deleteFilm(film.idRoom)
    }




}