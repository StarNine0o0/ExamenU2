package com.example.appmovil.api


import com.cursosant.chatexentar.data.model.LoginResponse
import com.cursosant.chatexentar.data.model.MensajeDto
import com.cursosant.chatexentar.data.model.SendMessageResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body body: Map<String, String>): LoginResponse

    @GET("mensajes")
    suspend fun getMensajes(): List<MensajeDto>

    @POST("mensajes")
    suspend fun enviarMensaje(@Body body: Map<String, Any>): SendMessageResponse
}
