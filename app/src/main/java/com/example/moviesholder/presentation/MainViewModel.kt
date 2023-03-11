package com.example.moviesholder.presentation

import android.app.Application
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
import com.example.moviesholder.di.DaggerFilmComponent
import com.example.moviesholder.domain.Film
import com.example.moviesholder.domain.FilmApp
import com.example.moviesholder.domain.FilmRepository
import com.example.moviesholder.domain.MovieFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.jetbrains.annotations.NotNull
import javax.inject.Inject
import javax.inject.Singleton


class MainViewModel (application : Application) : AndroidViewModel(application){


    private val appl = application
    lateinit var base : AppDatabase
    lateinit var mediator : FilmsRxRemoteMediator
    lateinit var  repository: RemoteRepositoryImpl
    lateinit var localPagingSource : GetFilmsPagingSource


    private fun init(filmApi : FilmApi){
        base = AppDatabase.getInstance(appl)
        mediator = FilmsRxRemoteMediator(filmApi,base)
        localPagingSource = GetFilmsPagingSource(base)
        repository= RemoteRepositoryImpl(base,mediator,localPagingSource)
    }



//    private val _selected = MutableLiveData<List<FilmsDb.FilmDbModel>>()
//    val selected : LiveData<List<FilmsDb.FilmDbModel>>
//        get() = _selected



    @ExperimentalCoroutinesApi
    fun getFilms(filmApi : FilmApi): Flowable<PagingData<FilmsDb.FilmDbModel>> {



        init(filmApi)

        if (MovieFilter.isPreserved){
            return repository.getFavoriteFilms()
                .map { pagingData -> pagingData.filter { it.poster != null } }
                .cachedIn(viewModelScope)
        }else{
            return repository
                .getFilms()
                .map { pagingData -> pagingData.filter { it.poster != null } }
                .cachedIn(viewModelScope)
        }

    }


    fun getfavoriteFilms(filmApi : FilmApi): Flowable<PagingData<FilmsDb.FilmDbModel>> {

        init(filmApi)

        return repository.getFavoriteFilms()
            .map { pagingData -> pagingData.filter { it.poster != null } }
            .cachedIn(viewModelScope)
    }






    fun saveFavoriteFilm(film : Film, filmApi : FilmApi) {

        init(filmApi)
        return repository.saveFavoriteFilm(film)

    }


    fun deleteFilm(film : Film, filmApi : FilmApi){
        init(filmApi)

        repository.deleteFilm(film)
    }


    fun refresh(){
        repository.refresh()
        //getFilms()
    }





}//(application : Application) : AndroidViewModel(application){

//
//
//    private val appl = application
//    private val repository = ItemRepositoryImpl(application)
//
//    @Inject
//    lateinit var compositeDisposable : CompositeDisposable
//
//
//    init {
//        DaggerFilmComponent.builder().build().inject(this)
//    }
//
//
//    private val _selected = MutableLiveData<List<Film>>()
//    val selected: LiveData<List<Film>>
//        get() = _selected
//
//
//    @NotNull
//    fun saveFilm(film : Film){
//        val disposable = repository.addFilm(film)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Toast.makeText(appl, "фильм сохраннен в вашу коллекцию", Toast.LENGTH_LONG).show()
//            },{
//                Log.i("MyResult",it.toString())
//            })
//    }
//
//    fun deleteFilm(film : Film){
//        val disposable = repository.deleteFilm(film)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Toast.makeText(appl, "фильм удален из вашей коллекции", Toast.LENGTH_LONG).show()
//            },{
//                Log.i("MyResult",it.toString())
//            })
//        val filmList = selected.value
//        val mulableFilmList= filmList?.toMutableList()
//        mulableFilmList?.remove(film)
//        _selected.postValue(mulableFilmList?.toList())
//    }
//
//    override fun onCleared() {
//        compositeDisposable.dispose()
//        super.onCleared()
//    }
//
//    fun fetchList(filmApi: FilmApi?){
//        if (MovieFilter.isPreserved){
//            val disposable = repository.getListFilm()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    val listDbMoldelToFilm = MapperFilm.mapListDbItemToEmtity(it)
//                    _selected.postValue(listDbMoldelToFilm)
//                },{
//                    Log.i("MyResult",it.toString())
//                })
//        }else{
//            filmApi?.let { filmApi ->
//                val disposable =filmApi.getFilmList(MovieFilter.token,MovieFilter.search,MovieFilter.field,MovieFilter.sortedField,MovieFilter.sortedType,MovieFilter.page,MovieFilter.limit)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({
//                        val listDocToFilm = MapperFilm.mapListDocToFilm(it.docs, it.page)
//                        _selected.postValue(listDocToFilm)
//                    },{
//                        Log.i("MyResult",it.toString())
//                    })
//            }
//        }
//    }

//}

