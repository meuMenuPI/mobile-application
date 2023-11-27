package com.example.mobile_application

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
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
import com.example.mobile_application.adapter.MiniUfAdapter
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityMenuPrincipalBinding
import com.example.mobile_application.models.RestauranteDto
import com.example.mobile_application.models.RestauranteReviewDto
import com.example.mobile_application.service.RestauranteService
import com.google.android.material.bottomsheet.BottomSheetDialog
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

        var lista: MutableList<RestauranteDto> = mutableListOf()
        var listaNome: MutableList<String> = mutableListOf()
        val listaRes = binding.listaRestaurante
        var listAdapter: ArrayAdapter<String>


        retrofit.pegarRestaurante().enqueue(object : Callback<List<RestauranteDto>> {
            override fun onResponse(
                call: Call<List<RestauranteDto>>,
                response: Response<List<RestauranteDto>>
            ) {

                Log.d("RESPOSTA", response.body().toString())

                var listaTemp: List<RestauranteDto>? = response.body()
                lista = listaTemp as MutableList<RestauranteDto>

                listaTemp?.forEach { item ->
                    listaNome.add(item.nome)
                }

            }

            override fun onFailure(call: Call<List<RestauranteDto>>, t: Throwable) {
                Log.d("T_RESPONSE_ERRO", t.toString())
            }

        })

        listAdapter = ArrayAdapter<String>(
            this@MenuPrincipal,
            android.R.layout.simple_list_item_1,
            listaNome
        )
        listaRes.adapter = listAdapter
        listaRes.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val restauranteClicavel = lista.find { item -> item.nome == (p1 as TextView).text }
                val prefs = getSharedPreferences("RESTAURANTE", MODE_PRIVATE)
                val editor = prefs.edit()
                if (restauranteClicavel != null) {
                    editor.putInt("ID", restauranteClicavel.id)
                    editor.apply()
                    val i = Intent(
                        this@MenuPrincipal,
                        RestauranteReview::class.java
                    )
                    startActivity(i)
                }
            }
        })

        binding.barraPesquisa.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (listaNome.contains(query)!!) {
                    listAdapter.filter.filter(query)
                    listaRes.visibility = View.VISIBLE
                } else {
                    listaRes.visibility = View.INVISIBLE
                    Toast.makeText(
                        this@MenuPrincipal,
                        "Restaurante não encontrado!",
                        Toast.LENGTH_LONG
                    )
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

                binding.linearLayout4.adapter = MiniRestauranteAdapter(response.body(),::guiarReview, this@MenuPrincipal)
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

                binding.linearLayout5.adapter = MiniEspecialidadeAdapter(response.body(),::abrirModal)

            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d("T_RESPONSE_ERRO", t.toString())
            }
        })

        retrofit.filtrarUf("sp").enqueue(object : Callback<List<RestauranteReviewDto>> {
            override fun onResponse(
                call: Call<List<RestauranteReviewDto>>,
                response: Response<List<RestauranteReviewDto>>
            ) {

                binding.linearLayout6.adapter = MiniUfAdapter(response.body(),::guiarReview,this@MenuPrincipal)

            }

            override fun onFailure(call: Call<List<RestauranteReviewDto>>, t: Throwable) {
                Log.d("T_RESPONSE_ERRO", t.toString())
            }
        })

    }

    fun guiarReview(restaurante: RestauranteReviewDto) {
        val prefs = getSharedPreferences("RESTAURANTE", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt("ID", restaurante.id)
        editor.apply()
        val i = Intent(
            this@MenuPrincipal,
            PerfilRestauranteCardapio::class.java
        )
        startActivity(i)
    }

    fun abrirModal(especialidade: String) {
        val view = layoutInflater.inflate(R.layout.modal_restaurantes, null)
        val dialog = BottomSheetDialog(this)
        val btnClose = view.findViewById<Button>(R.id.btnFechar)
        val mListView = view.findViewById<ListView>(R.id.listaRestaurante)
        var listaNomeEspecialidade: MutableList<String> = mutableListOf()

        val retrofitCallback = object : Callback<List<RestauranteReviewDto>> {
            override fun onResponse(
                call: Call<List<RestauranteReviewDto>>,
                response: Response<List<RestauranteReviewDto>>
            ) {
                val listaResEspecialidade = response.body() as MutableList<RestauranteReviewDto>?
                listaResEspecialidade?.let {
                    listaNomeEspecialidade = it.map { item -> item.nome }.toMutableList()
                    val arrayAdapter = ArrayAdapter(
                        this@MenuPrincipal,
                        android.R.layout.simple_list_item_1,
                        listaNomeEspecialidade
                    )
                    mListView.adapter = arrayAdapter
                }

                mListView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val restauranteClicavel = listaResEspecialidade?.find { item -> item.nome == (p1 as TextView).text }
                        val prefs = getSharedPreferences("RESTAURANTE", MODE_PRIVATE)
                        val editor = prefs.edit()
                        if (restauranteClicavel != null) {
                            editor.putInt("ID", restauranteClicavel.id)
                            editor.apply()
                            val i = Intent(
                                this@MenuPrincipal,
                                RestauranteReview::class.java
                            )
                            startActivity(i)
                        }
                    }
                })
            }

            override fun onFailure(call: Call<List<RestauranteReviewDto>>, t: Throwable) {
                Log.d("T_RESPONSE_ERRO", t.toString())
            }
        }

        retrofit.filtrarRestauranteEspecialidade(especialidade).enqueue(retrofitCallback)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }


}


