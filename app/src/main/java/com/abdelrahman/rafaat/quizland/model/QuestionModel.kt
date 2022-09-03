package com.abdelrahman.rafaat.quizland.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.abdelrahman.rafaat.quizland.database.DataConverter
import java.io.Serializable

data class QuestionModel(
    val response_code: Int,
    val results: List<Question>
)
@TypeConverters(DataConverter::class)
@Entity(tableName = "questions")
data class Question(
    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "correct_answer")
    val correct_answer: String,

    @ColumnInfo(name = "difficulty")
    val difficulty: String,

    @TypeConverters(DataConverter::class)
    @ColumnInfo(name = "incorrect_answers")
    val incorrect_answers: List<String>,

    @ColumnInfo(name = "question")
    @PrimaryKey
    val question: String,

    @ColumnInfo(name = "type")
    val type: String
): Serializable
