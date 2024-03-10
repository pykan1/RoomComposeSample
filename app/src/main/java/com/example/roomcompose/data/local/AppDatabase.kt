package com.example.roomcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomcompose.data.local.models.UserModel
import com.example.roomcompose.data.local.repository.UserRepository


@Database(entities = [UserModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserRepository

}