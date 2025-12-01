package com.cursosant.chatexentar.ui



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cursosant.chatexentar.data.model.MensajeDto
import com.cursosant.chatexentar.data.viewmodel.MainViewModel


@Composable
fun ChatScreen(vm: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Chat de ${vm.usuario?.nombre}")
            Button(onClick = { vm.cargarMensajes() }) { Text("Recargar") }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(vm.mensajes) { msg ->
                MensajeItem(msg, vm.usuario?.id)
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = vm.textoMensaje,
                onValueChange = { vm.textoMensaje = it },
                modifier = Modifier.weight(1f),
                label = { Text("Mensaje") }
            )
            Spacer(Modifier.width(4.dp))
            Button(
                onClick = { vm.enviarMensaje() },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("Enviar")
            }
        }
    }
}

@Composable
fun MensajeItem(msg: MensajeDto, miId: Int?) {
    val esMio = miId == msg.usuario_id
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalAlignment = if (esMio) Alignment.End else Alignment.Start
    ) {
        Text("Usuario ${msg.usuario_id}")
        Text(msg.contenido)
    }
}