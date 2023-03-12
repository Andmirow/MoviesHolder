package com.example.moviesholder.data

import android.app.Application
import androidx.paging.PagingData
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmsDb
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.FilmRepository
import io.reactivex.Flowable
import io.reactivex.Single

class ItemRepositoryImpl(application: Application) {//}: FilmRepository {

//    private val shopListDao = AppDatabase.getInstance(application).filmListDao()
//
//    override fun getListFilm(): Single<List<FilmsDb.FilmDbModel>> {
//        return shopListDao.getListFilm()
//    }
//
//    override fun deleteFilm(film: Film) : Single<Int>  {
//        return shopListDao.deleteFilm(film.idRoom)
//    }
//
//    override fun findFilmByIdRoom(id: Int): Film {
//        val filmDb = shopListDao.getFilmById(id)
//        return MapperFilm.mapFilmDbModelToFilm(filmDb)
//    }
//
//    override fun findFilmByIdRetrofit(id: Int): Film? {
//        val filmDb = shopListDao.getFilmByIdRetrofit(id)
//        if (filmDb != null){
//            return MapperFilm.mapFilmDbModelToFilm(filmDb)
//        }
//        return null
//    }
//
//    override fun addFilm(film: Film) : Single<Unit> {
//        val filmChek = findFilmByIdRetrofit(film.idRetrofit)
//         return if (filmChek != null) {
//            shopListDao.addFilm(
//                MapperFilm.mapFilmToFilmDbModel(
//                    filmChek.copy(idRoom = filmChek.idRoom)
//                )
//            )
//        }else{
//            shopListDao.addFilm(MapperFilm.mapFilmToFilmDbModel(film))
//        }
//    }
//
//    override fun getMovies(): Flowable<PagingData<FilmsDb.FilmDbModel>> {
//        TODO("Not yet implemented")
//    }
}