package com.mrmaximka.autoexpert.ui.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartViewModel : ViewModel() {

    var needStart = MutableLiveData<Boolean>().apply { value = false }

    fun onStartClick() {
        needStart.value = true
    }

    /*fun calculateAnswer(list: ArrayList<Int>): Int{
        var answers = 0
        list.forEach {
                i: Int -> if (i == 1) answers++
        }

        return answers
    }*/
}