package com.abdelrahman.rafaat.quizland.model

import retrofit2.Response

interface RepositoryInterface {

/*    suspend fun getQuestions(
        amount: Int,
        category: String,
        difficulty: String,
        type: String
    ): Response<QuestionModel>*/

    suspend fun getQuestions(): Response<QuestionModel>
}