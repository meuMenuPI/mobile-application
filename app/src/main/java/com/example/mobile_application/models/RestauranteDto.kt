package com.example.mobile_application.models

data class RestauranteDto(
   val id : Int,
   val nome : String,
   val especialidade : String,
   val beneficio : Boolean,
   val telefone : String,
   val site : String,
   val estrela : Int
)
