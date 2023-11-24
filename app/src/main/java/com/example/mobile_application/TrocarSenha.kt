package com.example.mobile_application

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityMainCadastro1Binding
import com.example.mobile_application.databinding.ActivityUsuarioTrocarSenhaBinding
import com.example.mobile_application.models.CadastroRequest
import com.example.mobile_application.models.LoginRequest
import com.example.mobile_application.models.Usuario
import com.example.mobile_application.service.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class TrocarSenha : AppCompatActivity() {

    private val binding by lazy {
        ActivityUsuarioTrocarSenhaBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        Rest.getInstance().create(UsuarioService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val meuBotao = findViewById<Button>(R.id.btn_continuar)

        val pref = getSharedPreferences("USUARIO", MODE_PRIVATE)
        val userId = pref.getString("ID", "") ?: ""
        val userIdInt = userId.toInt()
        val userName = pref.getString("NOME", "") ?: ""
        Log.d("Id usuario", userId.toString())
        Log.d("Nome usuario", userName.toString())

        meuBotao.setOnClickListener {
            trocarSenha(userIdInt)
        }

        binding.iconeSeta.setOnClickListener{
            val i = Intent(
                this@TrocarSenha,
                EditarPerfil::class.java
            )
            startActivity(i)
        }
    }

    private fun trocarSenha(id: Int) {

        val editSenhaAtual = findViewById<EditText>(R.id.txt_senhaAtual).text.toString()
        val editNovaSenha = findViewById<EditText>(R.id.txt_novaSenha).text.toString()
        val editNovaSenhaConfirma = findViewById<EditText>(R.id.txt_novaSenhaConfirma).text.toString()

        if(editNovaSenha != editNovaSenhaConfirma){
            showMessage("Erro", "As novas senhas estão diferentes")
        }else{
            retrofit.trocarSenha(id, editSenhaAtual, editNovaSenha).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.code().toString() == "400"){
                        showMessage("Erro", "Senha Atual não encontrada")
                    }else{
                        showMessage("Sucesso", "Senha alterada com sucesso")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    if(editNovaSenha != editNovaSenhaConfirma){
                        showMessage("Erro", "As novas senhas estão diferentes")
                    }else{
                        showMessage("Sucesso", "Senha alterada com sucesso")
                    }
                }

            })
        }




    }

    private fun showMessage(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton("Ok", null)
            .create()
            .show()
    }

}
