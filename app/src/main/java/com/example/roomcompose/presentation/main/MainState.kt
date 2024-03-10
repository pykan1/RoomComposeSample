package com.example.roomcompose.presentation.main

import com.example.roomcompose.domain.models.UserUI

data class MainState(
    val users: List<UserUI>,
    val tempAddUserUI: UserUI?,
) {
    companion object {
        val InitState = MainState(
            users = emptyList(),
            tempAddUserUI = null
        )
    }
}