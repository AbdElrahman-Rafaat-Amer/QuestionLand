package com.abdelrahman.rafaat.quizland.playing.viewmodel

import android.app.Application
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

class QuestionViewModel(private val _iRepo: RepositoryInterface, var application: Application) :
    ViewModel() {

    private var _question = MutableLiveData<QuestionModel>()
    val question: LiveData<QuestionModel> = _question

    fun getQuestions() {
        viewModelScope.launch {
            val response = _iRepo.getQuestions()
            withContext(Dispatchers.Main) {
                if (response.code() == 200) {
                    _question.postValue(response.body())
                }
            }
        }
    }

    fun updateResult(totalQuestions: Int, multipleQuestion: Int, correctAnswer: Int) {
        viewModelScope.launch {
            _iRepo.updateGameStatics(totalQuestions, multipleQuestion, correctAnswer)
        }
    }

    fun insertQuestionsToRoom(questions: List<Question>) {
        viewModelScope.launch {
            _iRepo.insertQuestionsToRoom(questions)
        }
    }

}