package com.example.mobile_application.models

data class Endereco (
    val id: Int,
    val fk_restaurante: Int,
    val fk_usuario: Int,
    val complemento: String,
    val numero: Int,
    val cep: String,
    val uf: String
)