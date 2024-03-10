package com.example.roomcompose.data.local.repositoryImpl

import com.example.roomcompose.data.local.repository.UserRepository
import com.example.roomcompose.data.mapper.toModel
import com.example.roomcompose.data.mapper.toUI
import com.example.roomcompose.domain.models.UserUI
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userRepository: UserRepository) {

    suspend fun getAllUsers(): List<UserUI> {
        return userRepository.getAllUsers().map { it.toUI() }
    }

    suspend fun insertUser(user: UserUI) {
        userRepository.insertUser(user.toModel())
    }

    suspend fun deleteUser(user: UserUI) {
        userRepository.deleteUser(user.toModel())
    }

}