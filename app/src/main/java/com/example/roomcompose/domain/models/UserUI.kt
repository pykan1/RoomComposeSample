package com.example.roomcompose.domain.models

data class UserUI (
    val id: Long,
    val name: String,
    val lastName: String,
    val age: Int,
) {
    companion object {
        val Default = UserUI(0, "", "", 0)
    }
}