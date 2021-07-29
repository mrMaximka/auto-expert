package com.mrmaximka.autoexpert.ui.start

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
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

    private lateinit var viewModel: StartViewModel
    private lateinit var userModel: UserModel
    private lateinit var categoriesSize: CategoriesSize

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userModel = UserModel.instance
        categoriesSize = CategoriesSize.instance
        setViewSettings()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StartViewModel::class.java)

        userModel.email.observe(viewLifecycleOwner, Observer {
            it?.let {
                setUserSettings()
            }
        })

        viewModel.needStart.observe(viewLifecycleOwner, Observer {
            it?.let {
                NavigationUtils.observerNavigation(
                    requireActivity(),
                    it,
                    viewModel.needStart,
                    R.id.action_startFragment_to_categoryFragment
                )
            }
        })
//        calculateAnswers()
    }

    /*private fun calculateAnswers() {
        var partsAnswer = 0
        var logoAnswer = 0

        val calculate = Runnable {
            partsAnswer = viewModel.calculateAnswer(userModel.getParts())
            logoAnswer = viewModel.calculateAnswer(userModel.getLogo())
        }

        val calculateObserver = object : CompletableObserver {
            override fun onComplete() {
                val allQuests = categoriesSize.getParts().toInt() + categoriesSize.getLogo().toInt()
                val userAnswer = partsAnswer + logoAnswer
//                start_result.text = "Отгадано $userAnswer / $allQuests"
                start_result.text = "ffffffffffffffffffffffff"
            }

            override fun onSubscribe(d: Disposable?) {}

            override fun onError(e: Throwable?) {
                Log.d("MMV", "Start result error")
            }

        }

        Completable.fromRunnable(calculate)
            .observeOn(Schedulers.computation())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeWith(calculateObserver)
    }*/

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
    private fun setViewSettings() {

        google_sign_in_btn.setOnClickListener { userModel.needToLogin.value = true }
        google_sign_out_btn.setOnClickListener { userModel.needToLogOut.value = true }
        btn_exit.setOnClickListener { activity?.onBackPressed() }
        btn_start.setOnClickListener { onStartClick() }
    }

    private fun onStartClick() {
        viewModel.onStartClick()
    }

    private fun setUserSettings(){
        if (userModel.email.value!!.isNotEmpty()){
            google_sign_in_btn.visibility = View.GONE
            val name = userModel.getName()
            if (name.isNotEmpty()) start_welcome.text = String.format(getString(R.string.start_welcome), name)
            start_welcome.visibility = View.VISIBLE
            google_sign_out_btn.visibility = View.VISIBLE
        }else{
            google_sign_in_btn.visibility = View.VISIBLE
            start_welcome.visibility = View.INVISIBLE
            google_sign_out_btn.visibility = View.GONE
        }
    }

}
