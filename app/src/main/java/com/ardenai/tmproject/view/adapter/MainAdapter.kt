package com.ardenai.tmproject.view.adapter

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardenai.tmproject.R
import com.ardenai.tmproject.model.listDataClasses.CardSummary
import com.ardenai.tmproject.model.listDataClasses.TextSummary
import com.squareup.picasso.Picasso

class MainAdapter(
    private var summaries: MutableList<CardSummary>,
    private var recyclerWidth: Float
) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.image.visibility = View.GONE
        holder.title.text = ""
        holder.desc.text = ""

        summaries[position].card.title?.let {
            setUpTextView(holder.title, it)
        }

        summaries[position].card.description?.let {
            setUpTextView(holder.desc, it)
        }

        summaries[position].card.image?.let {
            val resizeRatio = (recyclerWidth / it.size.width)
            //not worrying about preloading images or fail state images this time.
            Picasso.get().load(Uri.parse(summaries[position].card.image?.url))
                .resize(
                    (it.size.width * resizeRatio).toInt(),
                    (it.size.height * resizeRatio).toInt()
                )
                .into(holder.image)
            holder.image.visibility = View.VISIBLE
        }
    }

    private fun setUpTextView(
        holder: TextView,
        summary: TextSummary
    ) {
        holder.text = summary.value
        holder.textSize = summary.attributes.font.size
        holder.setTextColor(Color.parseColor(summary.attributes.text_color))
    }

    override fun getItemCount(): Int = summaries.size

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView
        val title: TextView
        val desc: TextView

        init {
            image = view.findViewById(R.id.item_image)
            title = view.findViewById(R.id.item_title)
            desc = view.findViewById(R.id.item_desc)
        }
    }
}