package com.example.mobile_application.service


import com.example.mobile_application.models.CadastroRequest
import com.example.mobile_application.models.LoginRequest
import com.example.mobile_application.models.Usuario
import retrofit2.Call
import retrofit2.http.*

interface UsuarioService {
    @POST("usuarios/logar")
    fun entrar(@Body dados : LoginRequest):
            retrofit2.Call<Usuario>

    @PUT("usuarios/emailUsuario")
    fun gerarCodigo(@Query("id") id : Int, @Query("emailNovo") emailNovo : String):
            Call<Usuario>

    @GET("usuarios/validarEmail")
    fun validarCodigo(@Query("codigo") codigo : String,@Query("id") id : Int, @Query("novoEmail") novoEmail : String):
            Call<Usuario>

    @POST("usuarios/cadastrar")
    fun cadastrar(@Body dadosRequest: CadastroRequest):
            retrofit2.Call<Usuario>

    @PUT("usuarios/trocarSenha")
    @FormUrlEncoded
    fun trocarSenha(
        @Query("id") id: Int,
        @Query("senhaAtual") senhaAtual: String,
        @Field("novaSenha") novaSenha: String
    ): Call<String>
}