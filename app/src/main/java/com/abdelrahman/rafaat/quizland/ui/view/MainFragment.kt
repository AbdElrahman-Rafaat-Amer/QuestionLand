package com.abdelrahman.rafaat.quizland.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdelrahman.rafaat.quizland.R
import com.abdelrahman.rafaat.quizland.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



      /*      CoroutineScope(Dispatchers.Main).launch {
                Repository.getRepositoryInstance(
                    QuizClient.getQuizClient(), application
                ).getQuestions()
            }
*/

        binding.setting.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, SettingsFragment())
                .addToBackStack(null).commit()
        }

        binding.play.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, PlayingFragment())
                .addToBackStack(null).commit()
        }
    }
}