package com.abdelrahman.rafaat.quizland.network

import com.abdelrahman.rafaat.quizland.model.QuestionModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizService {
    @GET("api.php?")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 10,
        @Query("category") category: String,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ): Response<QuestionModel>

}