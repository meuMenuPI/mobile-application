package com.example.mobile_application

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.example.mobile_application.adapter.MiniEspecialidadeAdapter
import com.example.mobile_application.adapter.MiniRestauranteAdapter
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityMenuPrincipalBinding
import com.example.mobile_application.models.RestauranteReviewDto
import com.example.mobile_application.service.RestauranteService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuPrincipal : AppCompatActivity() {

    private val binding by lazy {
        ActivityMenuPrincipalBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        Rest.getInstance().create(RestauranteService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnPerfil.setOnClickListener {
            val i = Intent(
                this@MenuPrincipal,
                PerfilUsuario::class.java
            )
            startActivity(i)
        }

        binding.btnSair.setOnClickListener {
            val sharedPreferences: SharedPreferences =
                baseContext.getSharedPreferences("USUARIO", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val sharedPreferences2: SharedPreferences =
                baseContext.getSharedPreferences("RESTAURANTE", MODE_PRIVATE)
            val editor2 = sharedPreferences.edit()
            editor2.clear()
            editor2.apply()

            val i = Intent(
                this@MenuPrincipal,
                Logar::class.java
            )
            startActivity(i)
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //val constraintLayoutCultura = ConstraintLayout(baseContext)
        //val constraintLayoutPerto = ConstraintLayout(baseContext)


        retrofit.filtrarBemAvaliado().enqueue(object : Callback<List<RestauranteReviewDto>>{
            override fun onResponse(
                call: Call<List<RestauranteReviewDto>>,
                response: Response<List<RestauranteReviewDto>>
            ) {

                binding.linearLayout4.adapter = MiniRestauranteAdapter(response.body())

            }

            override fun onFailure(call: Call<List<RestauranteReviewDto>>, t: Throwable) {
                Log.d("T_RESPONSE_ERRO",t.toString())
            }

        })

        retrofit.filtrarEspecialidade().enqueue(object:Callback<List<String>>{
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>
            ) {

                binding.linearLayout5.adapter = MiniEspecialidadeAdapter(response.body())

            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d("T_RESPONSE_ERRO",t.toString())
            }
        })
    }
}