package com.example.mobile_application.service

import com.example.mobile_application.models.Review
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ReviewService {
    @POST("reviews")
    fun cadastrarReview(@Body dados : Review):
            Call<Review>

    @GET("reviews")
    fun getReview(@Query ("fkRestaurante") fkRestaurante : Int):
            Call<List<Review>>
    //retrofit2.Call<Review>
}