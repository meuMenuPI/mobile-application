package com.example.mobile_application.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_application.R
import com.example.mobile_application.models.RestauranteReviewDto

class MiniRestauranteAdapter(
    val lista: List<RestauranteReviewDto>?
) : RecyclerView.Adapter<MiniRestauranteAdapter.MiniRestauranteHolder>() {
    inner class MiniRestauranteHolder(val layoutCard : View) : RecyclerView.ViewHolder(layoutCard){
        fun adaptar(MiniRestaurante : RestauranteReviewDto){
            layoutCard.findViewById<TextView>(R.id.nome_restaurante).text = MiniRestaurante.nome
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