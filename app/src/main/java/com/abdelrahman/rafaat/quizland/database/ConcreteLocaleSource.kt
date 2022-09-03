package com.abdelrahman.rafaat.quizland.database

import android.content.Context
import android.util.Log
import com.abdelrahman.rafaat.quizland.model.Question

private const val TAG = "ConcreteLocaleSource"

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
        Log.i(TAG, "getQuestionsFromDataBase: ------------------------")
        val response = weatherDao?.getStoredQuestions()!!
        Log.i(TAG, "getQuestionsFromDataBase: ------------------> $response")
        return response
    }


    override suspend fun insertQuestionsToRoom(questions: List<Question>) {

        Log.i(TAG, "insertQuestionsToRoom: -----------------------------")
        weatherDao?.insertQuestionsToRoom(questions)

    }

}