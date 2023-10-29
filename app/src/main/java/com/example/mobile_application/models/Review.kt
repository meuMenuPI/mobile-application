package com.example.mobile_application.models

import java.time.LocalDateTime
data class Review (
    val nome: String,
    val fkRestaurante : Int,
    val fkUsuario : Int,
    val data_hora: String,
    val descricao: String,
    val nt_comida: Double,
    val nt_ambiente: Double,
    val nt_atendimento: Double
)