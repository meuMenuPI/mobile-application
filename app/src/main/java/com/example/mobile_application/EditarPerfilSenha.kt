package com.example.mobile_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_application.databinding.ActivityEditarPerfilBinding
import com.example.mobile_application.databinding.ActivityPerfilUsuarioBinding

class EditarPerfilSenha : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditarPerfilBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        binding.constraintSenha.setOnClickListener{
//            val i = Intent(
//                this@EditarPerfilSenha,
//                TrocarSenha::class.java
//            )
//            startActivity(i)
//        }
    }
}