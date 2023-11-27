package com.example.mobile_application.service
import com.example.mobile_application.models.RestauranteDto
import com.example.mobile_application.models.FotoRestaurante
import com.example.mobile_application.models.RestauranteReviewDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestauranteService {
    @GET("restaurantes/filtrar/bem-avaliado")
    fun filtrarBemAvaliado():
        Call<List<RestauranteReviewDto>>

    @GET("restaurantes/filtrar/nome-especialiade")
    fun filtrarEspecialidade():
            Call<List<String>>

    @GET("restaurantes/filtrar/uf")
    fun filtrarUf(@Query("uf") uf : String):
            Call<List<RestauranteReviewDto>>

    @GET("restaurantes/filtrar/especialidade")
    fun filtrarRestauranteEspecialidade(@Query("especialidade") especialidade : String):
            Call<List<RestauranteReviewDto>>

    @GET("restaurantes")
    fun pegarRestaurante():
            Call<List<RestauranteDto>>

    @GET("restaurantes/foto-restaurante")
    fun listarFotos(@Query("id") id : Int):
            Call<List<FotoRestaurante>>
}