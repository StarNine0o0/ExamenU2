package com.example.examenu2.data.viewmodel

// **********************************
// IMPORTS CORREGIDOS Y NECESARIOS
// **********************************
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenu2.data.repository.MensajeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.examenu2.data.model.Mensaje
import com.example.examenu2.data.model.Usuario // <-- Falta en tu código
import androidx.lifecycle.ViewModelProvider // <-- Falta en tu código
import java.lang.Exception
// **********************************


class MensajeViewModel(private val repository: MensajeRepository) : ViewModel() { // Usamos MensajeViewModel, como en tu código

    // 1. ESTADO DE AUTENTICACIÓN
    private val _currentUser = MutableStateFlow<Usuario?>(null)
    val currentUser: StateFlow<Usuario?> = _currentUser

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    // 2. ESTADO DEL CHAT
    // **********************************
    // Nota: Eliminamos la declaración duplicada de _mensajes
    // **********************************
    private val _mensajes = MutableStateFlow<List<Mensaje>>(emptyList())
    val mensajes: StateFlow<List<Mensaje>> = _mensajes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchMensajes()
    }

    // =========================================================================
    // FUNCIONES DE AUTENTICACIÓN
    // =========================================================================

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginError.value = null
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.usuario != null) {
                        _currentUser.value = loginResponse.usuario
                        fetchMensajes()
                        println("LOGIN: Usuario logueado: ${loginResponse.usuario.nombre}")
                    } else {
                        _loginError.value = "Login fallido o respuesta incompleta."
                    }
                } else {
                    _loginError.value = "Credenciales incorrectas o error HTTP: ${response.code()}"
                }

            }catch (e: Exception){
                _loginError.value = "Error de red al server: ${e.message}"
                println("LOGIN: ERROR: ${e.message}")
            }
        }
    }

    // **********************************
    // FUNCIÓN DE LOGOUT - NO ANIDADA
    // **********************************
    fun logout() {
        _currentUser.value = null
        _mensajes.value = emptyList()
    }

    // =========================================================================
    // FUNCIONES DE CHAT (LECTURA)
    // =========================================================================

    // **********************************
    // FUNCIÓN DE FETCH - NO ANIDADA
    // **********************************
    fun fetchMensajes() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val listaMensajes = repository.obtenerMensajes()
                _mensajes.value = listaMensajes
            }catch (e: Exception){
                println("CHAT_APP: ERROR al cargar mensajes: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // =========================================================================
    // FUNCIÓN DE CHAT (ESCRITURA)
    // =========================================================================

    fun sendMessage(contenido: String) { // Renombramos el parámetro a 'contenido' para ser más claro
        val currentUserId = _currentUser.value?.id ?: return
        if (contenido.isBlank()) return // Corregido: usa el parámetro 'contenido'

        // Mensaje temporal (UI optimista)
        val tempMensaje = Mensaje(id = -1, usuarioId = currentUserId, contenido = contenido)
        _mensajes.value = _mensajes.value + tempMensaje

        viewModelScope.launch {
            try {
                // Llama al repositorio con los datos correctos
                val response = repository.crearNuevoMensaje(currentUserId, contenido)
                if (response.isSuccessful) {
                    fetchMensajes() // Recargar para obtener el ID/fecha del servidor
                } else {
                    println("CHAT_APP: ERROR al enviar mensaje: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("CHAT_APP: ERROR fallo en la red del servidor: ${e.message}")
            }
        }
    }

    // =========================================================================
    // FUNCIÓN AUXILIAR
    // =========================================================================
    fun isCurrentUser(mensaje: Mensaje): Boolean {
        // Corregido: compara el ID del usuario logueado con el ID del usuario del mensaje
        return _currentUser.value?.id == mensaje.usuarioId
    }

    // **********************************
    // COMPANION OBJECT CORREGIDO
    // **********************************
    companion object {
        fun Factory(repository: MensajeRepository) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // Aquí deberías cambiar a MensajeViewModel
                return MensajeViewModel(repository) as T
            }
        }
    }
}

    // Nota: Aquí se implementaría la lógica de crear mensaje y otras acciones
