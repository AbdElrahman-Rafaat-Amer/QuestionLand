package com.abdelrahman.rafaat.quizland.model

import android.app.Application
import android.util.Log
import androidx.preference.PreferenceManager
import com.abdelrahman.rafaat.quizland.network.RemoteSource
import retrofit2.Response

private const val TAG = "Repository"

class Repository private constructor(
    private var remoteSource: RemoteSource, private var application: Application
) : RepositoryInterface {

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

    companion object {
        private var instance: Repository? = null
        fun getRepositoryInstance(
            remoteSource: RemoteSource,
            application: Application
        ): Repository {
            if (instance == null)
                instance = Repository(remoteSource, application)
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

}