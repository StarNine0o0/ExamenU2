package com.example.appmovil.api

import com.cursosant.chatexentar.data.model.LoginResponse
import com.cursosant.chatexentar.data.model.MensajeDto
import com.cursosant.chatexentar.data.model.SendMessageRequest
import com.cursosant.chatexentar.data.model.SendMessageResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // LOGIN
    @POST("login")
    suspend fun login(
        @Body body: Map<String, String> // Mapea los datos del cuerpo de la solicitud
    ): LoginResponse

    // OBTENER MENSAJES
    @GET("mensajes")
    suspend fun getMensajes(): List<MensajeDto>

    @POST("mensajes")//enviar mensjae
    suspend fun enviarMensaje(@Body body: SendMessageRequest): SendMessageResponse

}