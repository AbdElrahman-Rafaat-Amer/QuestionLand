package com.abdelrahman.rafaat.quizland.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdelrahman.rafaat.quizland.model.Question


@Database(
    entities = [Question::class],
    version = 1
)
//@TypeConverters(DataConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun weatherDao(): QuestionDAO

    companion object {
        private var instance: AppDataBase? = null

        @Synchronized
        fun getInstance(context: Context): AppDataBase {

            return instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "history"
            ).fallbackToDestructiveMigration().build()

        }
    }
}