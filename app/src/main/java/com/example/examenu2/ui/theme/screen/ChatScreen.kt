package com.example.examenu2.ui.theme.screen



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp

import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.example.examenu2.data.model.Mensaje // Importa tu modelo
import com.example.examenu2.data.viewmodel.MensajeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: MensajeViewModel) {

    // Estados observados del ViewModel
    val mensajes by viewModel.mensajes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    var textoMensaje by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Scroll automático al final
    LaunchedEffect(mensajes.size) {
        if (mensajes.isNotEmpty()) {
            scope.launch {
                listState.animateScrollToItem(mensajes.size - 1)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                // Muestra el nombre del usuario logueado en la barra superior
                title = { Text("Chat Grupal (${currentUser?.nombre ?: "Usuario"})") },
                actions = {
                    // Botón para recargar mensajes del servidor
                    IconButton(onClick = { viewModel.fetchMensajes() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Recargar")
                    }
                    // Botón para cerrar sesión (logout)
                    IconButton(onClick = { viewModel.logout() }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión")
                    }
                }
            )
        },
        bottomBar = {
            // Barra para escribir y enviar mensajes
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = textoMensaje,
                    onValueChange = { textoMensaje = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Escribe un mensaje...") },
                    shape = RoundedCornerShape(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    // Al hacer clic, llama a la función de envío del ViewModel
                    onClick = {
                        viewModel.sendMessage(textoMensaje)
                        textoMensaje = "" // Limpia el campo después de enviar
                    },
                    modifier = Modifier.size(48.dp),
                    enabled = textoMensaje.isNotEmpty()
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Enviar", tint = Color.White)
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {

            // Lista de mensajes (LazyColumn)
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                state = listState,
                contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
            ) {
                items(mensajes) { mensaje ->
                    BurbujaMensaje(mensaje = mensaje, viewModel = viewModel)
                }
            }

            // Indicador de carga
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

// Componente para renderizar la burbuja del mensaje
@Composable
fun BurbujaMensaje(mensaje: Mensaje, viewModel: MensajeViewModel) {
    val esEmisor = viewModel.isCurrentUser(mensaje)

    val backgroundColor = if (esEmisor) Color(0xFF4CAF50) else Color(0xFFEEEEEE) // Verde vs Gris
    val contentColor = if (esEmisor) Color.White else Color.Black

    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalAlignment = if (esEmisor) Alignment.End else Alignment.Start
    ) {
        Card(
            modifier = Modifier.widthIn(max = 300.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,
                contentColor = contentColor
            )
        ) {
            Text(
                text = mensaje.contenido,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}