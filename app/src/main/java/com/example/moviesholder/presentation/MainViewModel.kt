package com.example.moviesholder.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesholder.data.MapperFilm
import com.example.moviesholder.data.retrofit.FilmApi
import com.example.moviesholder.data.retrofit.film_model.FilmModelList
import com.example.moviesholder.data.room.ItemRepositoryImpl
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.MovieFilter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(application : Application) : AndroidViewModel(application){


    private val repository = ItemRepositoryImpl(application)

    private val compositeDisposable = CompositeDisposable()




    //private val _selected = MutableLiveData<FilmModelList>()
    private val _selected = MutableLiveData<List<Film>>()
    val selected: LiveData<List<Film>>
        get() = _selected



    fun saveFilm(film : Film){
        repository.addFilm(film)
    }



    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchList(filmApi: FilmApi?){
        if (MovieFilter.isPreserved){
            val filmList = repository.getListFilm()
            _selected.postValue(filmList)
        }else{
            filmApi?.let { filmApi ->
                val disposable =filmApi.getFilmList(MovieFilter.token,MovieFilter.search,MovieFilter.field,MovieFilter.sortedField,MovieFilter.sortedType,MovieFilter.page,MovieFilter.limit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val listDocToFilm = MapperFilm.mapListDocToFilm(it.docs)
                        _selected.postValue(listDocToFilm)
                    },{
                        Log.i("MyResult",it.toString())
                    })
            }
        }
    }





}