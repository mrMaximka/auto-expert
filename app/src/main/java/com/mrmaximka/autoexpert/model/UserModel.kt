package com.mrmaximka.autoexpert.model

import androidx.lifecycle.MutableLiveData

class UserModel {
    private var name: String = ""
//    private lateinit var image: String
    private lateinit var coins: String
    private lateinit var uid: String
    var email: MutableLiveData<String> = MutableLiveData<String>().apply { value = null }
    var needToLogin: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    var needToLogOut: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }

    private var parts: ArrayList<Int> = ArrayList()
    private var logo: ArrayList<Int> = ArrayList()

    fun getName(): String{
        return name
    }

    fun setName(name: String){
        this.name = name
    }

    /*fun getImage(): String{
        return image
    }

    fun setImage(image: String){
        this.image = image
    }*/

    fun getUid(): String{
        return uid
    }

    fun setUid(uid: String){
        this.uid = uid
    }

    fun setCoins(coins: String){
        this.coins = coins
    }

    fun getCoins(): Int{
        return coins.toInt()
    }

    fun getPartValue(part: Int, collection: String): Int{
        when(collection){
            "parts" -> return parts[part]
            "logo" -> return logo[part]
        }
        return parts[part]
    }

    fun setParts(list: ArrayList<Int>){
        parts = list
    }

    fun setLogo(list: ArrayList<Int>){
        logo = list
    }

    fun getParts(): ArrayList<Int>{
        return parts
    }

    fun getLogo(): ArrayList<Int>{
        return logo
    }

    companion object{
        val instance = UserModel()
    }
}