package com.example.moviesholder.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import com.example.moviesholder.data.RemoteRepositoryImpl
import com.example.moviesholder.data.room.database.FilmsDb
import com.example.moviesholder.domain.FilmRepository
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class RxViewModel @Inject constructor(private val repository: RemoteRepositoryImpl) : ViewModel(){



    fun getFilms(): Flowable<PagingData<FilmsDb.FilmDbModel>> {
        return repository
            .getFilms()
            .map { pagingData -> pagingData.filter { it.poster != null } }
            .cachedIn(viewModelScope)
    }








}