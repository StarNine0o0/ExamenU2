package com.example.examenu2.data.api

import com.example.examenu2.data.model.Mensaje
import com.example.examenu2.data.model.Usuario
import com.example.examenu2.data.model.LoginResponse // Si usas un modelo para la respuesta de login
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // 1. RUTA: /api/login (Autenticación)
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse> // Asumo que LoginResponse incluye el token/usuario

    // 2. RUTA: /api/usuarios/buscar/{nombre} (Búsqueda de Usuario)
    @GET("usuarios/buscar/{nombre}")
    suspend fun buscarPorNombre(
        @Path("nombre") nombre: String
    ): List<Usuario>

    // 3. RUTA: /api/mensajes (Obtener todos los mensajes)
    // Para el chat, es mejor filtrar. Si devuelve todo, ajustaremos la lógica en el ViewModel.
    @GET("mensajes")
    suspend fun obtenerMensajes(): List<Mensaje>

    // 4. RUTA: /api/mensajes (Crear un nuevo mensaje)
    @FormUrlEncoded
    @POST("mensajes")
    suspend fun crearMensaje(
        @Field("usuario_id") usuarioId: Int,
        @Field("contenido") contenido: String
    ): Response<Mensaje> // Usamos Response<Mensaje> para obtener el objeto recién creado (con ID/fecha)

    // 5. RUTA: /api/mensajes/{mensaje} (Obtener un mensaje por ID)
    @GET("mensajes/{mensaje}")
    suspend fun obtenerMensajePorId(
        @Path("mensaje") mensajeId: Int
    ): Mensaje
}