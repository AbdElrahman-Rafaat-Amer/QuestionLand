package com.abdelrahman.rafaat.quizland.network

import com.abdelrahman.rafaat.quizland.model.QuestionModel
import retrofit2.Response

interface RemoteSource {

    suspend fun getQuestions(
        amount: Int,
        category: String,
        difficulty: String,
        type: String
    ): Response<QuestionModel>

}