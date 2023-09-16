package com.abdelrahman.rafaat.quizland.database

import com.abdelrahman.rafaat.quizland.model.Question

interface LocaleSource {

    suspend fun getQuestionsFromDataBase(): List<Question>?
    suspend fun insertQuestionsToRoom(questions: List<Question>)

}