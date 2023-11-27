package com.example.mobile_application.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_application.R
import com.example.mobile_application.databinding.FotoRestauranteBinding
import com.example.mobile_application.databinding.RecycleCardapioBinding
import com.example.mobile_application.models.Cardapio
import com.example.mobile_application.models.FotoRestaurante

class CardapioAdapter(
    val lista: List<Cardapio>?,
    private val context: Context

) : RecyclerView.Adapter<CardapioAdapter.CardapioHolder>() {

    val link = "https://meumenuimagens.blob.core.windows.net/foto-suario/"

    inner class CardapioHolder(private val binding: RecycleCardapioBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun adaptar(prato : Cardapio) {

            binding.tvNomePrato.text = prato.nome
            binding.tvDescricaoPrato.text = prato.descricao
            binding.tvPreco.text = ("R$${prato.preco.toString()}")
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardapioAdapter.CardapioHolder {
        val binding =
            RecycleCardapioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardapioHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CardapioAdapter.CardapioHolder,
        position: Int
    ) {
        holder.adaptar(lista!![position])
    }

    override fun getItemCount(): Int {
        return lista?.size ?: 0
    }
}