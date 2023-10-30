package com.example.mobile_application.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Rest {
    fun getInstance(): Retrofit {
        return Retrofit
            .Builder()
            //AWS
            .baseUrl("https://meumenuweb.ddns.net/meumenu/")
            //Emulador
    //        .baseUrl("http:/10.0.2.2:8080/meumenu/")
            //Pc Lucas
    //        .baseUrl("http:/192.168.18.41:8080/meumenu/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}