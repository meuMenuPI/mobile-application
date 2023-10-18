package com.example.mobile_application

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.marginRight
import androidx.core.view.setPadding
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityMenuPrincipalBinding
import com.example.mobile_application.models.RestauranteReviewDto
import com.example.mobile_application.service.RestauranteService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuPrincipal : AppCompatActivity() {

    private val binding by lazy {
        ActivityMenuPrincipalBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        Rest.getInstance().create(RestauranteService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val constraintLayoutCultura = ConstraintLayout(baseContext)
        //val constraintLayoutPerto = ConstraintLayout(baseContext)


        retrofit.filtrarBemAvaliado().enqueue(object : Callback<List<RestauranteReviewDto>>{
            override fun onResponse(
                call: Call<List<RestauranteReviewDto>>,
                response: Response<List<RestauranteReviewDto>>
            ) {


                val restaurantes = response.body()
                restaurantes?.forEach{restaurante ->

                    val constraintLayoutAvaliado = ConstraintLayout(baseContext)
                    constraintLayoutAvaliado.id = View.generateViewId()

                    val scale = resources.displayMetrics.density

                    val layoutParamsAvaliado = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParamsAvaliado.height = (80 * scale + 0.5f).toInt()
                    layoutParamsAvaliado.width = (110 * scale + 0.5f).toInt()
                    layoutParamsAvaliado.leftMargin = (15 * scale + 0.5f).toInt()
                    constraintLayoutAvaliado.layoutParams = layoutParamsAvaliado
                    constraintLayoutAvaliado.setBackgroundResource(R.drawable.borda_imagem)
                    constraintLayoutAvaliado.setPadding((5 * scale + 0.5f).toInt())

                    val image = ImageView(baseContext)
                    image.id = View.generateViewId()
                    image.layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )

                    image.setImageResource(R.drawable.sushi)
                    val layoutParamsImage = image.layoutParams as ConstraintLayout.LayoutParams
                    //layoutParamsImage.startToStart = constraintLayoutAvaliado.id
                    //layoutParamsImage.topToTop = constraintLayoutAvaliado.id

                    val nm_restaurante = TextView(baseContext)
                    nm_restaurante.id = View.generateViewId()

                    nm_restaurante.layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    nm_restaurante.text = restaurante.nome
                    nm_restaurante.setTextColor(ContextCompat.getColor(baseContext,R.color.white))
                    nm_restaurante.setTypeface(null, Typeface.BOLD)

                    val layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.startToStart = constraintLayoutAvaliado.id
                    layoutParams.endToEnd = constraintLayoutAvaliado.id
                    layoutParams.topToTop = constraintLayoutAvaliado.id
                    layoutParams.bottomToBottom = constraintLayoutAvaliado.id

                    image.layoutParams = layoutParamsImage
                    nm_restaurante.layoutParams = layoutParams

                    constraintLayoutAvaliado.setOnClickListener{
                        val prefs = getSharedPreferences("RESTAURANTE", MODE_PRIVATE)
                        val editor = prefs.edit()
                        editor.putString("ID", restaurante.id.toString())
                        editor.apply()
                    }

                    constraintLayoutAvaliado.addView(image)
                    constraintLayoutAvaliado.addView(nm_restaurante)
                   binding.linearLayout4.addView(constraintLayoutAvaliado)
                }
            }

            override fun onFailure(call: Call<List<RestauranteReviewDto>>, t: Throwable) {
                Log.d("T_RESPONSE_ERRO",t.toString())
            }

        })

        retrofit.filtrarEspecialidade().enqueue(object:Callback<List<String>>{
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>
            ) {


                val especialidades = response.body()
                especialidades?.forEach{especialidade ->

                    val constraintLayoutAvaliado = ConstraintLayout(baseContext)
                    constraintLayoutAvaliado.id = View.generateViewId()

                    val scale = resources.displayMetrics.density

                    val layoutParamsAvaliado = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParamsAvaliado.height = (80 * scale + 0.5f).toInt()
                    layoutParamsAvaliado.width = (110 * scale + 0.5f).toInt()
                    layoutParamsAvaliado.leftMargin = (15 * scale + 0.5f).toInt()
                    constraintLayoutAvaliado.layoutParams = layoutParamsAvaliado
                    constraintLayoutAvaliado.setBackgroundResource(R.drawable.borda_imagem)
                    constraintLayoutAvaliado.setPadding((5 * scale + 0.5f).toInt())

                    val image = ImageView(baseContext)
                    image.id = View.generateViewId()
                    image.layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )

                    image.setImageResource(R.drawable.sushi)
                    val layoutParamsImage = image.layoutParams as ConstraintLayout.LayoutParams
                    //layoutParamsImage.startToStart = constraintLayoutAvaliado.id
                    //layoutParamsImage.topToTop = constraintLayoutAvaliado.id

                    val nm_restaurante = TextView(baseContext)
                    nm_restaurante.id = View.generateViewId()

                    nm_restaurante.layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    nm_restaurante.text = especialidade
                    nm_restaurante.setTextColor(ContextCompat.getColor(baseContext,R.color.white))
                    nm_restaurante.setTypeface(null, Typeface.BOLD)

                    val layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.startToStart = constraintLayoutAvaliado.id
                    layoutParams.endToEnd = constraintLayoutAvaliado.id
                    layoutParams.topToTop = constraintLayoutAvaliado.id
                    layoutParams.bottomToBottom = constraintLayoutAvaliado.id

                    image.layoutParams = layoutParamsImage
                    nm_restaurante.layoutParams = layoutParams

//                    constraintLayoutAvaliado.setOnClickListener{
//                        val prefs = getSharedPreferences("RESTAURANTE", MODE_PRIVATE)
//                        val editor = prefs.edit()
//                        editor.putString("ID", restaurante.id.toString())
//                        editor.apply()
//                    }

                    constraintLayoutAvaliado.addView(image)
                    constraintLayoutAvaliado.addView(nm_restaurante)
                    binding.linearLayout5.addView(constraintLayoutAvaliado)
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d("T_RESPONSE_ERRO",t.toString())
            }
        })
    }
}