package com.example.mobile_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_application.adapter.CardapioAdapter
import com.example.mobile_application.adapter.FotosRestauranteAdapter
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityPerfilRestauranteCardapioBinding
import com.example.mobile_application.databinding.ActivityPerfilRestauranteFotoBinding
import com.example.mobile_application.models.Cardapio
import com.example.mobile_application.models.FotoRestaurante
import com.example.mobile_application.service.CardapioService
import com.example.mobile_application.service.RestauranteService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilRestauranteCardapio : AppCompatActivity() {
    private val binding by lazy {
        ActivityPerfilRestauranteCardapioBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        Rest.getInstance().create(CardapioService::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnVltMenu.setOnClickListener{
            val i = Intent(
                this@PerfilRestauranteCardapio,
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

        retrofit.listarCardapio(id).enqueue(object : Callback<List<Cardapio>>{
            override fun onResponse(
                call: Call<List<Cardapio>>,
                response: Response<List<Cardapio>>
            ) {
                println("Pratos ${response.body()}")

                binding.recycleCardapio.apply {
                    layoutManager = LinearLayoutManager(
                        this@PerfilRestauranteCardapio,

                    )
                    adapter = CardapioAdapter(response.body(), this@PerfilRestauranteCardapio)
                }

                //binding.recycleCardapio.adapter = CardapioAdapter(response.body(), this@PerfilRestauranteCardapio)
            }

            override fun onFailure(call: Call<List<Cardapio>>, t: Throwable) {
                Log.d("T_RESPONSE_ERRO", t.toString())
            }
        })

    }
}