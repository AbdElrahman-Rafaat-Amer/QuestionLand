package com.abdelrahman.rafaat.quizland.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DataConverter {
    @TypeConverter
    fun incorrectAnswerListToString(incorrectAnswers: List<String>?): String? {
        if (incorrectAnswers == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(incorrectAnswers, type)
    }

    @TypeConverter
    fun incorrectAnswerStringToList(incorrectAnswers: String?): List<String>? {
        if (incorrectAnswers == null) {
            return emptyList()
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(incorrectAnswers, type)
    }

}