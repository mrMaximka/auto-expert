package com.mrmaximka.autoexpert.model


class QuestModel {
    lateinit var id: String
    lateinit var image: String
    lateinit var description: String
    lateinit var name: String
    lateinit var letters: String
    lateinit var lettersList: ArrayList<QuestSymbol>
    lateinit var answerList: ArrayList<QuestSymbol>
    var answer: String? = null


}