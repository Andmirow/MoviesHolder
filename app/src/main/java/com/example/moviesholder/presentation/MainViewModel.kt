package com.example.moviesholder.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesholder.data.retrofit.FilmApi
import com.example.moviesholder.data.retrofit.film_object.movie.FilmListModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainViewModel(application : Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()


    private val _selected = MutableLiveData<FilmListModel>()
    val selected: LiveData<FilmListModel>
        get() = _selected


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchList(filmApi: FilmApi?){
        Log.i("MyResult","fetchList")
        filmApi?.let { filmApi ->
            val disposable =filmApi.getFilmList("54PEKD1-QV741T0-NHKSR4H-2JXE7A4","1-10","rating.kp","rating.kp","-1","1","5")
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