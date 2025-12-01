package com.example.examenu2.data.model

import com.google.gson.annotations.SerializedName

data class Mensaje (

    val id: Int,
    @SerializedName("usuario_id") val usuarioId: Int,
    val contenido: String


)