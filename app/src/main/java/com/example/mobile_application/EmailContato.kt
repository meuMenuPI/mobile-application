package com.example.mobile_application

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityEmailContatoBinding
import com.example.mobile_application.models.Usuario
import com.example.mobile_application.service.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EmailContato : AppCompatActivity() {

    private val binding by lazy {
        ActivityEmailContatoBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        Rest.getInstance().create(UsuarioService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.idButtonEmail.setOnClickListener{
            guardarEmailTemp()
        }

        binding.btnVlt.setOnClickListener {
            val i = Intent(
                this@EmailContato,
                PerfilUsuario::class.java
            )
            startActivity(i)
        }

    }


    private fun showErro(){
        AlertDialog.Builder(this)
            .setTitle("Erro")
            .setMessage("Erro! Tente enviar o codigo novamente!")
            .setNeutralButton("Ok", null)
            .create()
            .show()
    }


    fun guardarEmailTemp(){
        val inputEmail = binding.idInputEmailContato3.text.toString()
        val prefs = getSharedPreferences("USUARIO", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("EMAIL_TEMP", inputEmail)
        editor.apply()

        val pref = getSharedPreferences("USUARIO", MODE_PRIVATE)
        val idUser = pref.getString("ID", null)

        if (idUser != null) {
            retrofit.gerarCodigo(idUser.toInt(), inputEmail).enqueue(object : Callback<Usuario>{
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                    Toast.makeText(baseContext, "Código gerado!", Toast.LENGTH_LONG).show()

                    val i = Intent(
                        this@EmailContato,
                        ConfirmaEmail::class.java
                    )
                    startActivity(i)
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    showErro()
                }

            })

        }
    }
}