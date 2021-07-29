package com.mrmaximka.autoexpert.ui.quest

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mrmaximka.autoexpert.R
import com.mrmaximka.autoexpert.model.QuestSymbol
import com.mrmaximka.autoexpert.ui.quest.lists.AnswerAdapter
import com.mrmaximka.autoexpert.ui.quest.lists.LettersAdapter
import com.mrmaximka.autoexpert.utils.NavigationUtils
import kotlinx.android.synthetic.main.alert_show_description.view.*
import kotlinx.android.synthetic.main.fragment_quest.*
import kotlinx.android.synthetic.main.item_help_dialog.view.*

class QuestFragment : Fragment(), AnswerAdapter.Listener, LettersAdapter.Listener {

    private lateinit var viewModel: QuestViewModel
    private lateinit var answerAdapter: AnswerAdapter
    private lateinit var lettersAdapter: LettersAdapter
    private var width: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_quest, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestViewModel::class.java)

        loadQuest()

        viewModel.needNextQuest.observe(viewLifecycleOwner, Observer {
            it?.let {
                val bundle = Bundle()
                bundle.putInt("position", viewModel.position)
                NavigationUtils.observerNavigation(
                    requireActivity(),
                    it,
                    viewModel.needNextQuest,
                    R.id.action_questFragment_to_questFragment,
                    bundle
                )
            }
        })

        viewModel.needShowAnswer.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (viewModel.needShowAnswer.value == true){
                    viewModel.needShowAnswer.value = false

                    showAnswerDialog()
                }
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBtn()
        calcWidth()
        initLists()
    }

    private fun initBtn() {
        quest_help_btn.setOnClickListener {
            val dialog = context?.let { Dialog(it) }
            val factory = LayoutInflater.from(context)
            val view = factory.inflate(R.layout.item_help_dialog, null)
            view.help_add_btn.setOnClickListener {
                viewModel.addLetter()
                dialog?.onBackPressed()
            }
            view.help_del_btn.setOnClickListener {
                viewModel.delLetters()
                dialog?.onBackPressed()
            }
            view.help_answer_btn.setOnClickListener {
                viewModel.setAnswer()
                dialog?.onBackPressed()
            }

            dialog?.setContentView(view)
            dialog?.window?.setBackgroundDrawableResource(R.color.transparent)

            dialog?.show()
        }
    }

    private fun showAnswerDialog() {

        val answerDialog = context?.let { Dialog(it) }
        val factory = LayoutInflater.from(context)
        val view = factory.inflate(R.layout.alert_show_description, null)
        val image = viewModel.getAnswerImage()


        view.answer_text.text = viewModel.getDescription()
        view.answer_button.setOnClickListener {
            viewModel.getNextQuest()
            answerDialog?.cancel()
        }
        if (!image.isNullOrEmpty()) {
            Glide.with(requireContext())
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.loading)
                .into(view.answer_image)
        }
        answerDialog?.setContentView(view)
        answerDialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        answerDialog?.show()
    }

    private fun loadQuest() {
        val questPosition: Int = arguments?.get("position") as Int
        viewModel.loadQuest(questPosition, context, answerAdapter, lettersAdapter)

        Glide.with(requireContext())
            .load(viewModel.getImage())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.loading)
            .into(quest_fragment_image)
    }

    private fun calcWidth() {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
    }

    private fun initLists() {
        lettersAdapter = LettersAdapter(width, this)
        answerAdapter = AnswerAdapter(width, this, requireContext())

        list_answer.layoutManager = GridLayoutManager(context, 9)
        list_letters.layoutManager = GridLayoutManager(context, 9)

        list_answer.adapter = answerAdapter
        list_letters.adapter = lettersAdapter
    }

    override fun onAnswerClick(symbol: QuestSymbol, position: Int) {
        viewModel.onAnswer(symbol, position)
    }

    override fun onSymbolClick(symbol: QuestSymbol, position: Int) {
        viewModel.onSymbol(symbol, position)
    }
}