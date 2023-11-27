package com.example.mobile_application.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_application.R
import com.example.mobile_application.models.RestauranteReviewDto

class MiniUfAdapter(
    val lista: List<RestauranteReviewDto>?,
    val onClick : (RestauranteReviewDto) -> Unit
): RecyclerView.Adapter<MiniUfAdapter.MiniRestauranteHolder>() {

    inner class MiniRestauranteHolder(val layoutCard : View) : RecyclerView.ViewHolder(layoutCard){
        fun adaptar(MiniRestaurante : RestauranteReviewDto){
            layoutCard.findViewById<TextView>(R.id.nome_restaurante).text = MiniRestaurante.nome
            layoutCard.findViewById<ConstraintLayout>(R.id.layoutGeral).setOnClickListener{
                onClick(MiniRestaurante)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniUfAdapter.MiniRestauranteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mini_restaurante, parent,false)
        return MiniRestauranteHolder(view)
    }

    override fun onBindViewHolder(holder: MiniUfAdapter.MiniRestauranteHolder, position: Int) {
        holder.adaptar(lista!![position])
    }

    override fun getItemCount(): Int {
        return lista!!.size
    }
}