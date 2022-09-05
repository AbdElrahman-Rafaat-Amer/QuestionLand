package com.abdelrahman.rafaat.quizland.main.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.rafaat.quizland.model.RepositoryInterface
import com.abdelrahman.rafaat.quizland.model.SharedValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val TAG = "QuestionViewModel"

class MainViewModel(private val _iRepo: RepositoryInterface, var application: Application) :
    ViewModel() {

    private var _gameStatics = MutableLiveData<SharedValue>()
    val gameStatics: LiveData<SharedValue> = _gameStatics

    init {
        Log.i(TAG, "init: ")
    }

    fun getSharedResult() {
        viewModelScope.launch {
            val response = _iRepo.getSharedResult()
            withContext(Dispatchers.Main) {
                _gameStatics.postValue(response)
            }
        }
    }

    fun updateUserName(userName: String) {
        viewModelScope.launch {
            _iRepo.updateUserName(userName)
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ")
    }
}