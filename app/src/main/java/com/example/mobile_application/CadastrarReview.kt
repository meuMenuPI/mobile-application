package com.example.mobile_application

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityCadastrarReviewBinding
import com.example.mobile_application.models.Review
import com.example.mobile_application.service.ReviewService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

class CadastrarReview : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastrarReviewBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        Rest.getInstance().create(ReviewService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pref = getSharedPreferences("RESTAURANTE", MODE_PRIVATE)
        val idRestaurante = pref.getInt("ID", 0)
        val nome_restaurante = pref.getString("NOME", null)
        val prefUsuario = getSharedPreferences("USUARIO", MODE_PRIVATE)
        val idUsuario = prefUsuario.getString("ID", null)
        val nomeUsuario = prefUsuario.getString("NOME", null)


        binding.nomeRestaurante.text = nome_restaurante.toString()

        binding.btnVltMenu.setOnClickListener{
            val i = Intent(
                this@CadastrarReview,
                RestauranteReview::class.java
            )
            startActivity(i)
        }

        var rbAtendimento = 0.0f
        var rbAmbiente = 0.0f
        var rbComida = 0.0f

        binding.rbAmbiente.setOnRatingBarChangeListener { _, rating, fromUser ->
            // Verifica se a alteração foi feita pelo usuário
            if (fromUser) {
                // Atualiza a variável de instância com o valor das estrelas selecionadas
                rbAmbiente = rating
                println("valores $rbAmbiente")
            }
        }

        binding.rbComida.setOnRatingBarChangeListener { _, rating, fromUser ->
            // Verifica se a alteração foi feita pelo usuário
            if (fromUser) {
                // Atualiza a variável de instância com o valor das estrelas selecionadas
                rbComida = rating
                println("valores $rbComida")
            }
        }

        binding.rbAtendimento.setOnRatingBarChangeListener { _, rating, fromUser ->
            // Verifica se a alteração foi feita pelo usuário
            if (fromUser) {
                // Atualiza a variável de instância com o valor das estrelas selecionadas
                rbAtendimento = rating
                println("valores $rbAtendimento")
            }
        }


        binding.btnSalvar.setOnClickListener{

            val dataHoraAtual = Date()
            val data: String = SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual)
            val hora: String = SimpleDateFormat("HH:mm:ss").format(dataHoraAtual)

            val dateTime = data+hora

            println("Data: $dateTime")

            val dados = Review(
                nome = nomeUsuario.toString(),
                fkRestaurante = idRestaurante,
                fkUsuario = idUsuario!!.toInt(),
                data_hora = dateTime,
                descricao = binding.etDescricao.text.toString(),
                nt_comida = rbComida.toDouble(),
                nt_ambiente = rbAmbiente.toDouble(),
                nt_atendimento = rbAtendimento.toDouble()
            )

            println("Dados: $dados")

            retrofit.cadastrarReview(dados).enqueue(object : Callback<Review>{
                override fun onResponse(call: Call<Review>, response: Response<Review>) {
                    Toast.makeText(this@CadastrarReview, "Review Cadastrada!", Toast.LENGTH_LONG).show()
                    val i = Intent(
                        this@CadastrarReview,
                        RestauranteReview::class.java
                    )
                    startActivity(i)
                }

                override fun onFailure(call: Call<Review>, t: Throwable) {
                    Toast.makeText(this@CadastrarReview, t.message, Toast.LENGTH_LONG).show()
                }

            })
        }









}
}