package com.abdelrahman.rafaat.quizland.model

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.abdelrahman.rafaat.quizland.R
import com.abdelrahman.rafaat.quizland.database.LocaleSource
import com.abdelrahman.rafaat.quizland.network.RemoteSource
import retrofit2.Response


private const val TAG = "Repository"

class Repository private constructor(
    private var remoteSource: RemoteSource,
    private var localeSource: LocaleSource,
    private var application: Application
) : RepositoryInterface {

    private var sharedPrefs: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    private var amount =
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
            .getString("amount", "10")!!

    private var category =
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
            .getString("category", "")!!

    private var difficulty =
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
            .getString("difficulty", "")!!

    private var type =
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
            .getString("type", "")!!

    init {
        this.sharedPrefs = application.getSharedPreferences("GameStatics", Context.MODE_PRIVATE)
        this.editor = sharedPrefs!!.edit()
    }

    companion object {
        private var instance: Repository? = null
        fun getRepositoryInstance(
            remoteSource: RemoteSource,
            localeSource: LocaleSource,
            application: Application
        ): Repository {
            if (instance == null)
                instance = Repository(remoteSource, localeSource, application)
            return instance!!
        }
    }

    private fun checkSharedPreference() {
        amount =
            PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
                .getString("amount", "10")!!

        category =
            PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
                .getString("category", "")!!

        difficulty =
            PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
                .getString("difficulty", "")!!

        type =
            PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
                .getString("type", "")!!

        Log.i(TAG, "checkSharedPreference: amount-------------> $amount")
        Log.i(TAG, "checkSharedPreference: category-----------> $category")
        Log.i(TAG, "checkSharedPreference: difficulty---------> $difficulty")
        Log.i(TAG, "checkSharedPreference: type---------------> $type")
    }

    override suspend fun getQuestions(): Response<QuestionModel> {
        checkSharedPreference()
        val response = this.remoteSource.getQuestions(
            amount = amount.toInt(),
            category = category,
            difficulty = difficulty,
            type = type
        )
        Log.i(TAG, "getQuestions: code--------------> ${response.code()}")
        Log.i(TAG, "getQuestions: res_code----------> ${response.body()?.response_code}")
        Log.i(TAG, "getQuestions: res_size----------> ${response.body()?.results!!.size}")
        Log.i(TAG, "getQuestions: res_size----------> ${response.body()?.results}")

        return response
    }

    override suspend fun getQuestionsFromDataBase(): List<Question>? {
        val response = this.localeSource.getQuestionsFromDataBase()
        Log.i(TAG, "getQuestionsFromDataBase: -----------------------> $response")
        return response
    }

    override suspend fun insertQuestionsToRoom(questions: List<Question>) {
        Log.i(TAG, "insertQuestionsToRoom: ----------------------")
        this.localeSource.insertQuestionsToRoom(questions)
    }

    override suspend fun updateGameStatics(
        totalQuestions: Int,
        multipleQuestion: Int,
        correctAnswer: Int
    ) {

        var (totalOldQuestions, multipleOldQuestions, correctOldAnswers) = getSharedResult()
        totalOldQuestions += totalQuestions
        multipleOldQuestions += multipleQuestion
        correctOldAnswers += correctAnswer

        editor?.putInt("TOTAL_QUESTIONS", totalOldQuestions)
        editor?.putInt("MULTIPLE_QUESTIONS", multipleOldQuestions)
        editor?.putInt("CORRECT_ANSWER", correctOldAnswers)
        editor?.apply()
    }

    override suspend fun updateUserName(userName: String) {
        editor?.putString("USER_NAME", userName)
        editor?.apply()
    }

    override suspend fun getSharedResult(): SharedValue {

        val totalQuestions = sharedPrefs?.getInt("TOTAL_QUESTIONS", 0)!!
        val multipleQuestion = sharedPrefs?.getInt("MULTIPLE_QUESTIONS", 0)!!
        val correctAnswer = sharedPrefs?.getInt("CORRECT_ANSWER", 0)!!
        val userName =
            sharedPrefs?.getString("USER_NAME", application.getString(R.string.user_name))!!

        return SharedValue(totalQuestions, multipleQuestion, correctAnswer, userName)

    }

}