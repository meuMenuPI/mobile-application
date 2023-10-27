package com.example.mobile_application

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityMainCadastro1Binding
import com.example.mobile_application.databinding.ActivityMainCadastro2Binding
import com.example.mobile_application.models.CadastroRequest
import com.example.mobile_application.models.Usuario
import com.example.mobile_application.service.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Cadastro2 : AppCompatActivity() {
    private lateinit var binding: ActivityMainCadastro2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainCadastro2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val meuBotao = binding.root.findViewById<Button>(R.id.id_button2_cadastro2)
        val meuBotaoVoltar = binding.root.findViewById<ImageView>(R.id.id_seta_left2_cadastro2)

        val nome = intent.getStringExtra("nome") ?: ""
        val sobrenome = intent.getStringExtra("sobrenome") ?: ""
        val cpf = intent.getStringExtra("cpf") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        val tipoComidaPreferida = intent.getStringExtra("tipoComidaPreferida") ?: ""

        meuBotaoVoltar.setOnClickListener {
            val intent = Intent(this, Cadastro::class.java)
            startActivity(intent)
        }

        meuBotao.setOnClickListener {
            val senha = binding.root.findViewById<EditText>(R.id.id_input_senha_cadastro2).text.toString()
            val senhaConfirmada = binding.root.findViewById<EditText>(R.id.id_input_senha_confirmada_cadastro2).text.toString()

            val dados = CadastroRequest(
                nome = nome,
                sobrenome = sobrenome,
                cpf = cpf,
                email = email,
                senha = senha,
                tipoComidaPreferida = tipoComidaPreferida
            )

            val retrofit by lazy {
                Rest.getInstance().create(UsuarioService::class.java)
            }

            retrofit.cadastrar(dados).enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        val prefs = getSharedPreferences()
                        val editor = prefs.edit()
                        // editor.putString("ID", response.body()?.id.toString())
                        editor.apply()
                        Toast.makeText(this@Cadastro2, "Cadastrado!", Toast.LENGTH_LONG).show()
                        Log.d("batata", response.toString())
                        val intent = Intent(this@Cadastro2, Logar::class.java)
                        startActivity(intent)
                    } else {
                        if (nome.isEmpty()) {
                            Toast.makeText(this@Cadastro2, "O Nome deve ser preenchido", Toast.LENGTH_LONG).show()
                        } else if (sobrenome.isEmpty()) {
                            Toast.makeText(this@Cadastro2, "O Sobrenome deve ser preenchido", Toast.LENGTH_LONG).show()
                        } else if (cpf.length != 11) {
                            Toast.makeText(this@Cadastro2, "Favor apenas os 11 números no CPF", Toast.LENGTH_LONG).show()
                        } else if (!email.contains("@") || !email.contains(".")) {
                            Toast.makeText(this@Cadastro2, "E-mail inválido", Toast.LENGTH_LONG).show()
                        } else if (senha.isEmpty()) {
                            Toast.makeText(this@Cadastro2, "A Senha deve ser preenchida", Toast.LENGTH_LONG).show()
                        } else if (senha != senhaConfirmada) {
                            Toast.makeText(this@Cadastro2, "As Senhas estão diferentes", Toast.LENGTH_LONG).show()
                        } else if (tipoComidaPreferida.isEmpty()) {
                            Toast.makeText(this@Cadastro2, "A Comida preferida deve ser preenchida", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(this@Cadastro2, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun getSharedPreferences(): SharedPreferences {
        return getSharedPreferences("USUARIO", MODE_PRIVATE)
    }
}
