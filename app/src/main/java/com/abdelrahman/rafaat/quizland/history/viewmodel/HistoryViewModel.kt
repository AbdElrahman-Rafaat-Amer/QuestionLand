package com.abdelrahman.rafaat.quizland.history.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.rafaat.quizland.model.Question
import com.abdelrahman.rafaat.quizland.model.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(private val _iRepo: RepositoryInterface, var application: Application) :
    ViewModel() {

    private var _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> = _questions

    fun getQuestions() {
        viewModelScope.launch {
            val response = _iRepo.getQuestionsFromDataBase()
            withContext(Dispatchers.Main) {
                if (response!!.isNotEmpty()) {
                    _questions.postValue(response!!)
                } else {
                    _questions.postValue(emptyList())
                }
            }
        }
    }
}