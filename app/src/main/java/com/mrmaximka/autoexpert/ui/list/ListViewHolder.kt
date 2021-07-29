package com.mrmaximka.autoexpert.ui.list

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mrmaximka.autoexpert.R
import com.mrmaximka.autoexpert.model.QuestModel
import kotlinx.android.synthetic.main.item_list.view.*

class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var model: QuestModel
    private lateinit var elementClick: ListAdapter.OnClick
    private lateinit var context: Context

    fun bind(
        quest: QuestModel,
        elementClick: ListAdapter.OnClick,
        width: Int,
        position: Int,
        modelId: Int,
        context: Context
    ) {
        this.model = quest
        this.elementClick = elementClick
        this.context = context
        when (modelId) {
            1 -> {
                val color = ContextCompat.getColor(this.context, R.color.colorAccent)
                itemView.element_card.setCardBackgroundColor(color)
            }
            2 -> {
                val color = ContextCompat.getColor(this.context, R.color.darkRed)
                itemView.element_card.setCardBackgroundColor(color)
            }
            else -> {
                val color = ContextCompat.getColor(this.context, R.color.white)
                itemView.element_card.setCardBackgroundColor(color)
            }
        }

        itemView.element_list_container.layoutParams.width = width/4
        itemView.element_list_container.layoutParams.height = width/4
        itemView.element_image.layoutParams.width = (width/4 - (2*width/100))/10*7
        itemView.element_image.layoutParams.height = (width/4 - (2*width/100))/10*7
        itemView.element_list_container.setPadding(
            width / 100,
            width / 100,
            width / 100,
            width / 100)

        val image: ImageView = itemView.findViewById(R.id.element_image)

        Glide.with(context)
            .load(model.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.loading)
            .into(image)
//        Picasso.get().load(model.image).into(itemView.element_image)

        itemView.setOnClickListener { elementClick.onElementClick(position) }
    }

}