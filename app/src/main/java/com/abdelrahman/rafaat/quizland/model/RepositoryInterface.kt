package com.abdelrahman.rafaat.quizland.model

import android.net.Uri
import retrofit2.Response
import java.net.URI

interface RepositoryInterface {

    suspend fun getQuestions(): Response<QuestionModel>

    //DataBase
    suspend fun getQuestionsFromDataBase(): List<Question>?
    suspend fun insertQuestionsToRoom(questions: List<Question>)

    //Shared
    suspend fun updateGameStatics(totalQuestions: Int, multipleQuestion: Int, correctAnswer: Int)
    suspend fun updateUserName(userName: String)
    suspend fun getSharedResult(): SharedValue
}