package com.example.mobile_application.service

import com.example.mobile_application.models.RestauranteDto
import com.example.mobile_application.models.RestauranteReviewDto
import retrofit2.Call
import retrofit2.http.GET

interface RestauranteService {
    @GET("restaurantes/filtrar/bem-avaliado")
    fun filtrarBemAvaliado():
        Call<List<RestauranteReviewDto>>

    @GET("restaurantes/filtrar/nome-especialiade")
    fun filtrarEspecialidade():
            Call<List<String>>

    @GET("restaurantes")
    fun pegarRestaurante():
            Call<List<RestauranteDto>>
}