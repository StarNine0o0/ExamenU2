package com.cursosant.chatexentar.ui




import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.cursosant.chatexentar.data.viewmodel.MainViewModel

@Composable
fun LoginScreen(vm: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("LOGIN")

        OutlinedTextField(
            value = vm.email,
            onValueChange = { vm.email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = vm.password,
            onValueChange = { vm.password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        vm.error?.let { Text(it, color = Color.Red) }

        Spacer(Modifier.height(10.dp))

        Button(onClick = { vm.login() }) {
            Text("Entrar")
        }
    }
}