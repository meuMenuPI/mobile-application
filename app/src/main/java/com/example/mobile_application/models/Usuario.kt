package com.example.mobile_application.models

data class Usuario(
    val id : Int,
    val nome : String,
    val sobrenome: String,
    val cpf : String,
    val email : String,
    val senha : String,
    val tipo_comida_preferida : String
)
