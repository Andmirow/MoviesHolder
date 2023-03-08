package com.example.moviesholder.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import com.example.moviesholder.data.RemoteRepositoryImpl
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmsDb
import com.example.moviesholder.domain.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FilmRepository
) : ViewModel(){







    fun getMovies(): Flowable<PagingData<FilmsDb.FilmDbModel>> {
        return repository
            .getMovies()
            .map { pagingData -> pagingData.filter { it.poster != null } }
            .cachedIn(viewModelScope)
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

