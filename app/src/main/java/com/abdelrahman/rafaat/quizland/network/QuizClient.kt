package com.abdelrahman.rafaat.quizland.network

import android.util.Log
import com.abdelrahman.rafaat.quizland.model.QuestionModel
import retrofit2.Response


private const val TAG = "QuizClient"

class QuizClient private constructor() : RemoteSource {

    private val quizHelper = QuizHelper.getClient


    companion object {
        private var instance: QuizClient? = null
        fun getQuizClient(): QuizClient {
            if (instance == null)
                instance = QuizClient()
            return instance!!
        }
    }

    override suspend fun getQuestions(
        amount: Int,
        category: String,
        difficulty: String,
        type: String
    ): Response<QuestionModel> {
        val response = quizHelper.getQuestions(
            amount = amount,
            category = category,
            difficulty = difficulty,
            type = type
        )
        Log.i(TAG, "getQuestions: code----------> ${response.code()}")
        Log.i(TAG, "getQuestions: body----------> ${response.body()}")
        return response
    }


}