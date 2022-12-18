package com.example.moviesholder.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesholder.data.retrofit.FilmApi
import com.example.moviesholder.data.retrofit.film_model.FilmModelList
import com.example.moviesholder.domain.MovieFilter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel(application : Application) : AndroidViewModel(application){


    //@Inject lateinit var retrofit : FilmApi


    private val compositeDisposable = CompositeDisposable()


    private val _selected = MutableLiveData<FilmModelList>()
    val selected: LiveData<FilmModelList>
        get() = _selected


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchList(filmApi: FilmApi?){
        Log.i("MyResult","fetchList")
        filmApi?.let { filmApi ->
            val disposable =filmApi.getFilmList(MovieFilter.token,MovieFilter.search,MovieFilter.field,MovieFilter.sortedField,MovieFilter.sortedType,MovieFilter.page,MovieFilter.limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("TAG", it.docs.size.toString())
                    _selected.postValue(it)
                    Log.i("MyResult","good")
                },{
                    Log.i("MyResult",it.toString())
                })
        }

    }





}