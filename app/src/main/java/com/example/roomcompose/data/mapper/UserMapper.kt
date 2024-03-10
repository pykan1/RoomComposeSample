package com.example.roomcompose.data.mapper

import com.example.roomcompose.data.local.models.UserModel
import com.example.roomcompose.domain.models.UserUI

fun UserModel.toUI(): UserUI {
    return UserUI(id = id, name = name, lastName = lastName, age = age)
}

fun UserUI.toModel(): UserModel {
    return UserModel(id = id, name = name, age = age, lastName = lastName)
}
