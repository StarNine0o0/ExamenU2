package com.cursosant.chatexentar.data.model


data class LoginResponse(
    val success: Boolean,
    val data: Usuario?,
    val message: String
)