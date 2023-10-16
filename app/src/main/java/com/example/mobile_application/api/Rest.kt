package com.example.mobile_application.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Rest {
    fun getInstance(): Retrofit {
        return Retrofit
            .Builder()
    //        .baseUrl("http://23.21.123.201:8080/meumenu")
            .baseUrl("http:/10.0.2.2:8080/meumenu/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}