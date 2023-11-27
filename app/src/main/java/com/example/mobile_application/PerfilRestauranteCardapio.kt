package com.example.mobile_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_application.adapter.CardapioAdapter
import com.example.mobile_application.adapter.FotosRestauranteAdapter
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityPerfilRestauranteCardapioBinding
import com.example.mobile_application.databinding.ActivityPerfilRestauranteFotoBinding
import com.example.mobile_application.models.Cardapio
import com.example.mobile_application.models.Endereco
import com.example.mobile_application.models.Favoritar
import com.example.mobile_application.models.FotoRestaurante
import com.example.mobile_application.service.CardapioService
import com.example.mobile_application.service.RestauranteService
import com.example.mobile_application.service.UsuarioService
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

    private val retrofitUsuario by lazy {
        Rest.getInstance().create(UsuarioService::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pref = getSharedPreferences("RESTAURANTE", MODE_PRIVATE)
        val prefUsuario = getSharedPreferences("USUARIO" , MODE_PRIVATE)
        val idUsuario = prefUsuario.getString("ID", null)
        val id = pref.getInt("ID", 0)
        val nome_restaurante = pref.getString("NOME", null)
        Log.d("Id Restaurante", id.toString())
        Log.d("Nome Restaurante", nome_restaurante.toString())

        binding.tvNome.text = nome_restaurante.toString()

        retrofitUsuario.getEndereco(id).enqueue(object : Callback<Endereco>{
            override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                println("Endereco " + response.body())
                val endereco = response.body()
                if (endereco != null) {
                    binding.tvEndereco.text = "CEP: " + endereco.cep + " N°"+ endereco!!.numero.toString() + " " + endereco.uf
                }
            }

            override fun onFailure(call: Call<Endereco>, t: Throwable) {
                Toast.makeText(this@PerfilRestauranteCardapio, t.message, Toast.LENGTH_LONG).show()
            }

        })


        retrofitUsuario.getFavorito(idUsuario!!.toInt(),id).enqueue(object : Callback<List<Int>>{
            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                val lista = response.body()
                println("lista: $lista")
                if (lista != null && lista.isNotEmpty()) {
                    if (lista[0] == 0) {
                        binding.btnSeguir.visibility = View.VISIBLE
                        binding.btnDesfavoritar.visibility = View.INVISIBLE
                    } else {
                        binding.btnSeguir.visibility = View.INVISIBLE
                        binding.btnDesfavoritar.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                Toast.makeText(this@PerfilRestauranteCardapio, t.message, Toast.LENGTH_LONG).show()
            }


        })

        binding.btnVltMenu.setOnClickListener{
            val i = Intent(
                this@PerfilRestauranteCardapio,
                MenuPrincipal::class.java
            )
            startActivity(i)
        }

        binding.btnFotos.setOnClickListener{
            val i = Intent(
                this@PerfilRestauranteCardapio,
                PerfilRestauranteFoto::class.java
            )
            startActivity(i)
        }

        binding.btnReviews.setOnClickListener{
            val i = Intent(
                this@PerfilRestauranteCardapio,
                RestauranteReview::class.java
            )
            startActivity(i)
        }



        binding.btnSeguir.setOnClickListener {
            val dados = Favoritar(
                fk_restaurante = id,
                fk_usuario = idUsuario!!.toInt()
            )
            retrofitUsuario.seguir(dados).enqueue(object : Callback<List<Favoritar>>{
                override fun onResponse(
                    call: Call<List<Favoritar>>,
                    response: Response<List<Favoritar>>
                ) {
                    Toast.makeText(this@PerfilRestauranteCardapio, "Obrigado por nos seguir!", Toast.LENGTH_LONG).show()
                    binding.btnSeguir.visibility = View.INVISIBLE
                    binding.btnDesfavoritar.visibility = View.VISIBLE

                }

                override fun onFailure(call: Call<List<Favoritar>>, t: Throwable) {
                    Toast.makeText(this@PerfilRestauranteCardapio, t.message, Toast.LENGTH_LONG).show()
                }


            })
        }

        binding.btnDesfavoritar.setOnClickListener {
            retrofitUsuario.desfavoritar(idUsuario!!.toInt(),id).enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Toast.makeText(this@PerfilRestauranteCardapio, "Você deixou de seguir o restaurante!", Toast.LENGTH_LONG).show()
                    binding.btnSeguir.visibility = View.VISIBLE
                    binding.btnDesfavoritar.visibility = View.INVISIBLE
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@PerfilRestauranteCardapio, t.message, Toast.LENGTH_LONG).show()
                }


            })
        }

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