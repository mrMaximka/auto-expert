package com.mrmaximka.autoexpert.model

class CategoriesSize {

    private lateinit var parts: String
    private lateinit var logo: String

    fun getParts() : String{
        return parts
    }

    fun setParts(value: String){
        this.parts = value
    }

    fun getLogo() : String{
        return logo
    }

    fun setLogo(value: String){
        this.logo = value
    }

    companion object{
        val instance = CategoriesSize()
    }
}