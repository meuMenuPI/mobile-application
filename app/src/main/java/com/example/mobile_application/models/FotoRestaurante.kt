package com.example.mobile_application.models

data class FotoRestaurante (
    val id : Int,
    val fkRestaurante : Int,
    val nomeFoto: String,
    val fachada : Boolean,
    val interior : Boolean
)