package com.example.moviesholder.data.rfrxok.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesholder.data.Repo
import com.example.moviesholder.data.film_object.movie.FilmListModel
import com.example.moviesholder.data.film_object.movie.film_model.FilmModel
import com.example.moviesholder.data.rfrxok.data.remote.FilmApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelQestion(application : Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    val filmList = ArrayList<FilmListModel>()

    private val _selected = MutableLiveData<FilmListModel>()
    val selected: LiveData<FilmListModel>
        get() = _selected

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchList(filmApi: FilmApi?){
        filmApi?.let {
            val test =  compositeDisposable.add(filmApi.getFilmList("54PEKD1-QV741T0-NHKSR4H-2JXE7A4","1-10","rating.kp","rating.kp","-1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //filmList.add(it)
                    _selected.postValue(it)
                }, {

                })
            )

            Log.i("MyResult", "finish onViewCreated")
        }

    }
}