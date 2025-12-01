package com.example.examenu2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examenu2.data.Api.RetrofitClient
import com.example.examenu2.data.api.RetrofitClient
import com.example.examenu2.data.repository.MensajeRepository
import com.example.examenu2.data.viewmodel.MensajeViewModel
import com.example.examenu2.ui.screen.ChatScreen
import com.example.examenu2.ui.screen.LoginScreen
import com.example.examenu2.ui.viewmodel.MensajeViewModel // Importa tu ViewModel
import com.example.examenu2.ui.theme.ExamenU2Theme // Ajusta tu paquete de temas

class MainActivity : ComponentActivity() {

    // 1. Inicializa el Repositorio y la Factoría del ViewModel para inyección
    private val repository = MensajeRepository(RetrofitClient.instance)
    private val viewModelFactory = MensajeViewModel.Factory(repository) // Usa MensajeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenU2Theme {
                // 2. Crea la instancia única del ViewModel usando la factoría
                val viewModel: MensajeViewModel = viewModel(factory = viewModelFactory)

                // 3. Observa el estado del usuario autenticado (Logout/Login)
                val currentUser by viewModel.currentUser.collectAsState()

                // 4. Lógica de Navegación:
                if (currentUser == null) {
                    // Si no hay usuario logueado, muestra la pantalla de Login
                    LoginScreen(viewModel = viewModel)
                } else {
                    // Si hay un usuario logueado, muestra la pantalla de Chat
                    ChatScreen(viewModel = viewModel)
                }
            }
        }
    }
}