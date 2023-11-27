package com.example.mobile_application.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_application.R


class MiniEspecialidadeAdapter(
    val lista: List<String>?,
    val onClick: (String) -> Unit
) : RecyclerView.Adapter<MiniEspecialidadeAdapter.MiniRestauranteHolder>() {
    inner class MiniRestauranteHolder(val layoutCard : View) : RecyclerView.ViewHolder(layoutCard){
        fun adaptar(MiniRestaurante : String){
            layoutCard.findViewById<TextView>(R.id.nome_restaurante).text = MiniRestaurante
            layoutCard.findViewById<ConstraintLayout>(R.id.layoutGeral).setOnClickListener{
                onClick(MiniRestaurante)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniEspecialidadeAdapter.MiniRestauranteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mini_restaurante, parent,false)
        return MiniRestauranteHolder(view)
    }

    override fun onBindViewHolder(holder: MiniEspecialidadeAdapter.MiniRestauranteHolder, position: Int) {
        holder.adaptar(lista!![position])
    }

    override fun getItemCount(): Int {
        return lista!!.size
    }
}