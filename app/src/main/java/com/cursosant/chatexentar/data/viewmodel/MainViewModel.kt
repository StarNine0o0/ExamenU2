package com.cursosant.chatexentar.data.viewmodel


    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.setValue
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.cursosant.chatexentar.data.model.LoginResponse
    import com.cursosant.chatexentar.data.model.MensajeDto
    import com.cursosant.chatexentar.data.model.Usuario
    import com.example.appmovil.api.RetrofitClient
    import kotlinx.coroutines.launch

    class MainViewModel : ViewModel() {

        var email by mutableStateOf("")
        var password by mutableStateOf("")
        var error by mutableStateOf<String?>(null)
        var isLoading by mutableStateOf(false)
        var usuario by mutableStateOf<Usuario?>(null)
        var mensajes by mutableStateOf<List<MensajeDto>>(emptyList())
        var textoMensaje by mutableStateOf("")

        fun login() {
            isLoading = true
            error = null
            viewModelScope.launch {
                try {
                    val res: LoginResponse = RetrofitClient.api.login(
                        mapOf(
                            "email" to email,
                            "password" to password
                        )
                    )
                    if (res.success && res.data != null) {
                        usuario = res.data
                        isLoading = false
                        cargarMensajes()
                    } else {
                        error = res.message
                        isLoading = false
                    }
                } catch (e: Exception) {
                    error = "Error de conexión"
                    isLoading = false
                }
            }
        }

        fun cargarMensajes() {
            viewModelScope.launch {
                try {
                    mensajes = RetrofitClient.api.getMensajes()
                } catch (_: Exception) {
                    error = "No se pudieron cargar los mensajes"
                }
            }
        }

        fun enviarMensaje() {
            val userId = usuario?.id ?: return
            val contenido = textoMensaje.trim()
            if (contenido.isEmpty()) return
            viewModelScope.launch {
                try {
                    RetrofitClient.api.enviarMensaje(
                        mapOf(
                            "usuario_id" to userId,
                            "contenido" to contenido
                        )
                    )
                    textoMensaje = ""
                    cargarMensajes()
                } catch (_: Exception) {
                    error = "No se pudo enviar"
                }
            }
        }
    }


