package com.example.examenu2.data.model


import com.google.gson.annotations.SerializedName
data class LoginResponse (

 @SerializedName("token") val token: String,
 @SerializedName("usuario") val usuario: Usuario?

)