package com.example.mobile_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_application.adapter.FotosRestauranteAdapter
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityMenuPrincipalBinding
import com.example.mobile_application.databinding.ActivityPerfilRestauranteFotoBinding
import com.example.mobile_application.models.FotoRestaurante
import com.example.mobile_application.service.RestauranteService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class PerfilRestauranteFoto : AppCompatActivity() {

    private val binding by lazy {
        ActivityPerfilRestauranteFotoBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        Rest.getInstance().create(RestauranteService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnVltMenu.setOnClickListener{
            val i = Intent(
                this@PerfilRestauranteFoto,
                MenuPrincipal::class.java
            )
            startActivity(i)
        }

        val pref = getSharedPreferences("RESTAURANTE", MODE_PRIVATE)
        val id = pref.getInt("ID", 0)
        val nome_restaurante = pref.getString("NOME", null)
        Log.d("Id Restaurante", id.toString())
        Log.d("Nome Restaurante", nome_restaurante.toString())

        binding.tvNome.text = nome_restaurante.toString()


        retrofit.listarFotos(id).enqueue(object : Callback<List<FotoRestaurante>> {
            override fun onResponse(
                call: Call<List<FotoRestaurante>>,
                response: Response<List<FotoRestaurante>>
            ) {
                println("Fotos ${response.body()}")
                binding.recyclerViewFotos.apply {
                    layoutManager = GridLayoutManager(
                        this@PerfilRestauranteFoto,
                        3
                    )
                    adapter = FotosRestauranteAdapter(response.body(), this@PerfilRestauranteFoto)
                }

            }

            override fun onFailure(call: Call<List<FotoRestaurante>>, t: Throwable) {
                Log.d("T_RESPONSE_ERRO", t.toString())
            }

        })


    }
}