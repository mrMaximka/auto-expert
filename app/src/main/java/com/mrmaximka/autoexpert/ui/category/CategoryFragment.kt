package com.mrmaximka.autoexpert.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mrmaximka.autoexpert.MainActivity
import com.mrmaximka.autoexpert.R
import com.mrmaximka.autoexpert.model.CategoriesSize
import com.mrmaximka.autoexpert.model.UserModel
import com.mrmaximka.autoexpert.utils.NavigationUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment() {

    private lateinit var viewModel: CategoryViewModel
    private var bundle: Bundle? = null
    private lateinit var categoriesSize: CategoriesSize
    private lateinit var userModel: UserModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModel.needOpen.observe(viewLifecycleOwner, Observer {
            it?.let {
                NavigationUtils.observerNavigation(
                    requireActivity(),
                    it,
                    viewModel.needOpen,
                    R.id.action_categoryFragment_to_listFragment,
                    bundle
                )
            }
        })

        calculateAnswers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createList()

    }

    private fun calculateAnswers() {
        categoriesSize = CategoriesSize.instance
        userModel = UserModel.instance
        var partsAnswer = 0
        var logoAnswer = 0

        val calculate = Runnable {
            partsAnswer = viewModel.calculateAnswer(userModel.getParts())
            logoAnswer = viewModel.calculateAnswer(userModel.getLogo())
        }

        val calculateObserver = object : CompletableObserver {
            override fun onComplete() {
                category_parts_result.text = String.format(getString(R.string.category_answers), partsAnswer, categoriesSize.getParts())
                category_logo_result.text = String.format(getString(R.string.category_answers), logoAnswer, categoriesSize.getLogo())
            }

            override fun onSubscribe(d: Disposable?) {}

            override fun onError(e: Throwable?) {}

        }

        Completable.fromRunnable(calculate)
            .observeOn(Schedulers.computation())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeWith(calculateObserver)
    }

    private fun createList() {
        category_card_parts.setOnClickListener { onViewClick(1) }
        category_card_logo.setOnClickListener { onViewClick(2) }
    }

    private fun onViewClick(categoryId: Int) {
        bundle = Bundle()
        bundle!!.putInt("categoryId", categoryId)
        viewModel.onCategoryClick()
    }
}
