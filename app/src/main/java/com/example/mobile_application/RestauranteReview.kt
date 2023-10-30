package com.example.mobile_application

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.mobile_application.api.Rest
import com.example.mobile_application.databinding.ActivityRestauranteReviewBinding
import com.example.mobile_application.models.Review
import com.example.mobile_application.service.ReviewService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestauranteReview : AppCompatActivity() {

    private val binding by lazy {
        ActivityRestauranteReviewBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        Rest.getInstance().create(ReviewService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnVltMenu.setOnClickListener{
            val i = Intent(
                this@RestauranteReview,
                MenuPrincipal::class.java
            )
            startActivity(i)
        }

        val pref = getSharedPreferences("RESTAURANTE", MODE_PRIVATE)
        val id = pref.getInt("ID", 0)
        val nome_restaurante = pref.getString("NOME", null)
        Log.d("Id Restaurante", id.toString())
        Log.d("Nome Restaurante", nome_restaurante.toString())

        binding.textView.text = nome_restaurante.toString()

        if (id != null) {
            retrofit.getReview(id).enqueue(object : Callback<List<Review>> {
                override fun onResponse(
                    call: Call<List<Review>>,
                    response: Response<List<Review>>
                ) {

                    val reviews = response.body()
                    println("Reviews$reviews")
                    reviews?.forEach { Review ->

                        val constraintLayoutReview = ConstraintLayout(baseContext)
                        constraintLayoutReview.id = View.generateViewId()

                        val scale = resources.displayMetrics.density

                        val layoutParamsReview = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        layoutParamsReview.leftMargin = (15 * scale + 0.5f).toInt()
                        constraintLayoutReview.layoutParams = layoutParamsReview

                        val nomeView = TextView(baseContext)
                        nomeView.id = View.generateViewId()
                        val layoutParamsNome = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )
                        nomeView.text = Review.nome
                        nomeView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
                        nomeView.setTextColor(ContextCompat.getColor(baseContext, R.color.black))
                        nomeView.setTypeface(null, Typeface.BOLD)

                        layoutParamsNome.topToTop = constraintLayoutReview.id
                        layoutParamsNome.startToStart = constraintLayoutReview.id

                        val estrelas = RatingBar(baseContext)
                        estrelas.id = View.generateViewId()
                        val layoutParamsEstrela = ConstraintLayout.LayoutParams(
                            100,
                            30
                        )

                        estrelas.setPadding(20, 8, 0, 0)
                        estrelas.numStars = 5
                        estrelas.rating = 3.5f
                        estrelas.stepSize = 0.5f

                        layoutParamsEstrela.topToBottom = nomeView.id
                        layoutParamsEstrela.startToStart = nomeView.id


                        estrelas.layoutParams = layoutParamsEstrela

                        val tempo = TextView(baseContext)
                        tempo.id = View.generateViewId()
                        tempo.text = Review.data_hora
                        tempo.setTextColor(Color.parseColor("#808080"))
                        tempo.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)

                        val layoutParamsTempo = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        layoutParamsTempo.startToEnd = estrelas.id
                        layoutParamsTempo.topToTop = estrelas.id
                        tempo.layoutParams = layoutParamsTempo

                        val descricao = TextView(baseContext)
                        descricao.id = View.generateViewId()
                        descricao.text = Review.descricao
                        descricao.setTextColor(ContextCompat.getColor(baseContext, R.color.black))
                        descricao.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
                        Log.d("descrição", Review.descricao.toString())

                        val layoutParamsDescricao = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )

                        layoutParamsDescricao.topToBottom = tempo.id
                        layoutParamsDescricao.startToStart = estrelas.id

                        descricao.layoutParams = layoutParamsDescricao

                        constraintLayoutReview.addView(nomeView)
                        constraintLayoutReview.addView(estrelas)
                        constraintLayoutReview.addView(tempo)
                        constraintLayoutReview.addView(descricao)

                        val novaReviewLayout = binding.novaReview
                        novaReviewLayout.addView(constraintLayoutReview)

                    }
                }

                override fun onFailure(call: Call<List<Review>>, t: Throwable) {
                    Log.d("T_RESPONSE_ERRO", t.toString())
                }
            })
        }
    }
}