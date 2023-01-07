package com.example.moviesholder.presentation

import android.app.Application
import android.util.Log
import android.widget.Toast
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
import org.jetbrains.annotations.NotNull

class MainViewModel(application : Application) : AndroidViewModel(application){


    private val appl = application
    private val repository = ItemRepositoryImpl(application)
    private val compositeDisposable = CompositeDisposable()



    private val _selected = MutableLiveData<List<Film>>()
    val selected: LiveData<List<Film>>
        get() = _selected


    @NotNull
    fun saveFilm(film : Film){
        val disposable = repository.addFilm(film)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(appl, "фильм сохраннен в вашу коллекцию", Toast.LENGTH_LONG).show()
            },{
                Log.i("MyResult",it.toString())
            })
    }

    fun deleteFilm(film : Film){

        val disposable = repository.deleteFilm(film)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(appl, "фильм удален из вашей коллекции", Toast.LENGTH_LONG).show()
            },{
                Log.i("MyResult",it.toString())
            })

        val FilmList = selected.value
        val mulableFilmList= FilmList?.toMutableList()
        mulableFilmList?.remove(film)
        _selected.postValue(mulableFilmList?.toList())
    }



    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchList(filmApi: FilmApi?){
        if (MovieFilter.isPreserved){
            val disposable = repository.getListFilm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val listDbMoldelToFilm = MapperFilm.mapListDbItemToEmtity(it)
                    _selected.postValue(listDbMoldelToFilm)
                },{
                    Log.i("MyResult",it.toString())
                })

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