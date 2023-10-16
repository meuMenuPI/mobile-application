package com.example.mobile_application.models

data class Usuario(
    val id : Int,
    val nome : String,
    val sobrenome: String,
    val email : String,
    val tipoComidaPreferida : String,
    val fotoPerfil : String
)
