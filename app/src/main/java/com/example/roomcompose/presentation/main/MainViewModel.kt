package com.example.roomcompose.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomcompose.data.local.repositoryImpl.UserRepositoryImpl
import com.example.roomcompose.domain.models.UserUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepositoryImpl: UserRepositoryImpl) :
    ViewModel() {

    val state = MutableStateFlow(MainState.InitState)


    fun addUser() {
        viewModelScope.launch {
            if (state.value.tempAddUserUI == null) {
                state.update { it.copy(tempAddUserUI = UserUI.Default) }
            } else {
                state.value.tempAddUserUI?.let {
                    userRepositoryImpl.insertUser(it)
                }.apply {
                    state.update { it.copy(tempAddUserUI = null) }
                    getAllUsers()
                }
            }
        }
    }

    fun onDismissAddUser() {
        viewModelScope.launch {
            state.update { it.copy(tempAddUserUI = null) }
        }
    }

    fun changeLastName(lastName: String) {
        viewModelScope.launch {
            state.update { it.copy(tempAddUserUI = state.value.tempAddUserUI?.copy(lastName = lastName)) }
        }
    }

    fun changeName(name: String) {
        viewModelScope.launch {
            state.update { it.copy(tempAddUserUI = state.value.tempAddUserUI?.copy(name = name)) }
        }
    }

    fun changeAge(age: String) {
        viewModelScope.launch {
            state.update {
                it.copy(tempAddUserUI = state.value.tempAddUserUI?.copy(
                    age = age.filter { it.isDigit() }
                    .toIntOrNull() ?: 0))
            }
        }
    }

    fun getAllUsers() {
        viewModelScope.launch {
            userRepositoryImpl.getAllUsers().let { users ->
                state.update { it.copy(users = users) }
            }
        }
    }

    fun deleteUser(userUI: UserUI) {
        viewModelScope.launch {
            userRepositoryImpl.deleteUser(userUI).apply {
                getAllUsers()
            }
        }
    }

}