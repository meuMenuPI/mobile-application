package com.example.mobile_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        binding.buttonEntrar.setOnClickListener {

        }
    }
    private fun logar (){
       val loginRequest = LoginRequest(
           binding.editTextEmail.toString(),
           binding.editTextPassword.toString()
       )
       retrofit.logar(loginRequest).enqueue(object : Callback <Usuario> {
           override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    val prefs = getSharedPreferences("USUARIO", MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putString("ID", response.body()?.id.toString())
                    editor.apply()
                }
               else{
                    Toast.makeText(baseContext, "Usuario ou senha incorretos", Toast.LENGTH_LONG).show()
                }
           }

           override fun onFailure(call: Call<Usuario>, t: Throwable) {
               Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
           }

       })

    }
}