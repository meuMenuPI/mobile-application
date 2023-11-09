package com.example.mobile_application

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.example.mobile_application.adapter.MiniEspecialidadeAdapter
import com.example.mobile_application.adapter.MiniRestauranteAdapter
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityMenuPrincipalBinding
import com.example.mobile_application.models.RestauranteDto
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

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

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

        var lista: MutableList<String> = mutableListOf()
        val listaRes = binding.listaRestaurante
        var listAdapter: ArrayAdapter<String>


        retrofit.pegarRestaurante().enqueue(object : Callback<List<RestauranteDto>> {
            override fun onResponse(
                call: Call<List<RestauranteDto>>,
                response: Response<List<RestauranteDto>>
            ) {

                Log.d("RESPOSTA", response.body().toString())

                var listaTemp: List<RestauranteDto>? = response.body()
                listaTemp?.forEach { item ->
                    lista.add(item.nome)
                }

                Log.d("LISTATEMP", listaTemp.toString())
            }

            override fun onFailure(call: Call<List<RestauranteDto>>, t: Throwable) {
                Log.d("T_RESPONSE_ERRO", t.toString())
            }

        })

        listAdapter = ArrayAdapter<String>(
            this@MenuPrincipal,
            android.R.layout.simple_list_item_1,
            lista
        )
        listaRes.adapter = listAdapter

        binding.barraPesquisa.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (lista?.contains(query)!!) {
                    listAdapter.filter.filter(query)
                    listaRes.visibility = View.VISIBLE
                } else {
                    listaRes.visibility = View.INVISIBLE
                    Toast.makeText(this@MenuPrincipal, "Restaurante não encontrado!", Toast.LENGTH_LONG)
                        .show()
                }
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {

                listAdapter.filter.filter(newText)
                listaRes.visibility = View.VISIBLE
                return false
            }

        })

        binding.barraPesquisa.setOnCloseListener {
            listaRes.visibility = View.INVISIBLE
            false
        }

        retrofit.filtrarBemAvaliado().enqueue(object : Callback<List<RestauranteReviewDto>> {
            override fun onResponse(
                call: Call<List<RestauranteReviewDto>>,
                response: Response<List<RestauranteReviewDto>>
            ) {

                binding.linearLayout4.adapter = MiniRestauranteAdapter(response.body())

            }

            override fun onFailure(call: Call<List<RestauranteReviewDto>>, t: Throwable) {
                Log.d("T_RESPONSE_ERRO", t.toString())
            }

        })

        retrofit.filtrarEspecialidade().enqueue(object : Callback<List<String>> {
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>
            ) {

                binding.linearLayout5.adapter = MiniEspecialidadeAdapter(response.body())

            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d("T_RESPONSE_ERRO", t.toString())
            }
        })
    }
}