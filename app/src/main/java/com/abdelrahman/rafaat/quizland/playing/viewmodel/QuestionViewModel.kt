package com.abdelrahman.rafaat.quizland.playing.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.rafaat.quizland.model.Question
import com.abdelrahman.rafaat.quizland.model.QuestionModel
import com.abdelrahman.rafaat.quizland.model.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val TAG = "QuestionViewModel"

class QuestionViewModel(private val _iRepo: RepositoryInterface, var application: Application) :
    ViewModel() {

    private var _question = MutableLiveData<QuestionModel>()
    val question: LiveData<QuestionModel> = _question

    init {
        Log.i(TAG, "init: ")
    }

    fun getQuestions() {
        viewModelScope.launch {
            val response = _iRepo.getQuestions()
            withContext(Dispatchers.Main) {
                if (response.code() == 200) {
                    _question.postValue(response.body())
                } else {
                    Log.i(TAG, "getQuestions: code---------------> ${response.code()}")
                    Log.i(
                        TAG,
                        "getQuestions: res_code-----------> ${response.body()?.response_code}"
                    )
                    Log.i(
                        TAG,
                        "getQuestions: res_size-----------> ${response.body()?.results?.size}"
                    )
                    Log.i(TAG, "getQuestions: res_body-----------> ${response.body()?.results}")
                }
            }
        }
    }

    fun updateResult(totalQuestions: Int, multipleQuestion: Int, correctAnswer: Int) {
        viewModelScope.launch {
            val response =
                _iRepo.updateGameStatics(totalQuestions, multipleQuestion, correctAnswer)
        }
    }

    fun insertQuestionsToRoom(questions: List<Question>) {
        viewModelScope.launch {
            val response = _iRepo.insertQuestionsToRoom(questions)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ")
    }


}