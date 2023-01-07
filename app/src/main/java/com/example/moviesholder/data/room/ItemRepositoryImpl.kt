package com.example.moviesholder.data.room

import android.app.Application
import com.example.moviesholder.data.MapperFilm
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmDbModel
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.FilmRepository
import io.reactivex.Single
import org.jetbrains.annotations.NotNull

class ItemRepositoryImpl(application: Application) : FilmRepository {

//    @Inject
//    lateinit var appDatabase : AppDatabase
//
//    private val shopListDao = appDatabase.filmListDao()

    private val shopListDao = AppDatabase.getInstance(application).filmListDao()

//    init {
//        DaggerFilmComponent.builder().build().inject(this)   // create().inject(this)
//    }


    override fun getListFilm(): Single<List<FilmDbModel>> {
        return shopListDao.getListFilm()
    }

    override fun deleteFilm(film: Film) : Single<Int>  {
        return shopListDao.deleteFilm(film.idRoom)
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

    @NotNull
    override fun addFilm(film: Film) : Single<Unit> {
        val filmChek = findFilmByIdRetrofit(film.idRetrofot)
         return if (filmChek != null) {
            shopListDao.addFilm(
                MapperFilm.mapFilmToFilmDbModel(
                    filmChek.copy(idRoom = filmChek.idRoom)
                )
            )
        }else{
            shopListDao.addFilm(MapperFilm.mapFilmToFilmDbModel(film))
        }

    }


}