package com.example.mobile_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityConfirmaEmailBinding
import com.example.mobile_application.models.Usuario
import com.example.mobile_application.service.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmaEmail : AppCompatActivity() {

    private val binding by lazy {
        ActivityConfirmaEmailBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        Rest.getInstance().create(UsuarioService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btContinuar.setOnClickListener{
            confirmarEmail()
        }

        binding.btnVltContato.setOnClickListener {
            val i = Intent(
                this@ConfirmaEmail,
                EmailContato::class.java
            )
            startActivity(i)
        }
    }

    fun confirmarEmail(){
        val pref = getSharedPreferences("USUARIO", MODE_PRIVATE)
        val idUser = pref.getString("ID", null)
        val email = pref.getString("EMAIL_TEMP", null)
        val codigo = binding.idInputCodigoContato4.text.toString()

        if (idUser != null) {
            retrofit.validarCodigo(codigo, idUser.toInt(), email.toString()).enqueue(object : Callback<Usuario>{
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if(response.isSuccessful){
                        Toast.makeText(baseContext, "Email do Usuario " + response.body()?.nome.toString() + " foi alterado!", Toast.LENGTH_LONG).show()
                        val i = Intent(
                            this@ConfirmaEmail,
                            MenuPrincipal::class.java
                        )
                        startActivity(i)
                    }
                    else{
                        showErro()
                    }

//                    val erroBody = response.errorBody()
//                    val mensagemDeErro = erroBody?.string().toString()
//                    Log.d("ERRO", mensagemDeErro)
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    showErro()
                }

            })
        }
    }


    private fun showErro(){
        AlertDialog.Builder(this)
            .setTitle("Erro")
            .setMessage("Erro! Codigo Incorreto!")
            .setNeutralButton("Ok", null)
            .create()
            .show()
    }
}