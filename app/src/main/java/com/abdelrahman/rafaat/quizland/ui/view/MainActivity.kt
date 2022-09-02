package com.abdelrahman.rafaat.quizland.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdelrahman.rafaat.quizland.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, MainFragment())
            .addToBackStack(null).commit()
    }
}