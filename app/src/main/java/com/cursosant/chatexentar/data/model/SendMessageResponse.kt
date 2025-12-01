package com.cursosant.chatexentar.data.model

data class SendMessageResponse(
    val success: Boolean,
    val data: MensajeDto,
    val message: String
)