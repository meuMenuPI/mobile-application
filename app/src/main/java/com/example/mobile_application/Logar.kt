package com.example.mobile_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityLogarBinding
import com.example.mobile_application.models.LoginRequest
import com.example.mobile_application.models.Usuario
import com.example.mobile_application.service.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class Logar : AppCompatActivity() {

    private val binding by lazy {
        ActivityLogarBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        Rest.getInstance().create(UsuarioService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logar)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val i = Intent(
            this@Logar,
            MenuPrincipal::class.java
        )
        startActivity(i)

        val meuBotao = findViewById<Button>(R.id.buttonEntrar)


        meuBotao.setOnClickListener {
            logar()
        }

    }
    private fun logar (){

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextSenha = findViewById<EditText>(R.id.editTextPassword)

       val dados = LoginRequest(
           editTextEmail.text.toString(),
           editTextSenha.text.toString()
       )
        Log.d("EMAIL", dados.email)
        Log.d("SENHA", dados.senha)
       retrofit.entrar(dados).enqueue(object : Callback <Usuario> {
           override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    val prefs = getSharedPreferences("USUARIO", MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putString("ID", response.body()?.id.toString())
                    editor.apply()
                    Toast.makeText(baseContext, "Logado!", Toast.LENGTH_LONG).show()

                    val i = Intent(
                        this@Logar,
                        MenuPrincipal::class.java
                    )
                    startActivity(i)
                }
               else{
                   Log.d("ERRO", response.toString())
                    Toast.makeText(baseContext, response.toString(), Toast.LENGTH_LONG).show()
                }
           }

           override fun onFailure(call: Call<Usuario>, t: Throwable) {
               Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
           }

       })

    }
}