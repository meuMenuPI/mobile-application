package com.example.mobile_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityPerfilUsuarioBinding
import com.example.mobile_application.service.UsuarioService

class PerfilUsuario : AppCompatActivity() {

    private val binding by lazy {
        ActivityPerfilUsuarioBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.iconeSeta.setOnClickListener {
            val i = Intent(
                this@PerfilUsuario,
                MenuPrincipal::class.java
            )
            startActivity(i)
        }

        binding.btnEditarDados.setOnClickListener {
            val i = Intent(
                this@PerfilUsuario,
                EditarPerfil::class.java
            )
            startActivity(i)
        }

        val pref = getSharedPreferences("USUARIO", MODE_PRIVATE)
        val nome = pref.getString("NOME", null)
        val email = pref.getString("EMAIL", null)

        binding.nomeUsuario.text = nome
        binding.email.text = email

    }
}