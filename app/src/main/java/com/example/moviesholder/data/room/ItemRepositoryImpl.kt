package com.example.moviesholder.data.room

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.FilmRepository

class ItemRepositoryImpl(application: Application) : FilmRepository {

    //    private val shopListDao = AppDatabase.getInstance(application).filmListDao()
//
//    override suspend fun addFilm(item: Film) {
//        shopListDao.addFilm(MapperItem.mapItemToDbModel(item))
//    }
//
//
//    override fun getList(): LiveData<List<Film>> {
//        return MediatorLiveData<List<Film>>().apply {
//            this.addSource(shopListDao.getListFilm()){
//                value = MapperItem.mapListDbItemToEmtity(it)
//            }
//        }
//    }
//
//
//    override suspend fun remiveItem(item: Item) {
//        shopListDao.deleteItem(item.id)
//    }
//
//    suspend fun remiveItem(itemId: Int) {
//        shopListDao.deleteItem(itemId)
//    }
//
//
//    override suspend fun setItem(item: Item) {
//        addItem(item)
//    }
//
//    override suspend fun findItemById(id: Int) : Item {
//        val itemDb = shopListDao.getItem(id)
//        return MapperItem.mapDbItemToEmtity(itemDb)
//    }
    override fun getList(): LiveData<List<Film>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFilm(film: Film) {
        TODO("Not yet implemented")
    }

    override suspend fun setFilm(film: Film) {
        TODO("Not yet implemented")
    }

    override suspend fun findFilmById(id: Int): Film {
        TODO("Not yet implemented")
    }

    override suspend fun addFilm(film: Film) {
        TODO("Not yet implemented")
    }


}