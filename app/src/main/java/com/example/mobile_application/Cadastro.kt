package com.example.mobile_application

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityMainCadastro1Binding
import com.example.mobile_application.models.CadastroRequest
import com.example.mobile_application.service.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Cadastro : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainCadastro1Binding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        Rest.getInstance().create(UsuarioService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val meuBotaoCadastrar = findViewById<Button>(R.id.id_button_cadastro1)

        meuBotaoCadastrar.setOnClickListener {
            cadastrar()
        }

        binding.btnVltMenu.setOnClickListener {
            val intent = Intent(
                this@Cadastro,
                MenuPrincipal::class.java)
            startActivity(intent)
        }
    }

    private fun cadastrar() {
        val radioGroup = binding.radioGroupOpcoes
        var opcaoComidaPreferida: String = ""

        // ID do RadioButton selecionado
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId

        if (selectedRadioButtonId != -1) {
            // Um RadioButton foi selecionado
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            val opcaoSelecionada = selectedRadioButton.text.toString()
            opcaoComidaPreferida = opcaoSelecionada
            Log.i("Resultado", "Opção selecionada: $opcaoSelecionada")
        } else {
            // Nenhum RadioButton foi selecionado
            Log.i("Resultado", "Nenhuma opção selecionada")
        }

        val editTextComidaPreferida: String = opcaoComidaPreferida.toString()
        val editTextNome = findViewById<EditText>(R.id.id_input_nome_cadastro1).text.toString()
        val editTextSobreNome = findViewById<EditText>(R.id.id_input_sobrenome_cadastro1).text.toString()
        val editTextCpf = findViewById<EditText>(R.id.id_input_cpf_cadastro1).text.toString()
        val editTextEmail = findViewById<EditText>(R.id.id_input_email_cadastro1).text.toString()

        val intent = Intent(this, Cadastro2::class.java)
        intent.putExtra("nome", editTextNome)
        intent.putExtra("sobrenome", editTextSobreNome)
        intent.putExtra("cpf", editTextCpf)
        intent.putExtra("email", editTextEmail)
        intent.putExtra("tipoComidaPreferida", opcaoComidaPreferida)

        startActivity(intent)
    }
}
