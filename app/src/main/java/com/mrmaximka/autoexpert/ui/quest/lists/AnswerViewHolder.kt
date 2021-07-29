package com.mrmaximka.autoexpert.ui.quest.lists

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mrmaximka.autoexpert.R
import com.mrmaximka.autoexpert.model.QuestSymbol
import kotlinx.android.synthetic.main.item_quest_symbol.view.*

class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var model: QuestSymbol
    private lateinit var listener: AnswerAdapter.Listener
    private  var width: Int = 0
    private lateinit var context: Context

    fun bind(
        questSymbol: QuestSymbol,
        position: Int,
        listener: AnswerAdapter.Listener,
        width: Int,
        context: Context
    ) {

        this.model = questSymbol
        this.listener = listener
        this.width = width
        this.context = context

        itemView.symbol_container.layoutParams.width = width / 9
        itemView.symbol_container.layoutParams.height = width / 9
        itemView.symbol_container.setPadding(width / 150, width / 150, width / 150, width / 150)
        itemView.quest_symbol.setTextSize(TypedValue.COMPLEX_UNIT_PX, (width/20).toFloat())

        when {
            model.getName().equals(" ") -> {
                itemView.symbol_card_view.visibility = View.GONE
                model.setAnswer(model.getName())
            }
            model.getAnswer() == null -> {
                itemView.quest_symbol.visibility = View.INVISIBLE
                itemView.symbol_card_view.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray))
                itemView.symbol_card_view.visibility = View.VISIBLE
                itemView.symbol_card_view.elevation = 0F
            }
            else -> {
                itemView.quest_symbol.text = model.getAnswer()
                itemView.quest_symbol.visibility = View.VISIBLE
                itemView.symbol_card_view.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                itemView.symbol_card_view.visibility = View.VISIBLE
                itemView.symbol_card_view.elevation = 5F
            }
        }

        itemView.setOnClickListener {
            if (listener != null && !model.getName().equals(" ")){
                listener.onAnswerClick(model, position)
            }
        }

    }
}