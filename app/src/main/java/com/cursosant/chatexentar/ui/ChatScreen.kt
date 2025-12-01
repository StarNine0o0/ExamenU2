package com.cursosant.chatexentar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cursosant.chatexentar.data.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    vm: MainViewModel,
    onLogout: () -> Unit = {}
) {

    // Cargar mensajes al entrar
    LaunchedEffect(true) {
        vm.cargarMensajes()
    }

    Column(Modifier.fillMaxSize()) {

        // Barra superior
        TopAppBar(
            title = { Text("Chat de ${vm.usuario?.nombre}")
            }
        )

        //  Error en pantalla
        vm.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(8.dp)
            )
        }

        //  Lista de mensajes
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = false
        ) {

            items(vm.mensajes) { msg ->

                val isMine = msg.usuario_id == vm.usuario?.id

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
                ) {

                    // Nombre
                    Text(
                        text = if (isMine) "Tú" else "Usuario ${msg.usuario_id}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )

                    // Burbuja del mensaje
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (isMine) Color(0xFF0084FF) else Color(0xFF555555),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Text(
                            text = msg.contenido,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(4.dp))

        //  Caja de texto y botón Enviar
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            OutlinedTextField(
                value = vm.textoMensaje,
                onValueChange = { vm.textoMensaje = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe un mensaje...") }
            )

            Spacer(Modifier.width(8.dp))

            Button(
                onClick = { vm.enviarMensaje() }
            ) {
                Text("Enviar")
            }
        }
    }
}
