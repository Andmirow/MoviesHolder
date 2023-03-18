package com.example.moviesholder.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.moviesholder.data.FilmsRemoteMediator
import com.example.moviesholder.data.GetFilmsPagingSource
import com.example.moviesholder.data.GetRetrofitFilmsPagingSource
import com.example.moviesholder.data.RemoteRepositoryImpl
import com.example.moviesholder.data.room.database.AppDatabase
import com.example.moviesholder.data.room.database.FilmListDao
import com.example.moviesholder.data.room.database.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {


    @Provides
    fun provideAppContext(app: Application): Context {
        return app.applicationContext
    }


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        )
            .build()
    }


    @Provides
    fun provideFilmsDao(database: AppDatabase): FilmListDao {
        return database.filmListDao()
    }

    @Provides
    fun provideRemoteKeysDao(database: AppDatabase): RemoteKeysDao {
        return database.filmsKeysDao()
    }





    @Provides
    fun provideFilmsRepository(database: AppDatabase, remoteMediator: FilmsRemoteMediator, ps : GetFilmsPagingSource): RemoteRepositoryImpl{
        return RemoteRepositoryImpl(database,remoteMediator,ps)
    }






    private companion object {
        const val DB_NAME = "movies_holder.db"
    }







}