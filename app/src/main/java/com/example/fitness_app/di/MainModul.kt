package com.example.fitness_app.di

import android.app.Application
import androidx.room.Room
import androidx.transition.Visibility.Mode
import com.example.fitness_app.db.MainDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModul {
    @Provides
    @Singleton
    fun ProvideMainDB(app: Application):MainDb{
    return  Room.databaseBuilder(
        app,
        MainDb::class.java,
        "fitness.db"
    ).createFromAsset("db/fitness.db").build()
    }
}