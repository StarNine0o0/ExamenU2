package com.cursosant.chatexentar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material3.*

import androidx.activity.viewModels
import com.cursosant.chatexentar.data.viewmodel.MainViewModel
import com.cursosant.chatexentar.ui.ChatScreen
import com.cursosant.chatexentar.ui.LoginScreen


class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                if (vm.usuario == null) {
                    LoginScreen(vm)
                } else {
                    ChatScreen(vm)

            }
        }

        }
    }
}
