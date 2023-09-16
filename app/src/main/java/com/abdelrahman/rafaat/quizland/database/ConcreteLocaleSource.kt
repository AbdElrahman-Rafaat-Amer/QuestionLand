package com.abdelrahman.rafaat.quizland.database

import android.content.Context
import com.abdelrahman.rafaat.quizland.model.Question

class ConcreteLocaleSource(context: Context) : LocaleSource {

    private var weatherDao: QuestionDAO?

    init {
        val dataBase = AppDataBase.getInstance(context.applicationContext)
        weatherDao = dataBase.weatherDao()
    }

    companion object {
        private var sourceConcreteClass: ConcreteLocaleSource? = null
        fun getInstance(context: Context): ConcreteLocaleSource {
            if (sourceConcreteClass == null)
                sourceConcreteClass = ConcreteLocaleSource(context)
            return sourceConcreteClass as ConcreteLocaleSource
        }
    }

    override suspend fun getQuestionsFromDataBase(): List<Question> {
        val response = weatherDao?.getStoredQuestions()!!
        return response
    }


    override suspend fun insertQuestionsToRoom(questions: List<Question>) {
        weatherDao?.insertQuestionsToRoom(questions)
    }

}