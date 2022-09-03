package com.abdelrahman.rafaat.quizland.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.abdelrahman.rafaat.quizland.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}