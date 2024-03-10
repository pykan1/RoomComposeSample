package com.example.roomcompose.di

import android.content.Context
import androidx.room.Room
import com.example.roomcompose.data.local.AppDatabase
import com.example.roomcompose.data.local.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun getUserRepository(@ApplicationContext appContext: Context): UserRepository {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "user").build().userDao()
    }

}