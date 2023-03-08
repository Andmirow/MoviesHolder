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
import dagger.assisted.AssistedInject
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class RemoteRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val remoteMediator: FilmsRxRemoteMediator
    ) : FilmRepository {


    override fun getMovies(): Flowable<PagingData<FilmsDb.FilmDbModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.filmListDao().selectAll() }
        ).flowable
    }

    override fun getListFilm(): Single<List<FilmsDb.FilmDbModel>> {
        TODO("Not yet implemented")
    }

    override fun deleteFilm(film: Film): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun findFilmByIdRoom(id: Int): Film {
        TODO("Not yet implemented")
    }

    override fun findFilmByIdRetrofit(id: Int): Film? {
        TODO("Not yet implemented")
    }

    override fun addFilm(film: Film): Single<Unit> {
        TODO("Not yet implemented")
    }
}