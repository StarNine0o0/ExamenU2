package com.example.examenu2.data.repository

import com.example.examenu2.data.Api.RetrofitClient
import com.example.examenu2.data.model.Mensaje
import com.example.examenu2.data.api.ApiService
import retrofit2.Response

class MensajeRepository(private val apiService: ApiService) {

    // Función que llama a la API para obtener todos los mensajes
    suspend fun obtenerTodosLosMensajes(): List<Mensaje> {
        return apiService.obtenerMensajes()
    }

    // Función que llama a la API para crear un nuevo mensaje
    suspend fun crearNuevoMensaje(usuarioId: Int, contenido: String): Response<Mensaje> {
        return apiService.crearMensaje(usuarioId, contenido)
    }

    // ... (Puedes agregar aquí buscarUsuario, login, etc.)
}

// Factoría para inyectar el repositorio en el ViewModel (como en el ejemplo de GitHub)
class MensajeRepositoryFactory {
    fun create(): MensajeRepository {
        return MensajeRepository(RetrofitClient.instance)
    }
}