package com.ardenai.tmproject.view.adapter

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardenai.tmproject.R
import com.ardenai.tmproject.databinding.ListItemBinding
import com.ardenai.tmproject.model.listDataClasses.Card
import com.ardenai.tmproject.model.listDataClasses.CardSummary
import com.ardenai.tmproject.model.listDataClasses.TextSummary
import com.squareup.picasso.Picasso

class MainAdapter(
    private var summaries: MutableList<CardSummary>,
    private var recyclerWidth: Float
) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bind(summaries[position].card, recyclerWidth)

    override fun getItemCount(): Int = summaries.size

    class MainViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card, recyclerWidth: Float) {
            with(binding) {
                setUpTextView(itemTitle, card.title)
                setUpTextView(itemDesc, card.description)

                card.image?.let {

                    val resizeRatio = (recyclerWidth / it.size.width)
                    Picasso.get().load(Uri.parse(card.image.url))
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error)
                        .resize(
                            (it.size.width * resizeRatio).toInt(),
                            (it.size.height * resizeRatio).toInt()
                        )
                        .into(itemImage)
                    itemImage.visibility = View.VISIBLE
                }
            }
        }

        fun setUpTextView(
            holder: TextView,
            summary: TextSummary?
        ) {
            summary?.let {
                holder.visibility = View.VISIBLE
                holder.text = it.value
                holder.textSize = it.attributes.font.size
                holder.setTextColor(Color.parseColor(it.attributes.text_color))
            } ?: run {
                holder.visibility = View.GONE
            }
        }
    }
}