package com.cursosant.chatexentar.data.model

data class MensajeDto(
    val id: Int,
    val usuario_id: Int,
    val contenido: String,
    val created_at: String,
    val updated_at: String
)