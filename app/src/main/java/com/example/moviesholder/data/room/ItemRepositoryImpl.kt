package com.example.moviesholder.data.room

import android.app.Application
import com.example.moviesholder.data.MapperFilm
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.FilmRepository

class ItemRepositoryImpl(application: Application) : FilmRepository {

//    @Inject
//    lateinit var appDatabase : AppDatabase
//
//    private val shopListDao = appDatabase.filmListDao()

    private val shopListDao = AppDatabase.getInstance(application).filmListDao()

//    init {
//        DaggerFilmComponent.builder().build().inject(this)   // create().inject(this)
//    }


    override fun getListFilm(): List<Film> {
        val listFilm = shopListDao.getListFilm()
        return MapperFilm.mapListDbItemToEmtity(listFilm)
    }

    override fun deleteFilm(film: Film) {
        shopListDao.deleteFilm(film.idRoom)
    }

    override fun findFilmByIdRoom(id: Int): Film {
        val filmDb = shopListDao.getFilmById(id)
        return MapperFilm.mapFilmDbModelToFilm(filmDb)
    }

    override fun findFilmByIdRetrofit(id: Int): Film? {
        val filmDb = shopListDao.getFilmByIdRetrofit(id)
        if (filmDb != null){
            return MapperFilm.mapFilmDbModelToFilm(filmDb)
        }
        return null
    }


    override fun addFilm(film: Film) {
        val filmChek = findFilmByIdRetrofit(film.idRetrofot)

        if (filmChek != null) {
            shopListDao.addFilm(
                MapperFilm.mapFilmToFilmDbModel(
                    filmChek.copy(idRoom = filmChek.idRoom)
                )
            )
        }else{
            shopListDao.addFilm(
                MapperFilm.mapFilmToFilmDbModel(film)
            )
        }



    }


}