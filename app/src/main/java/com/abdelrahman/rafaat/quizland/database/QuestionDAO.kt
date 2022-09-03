package com.abdelrahman.rafaat.quizland.database

import androidx.room.*
import com.abdelrahman.rafaat.quizland.model.Question

@Dao
interface QuestionDAO {

    @Query("SELECT * From questions")
    suspend fun getStoredQuestions(): List<Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionsToRoom(questions: List<Question>)


}