package com.example.roomcompose.data.local.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.roomcompose.data.local.models.UserModel


@Dao
interface UserRepository {

    @Insert
    suspend fun insertUser(userModel: UserModel)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<UserModel>

    @Delete
    suspend fun deleteUser(userModel: UserModel)
}