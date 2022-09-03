package com.abdelrahman.rafaat.quizland.history.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdelrahman.rafaat.quizland.model.RepositoryInterface
import java.lang.IllegalArgumentException

class HistoryViewModelFactory(
    private val _iRepo: RepositoryInterface,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            HistoryViewModel(_iRepo, application) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}