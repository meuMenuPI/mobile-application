package com.example.mobile_application.service


import com.example.mobile_application.models.LoginRequest
import com.example.mobile_application.models.Usuario
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioService {
    @POST("usuarios/logar")
    fun entrar(@Body dados : LoginRequest):
            retrofit2.Call<Usuario>
}