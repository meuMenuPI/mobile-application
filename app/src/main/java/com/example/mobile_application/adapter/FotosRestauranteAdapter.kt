package com.example.mobile_application.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_application.R
import com.example.mobile_application.databinding.FotoRestauranteBinding
import com.example.mobile_application.models.FotoRestaurante

class FotosRestauranteAdapter(
    val lista: List<FotoRestaurante>?,
    private val context: Context

) : RecyclerView.Adapter<FotosRestauranteAdapter.FotoRestauranteHolder>() {

    val link = "https://meumenuimagens.blob.core.windows.net/foto-suario/"

    inner class FotoRestauranteHolder(private val binding: FotoRestauranteBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun adaptar() {

        Glide.with(context)
            .load(link + getData().nomeFoto)
            .into(binding.foto)

        }

        private fun getData() = lista!![adapterPosition]
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FotosRestauranteAdapter.FotoRestauranteHolder {
        val binding =
            FotoRestauranteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FotoRestauranteHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FotosRestauranteAdapter.FotoRestauranteHolder,
        position: Int
    ) {
        holder.adaptar()
    }

    override fun getItemCount(): Int {
        return lista?.size ?: 0
    }
}