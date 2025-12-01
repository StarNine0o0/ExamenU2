package com.example.examenu2.data.viewmodel

import androidx.lifecycle.ViewModel
import com.example.examenu2.data.repository.MensajeRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.examenu2.data.model.Mensaje



class MensajeViewModel(private val repository: MensajeRepository) : ViewModel() {

    private val _mensajes = MutableStateFlow<List<Mensaje>>(emptyList())
    val mensajes: StateFlow<List<Mensaje>> = _mensajes

    init {
        obtenerMensajes()
    }

    fun obtenerMensajes() {
        viewModelScope.launch {
            try {
                val listaMensajes = repository.obtenerTodosLosMensajes()
                _mensajes.value = listaMensajes
                println("CHAT_APP: Mensajes cargados: ${listaMensajes.size}")
            } catch (e: Exception) {
                println("CHAT_APP: ERROR al cargar mensajes: ${e.message}")
            }
        }
    }

    // Nota: Aquí se implementaría la lógica de crear mensaje y otras acciones
}