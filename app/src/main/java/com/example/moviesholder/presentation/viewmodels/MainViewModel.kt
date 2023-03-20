package com.example.moviesholder.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.moviesholder.data.FilmsRemoteMediator
import com.example.moviesholder.data.GetFilmsPagingSource
import com.example.moviesholder.data.RemoteRepositoryImpl
import com.example.moviesholder.data.retrofit.FilmApi
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmsDb
import com.example.moviesholder.domain.Film
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor( application : Application) : AndroidViewModel(application){


    private val appl = application
    lateinit var base : AppDatabase
    lateinit var mediator : FilmsRemoteMediator
    lateinit var  repository: RemoteRepositoryImpl
    lateinit var localPagingSource : GetFilmsPagingSource



    private fun init(filmApi : FilmApi){
        base = AppDatabase.getInstance(appl)
        mediator = FilmsRemoteMediator(filmApi,base)
        localPagingSource = GetFilmsPagingSource(base,filmApi)


        repository= RemoteRepositoryImpl(base,mediator,localPagingSource)
        //isPreserved = false
    }



    private val _isPreserved = MutableLiveData(false)//savedStateHandle.getLiveData(KEY_Preserved,false)
    var isPreserved: Boolean?
        get() = _isPreserved.value
        set(value) {
            _isPreserved.value = value
        }




    fun getFilms(filmApi : FilmApi) = _isPreserved.asFlow()
        .distinctUntilChanged()
        .flatMapLatest {
            getFilm(filmApi,it)
        }
        .cachedIn(viewModelScope)


    fun getFilm(filmApi : FilmApi,isPreserve : Boolean): Flow<PagingData<FilmsDb.FilmDbModel>> {
        init(filmApi)
        return if (isPreserve) {
            repository.getFavoriteFilms()
                .map { pagingData -> pagingData.filter { it.poster != null } }
        }else{
            repository.getFilms()
                .map { pagingData -> pagingData.filter { it.poster != null } }
            }
        }


    fun getFilmTest(filmApi : FilmApi): Flow<PagingData<FilmsDb.FilmDbModel>> {
        init(filmApi)
        val res = repository.getFavoriteFilms()
            .map { pagingData ->
                pagingData.filter { it.poster != null }
            }

        Log.i("MyFilm", "getFilmTest ${res.asLiveData()}")
        return res

    }







    suspend fun saveFavoriteFilm(film : Film, filmApi : FilmApi) {
        return repository.saveFavoriteFilm(film)

    }


    suspend fun deleteFilm(film : Film, filmApi : FilmApi){
        repository.deleteFilm(film)
    }


    private companion object {
        const val KEY_Preserved = "KEY_Preserved"
    }


    suspend fun refresh(){
        repository.refresh()
    }

}

