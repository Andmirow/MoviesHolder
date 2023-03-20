package com.example.moviesholder.presentation

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import com.example.moviesholder.data.FilmsRxRemoteMediator
import com.example.moviesholder.data.GetFilmsPagingSource
import com.example.moviesholder.data.RemoteRepositoryImpl
import com.example.moviesholder.data.retrofit.FilmApi
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmsDb
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.FilmApp
import com.example.moviesholder.domain.FilmRepository
import com.example.moviesholder.domain.MovieFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import org.jetbrains.annotations.NotNull
import javax.inject.Inject
import javax.inject.Singleton


class MainViewModel (application : Application) : AndroidViewModel(application){


    private val appl = application
     var base = AppDatabase.getInstance(appl)
    lateinit var mediator : FilmsRxRemoteMediator
    lateinit var  repository: RemoteRepositoryImpl
    lateinit var localPagingSource : GetFilmsPagingSource


    private fun init(filmApi : FilmApi){
        mediator = FilmsRxRemoteMediator(filmApi,base)
        localPagingSource = GetFilmsPagingSource(base)
        repository= RemoteRepositoryImpl(base,mediator,localPagingSource)
    }



    private val _isPreserved = MutableLiveData(false)
    var isPreserved : Boolean?
        get() = _isPreserved.value
        set(value) {
            _isPreserved.value = value
        }



    @ExperimentalCoroutinesApi
    fun getFilms(filmApi : FilmApi): Flowable<PagingData<FilmsDb.FilmDbModel>> {
        init(filmApi)
        val flowablePagingData =  if (isPreserved == true){
            repository.getFavoriteFilms()
        }else{
            repository.getFilms()
        }
        flowablePagingData
            .map {
                    pagingData ->
                pagingData
                    .filter {
                        it.poster != null
                    }
            }
            .distinct()
            .cachedIn(viewModelScope)
        return flowablePagingData
    }



    fun saveFavoriteFilm(film : Film, filmApi : FilmApi) {
        repository.saveFavoriteFilm(film)
    }


    fun deleteFilm(film : Film, filmApi : FilmApi){
        repository.deleteFilm(film)
    }



}

