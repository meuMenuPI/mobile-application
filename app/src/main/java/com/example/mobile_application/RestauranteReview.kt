package com.example.mobile_application

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_application.adapter.CardapioAdapter
import com.example.mobile_application.adapter.ReviewAdapter
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityRestauranteReviewBinding
import com.example.mobile_application.models.Endereco
import com.example.mobile_application.models.Favoritar
import com.example.mobile_application.models.Review
import com.example.mobile_application.service.ReviewService
import com.example.mobile_application.service.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestauranteReview : AppCompatActivity() {

    private val binding by lazy {
        ActivityRestauranteReviewBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        Rest.getInstance().create(ReviewService::class.java)
    }

    private val retrofitUsuario by lazy {
        Rest.getInstance().create(UsuarioService::class.java)
    }

    @SuppressLint("SuspiciousIndentation")
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

        binding.textView.text = nome_restaurante.toString()


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
                    Toast.makeText(this@RestauranteReview, t.message, Toast.LENGTH_LONG).show()
                }


            })

        retrofitUsuario.getEndereco(id).enqueue(object : Callback<Endereco>{
            override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                println("Endereco " + response.body())
                val endereco = response.body()
                if (endereco != null) {
                    binding.tvEndereco.text = "CEP: " + endereco.cep + " N°"+ endereco!!.numero.toString() + " " + endereco.uf
                }
            }

            override fun onFailure(call: Call<Endereco>, t: Throwable) {
                Toast.makeText(this@RestauranteReview, t.message, Toast.LENGTH_LONG).show()
            }

        })




        binding.btnVltMenu.setOnClickListener{
            val i = Intent(
                this@RestauranteReview,
                MenuPrincipal::class.java
            )
            startActivity(i)
        }

        binding.btnCardapio.setOnClickListener{
            val i = Intent(
                this@RestauranteReview,
                PerfilRestauranteCardapio::class.java
            )
            startActivity(i)
        }

        binding.btnFotos.setOnClickListener{
            val i = Intent(
                this@RestauranteReview,
                PerfilRestauranteFoto::class.java
            )
            startActivity(i)
        }

        binding.btnCadastrarReview.setOnClickListener{
            val i = Intent(
                this@RestauranteReview,
                CadastrarReview::class.java
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
                    Toast.makeText(this@RestauranteReview, "Obrigado por nos seguir!", Toast.LENGTH_LONG).show()
                    binding.btnSeguir.visibility = View.INVISIBLE
                    binding.btnDesfavoritar.visibility = View.VISIBLE

                }

                override fun onFailure(call: Call<List<Favoritar>>, t: Throwable) {
                    Toast.makeText(this@RestauranteReview, t.message, Toast.LENGTH_LONG).show()
                }


            })
        }

        binding.btnDesfavoritar.setOnClickListener {
            retrofitUsuario.desfavoritar(idUsuario!!.toInt(),id).enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Toast.makeText(this@RestauranteReview, "Você deixou de seguir o restaurante!", Toast.LENGTH_LONG).show()
                    binding.btnSeguir.visibility = View.VISIBLE
                    binding.btnDesfavoritar.visibility = View.INVISIBLE
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@RestauranteReview, t.message, Toast.LENGTH_LONG).show()
                }


            })
        }


        if (id != null) {
            retrofit.getReview(id).enqueue(object : Callback<List<Review>> {
                override fun onResponse(
                    call: Call<List<Review>>,
                    response: Response<List<Review>>
                ) {

                    binding.recycleReview.apply {
                        layoutManager = LinearLayoutManager(
                            this@RestauranteReview,

                            )
                        adapter = ReviewAdapter(response.body(), this@RestauranteReview)
                    }

                }

                override fun onFailure(call: Call<List<Review>>, t: Throwable) {
                    Log.d("T_RESPONSE_ERRO", t.toString())
                }
            })
        }
    }
}