package com.mrmaximka.autoexpert.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {

    var needOpen = MutableLiveData<Boolean>().apply { value = false }

    fun onCategoryClick(){
        needOpen.value = true
    }

    fun calculateAnswer(list: ArrayList<Int>): Int{
        var answers = 0
        list.forEach {
                i: Int -> if (i == 1) answers++
        }

        return answers
    }


}