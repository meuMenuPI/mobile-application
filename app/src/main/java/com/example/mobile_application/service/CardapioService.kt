package com.example.mobile_application.service

import com.example.mobile_application.models.Cardapio
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CardapioService {
    @GET("cardapios")
    fun listarCardapio(@Query("id") id : Int):
            Call<List<Cardapio>>
}