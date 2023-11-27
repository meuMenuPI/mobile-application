package com.example.mobile_application.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_application.R
import com.example.mobile_application.RestauranteReview
import com.example.mobile_application.models.RestauranteReviewDto

class MiniRestauranteAdapter(
    val lista: List<RestauranteReviewDto>?,
    val onClick : (RestauranteReviewDto) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<MiniRestauranteAdapter.MiniRestauranteHolder>() {

    val link = "https://meumenuimagens.blob.core.windows.net/restaurante/"
    inner class MiniRestauranteHolder(val layoutCard : View) : RecyclerView.ViewHolder(layoutCard){
        fun adaptar(MiniRestaurante : RestauranteReviewDto){

            Glide.with(context)
                .load(link + MiniRestaurante.nomeFoto)
                .into(layoutCard.findViewById<ImageView>(R.id.imageView4))

            layoutCard.findViewById<TextView>(R.id.nome_restaurante).text = MiniRestaurante.nome
            layoutCard.findViewById<ConstraintLayout>(R.id.layoutGeral).setOnClickListener{
                onClick(MiniRestaurante)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniRestauranteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mini_restaurante, parent,false)
        return MiniRestauranteHolder(view)
    }

    override fun onBindViewHolder(holder: MiniRestauranteHolder, position: Int) {
        holder.adaptar(lista!![position])
    }

    override fun getItemCount(): Int {
        return lista!!.size
    }
}