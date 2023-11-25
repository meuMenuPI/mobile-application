package com.example.mobile_application.models

data class Cardapio(

    val id: Int,
    val fk_restaurante: Int,
    val nome: String,
    val preco: Double,
    val estiloGastronomico: String,
    val descricao: String,
    val fotoPrato: String

)
