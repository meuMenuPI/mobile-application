package com.example.mobile_application.models

data class CadastroRequest(
    val nome: String,
    val sobrenome: String,
    val cpf: String,
    val email: String,
    val senha: String,
    val tipoComidaPreferida: String
)
