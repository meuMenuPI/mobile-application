package com.example.mobile_application.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_application.databinding.RecycleCardapioBinding
import com.example.mobile_application.databinding.RecycleReviewBinding
import com.example.mobile_application.models.Cardapio
import com.example.mobile_application.models.Review

class ReviewAdapter(
    val lista: List<Review>?,
    private val context: Context

) : RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {

    inner class ReviewHolder(private val binding: RecycleReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun adaptar(review : Review) {

            binding.tvNome.text = review.nome
            binding.tvDescricao.text = review.descricao
            binding.tvTempo.text = review.data_hora
            binding.rbEstrelas.rating = ((review.nt_atendimento + review.nt_ambiente + review.nt_comida) / 2).toFloat()
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewAdapter.ReviewHolder {
        val binding =
            RecycleReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ReviewAdapter.ReviewHolder,
        position: Int
    ) {
        holder.adaptar(lista!![position])
    }

    override fun getItemCount(): Int {
        return lista?.size ?: 0
    }

}
