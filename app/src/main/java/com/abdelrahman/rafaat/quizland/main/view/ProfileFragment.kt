package com.abdelrahman.rafaat.quizland.main.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.abdelrahman.rafaat.quizland.R
import com.abdelrahman.rafaat.quizland.database.ConcreteLocaleSource
import com.abdelrahman.rafaat.quizland.databinding.FragmentProfileBinding
import com.abdelrahman.rafaat.quizland.main.viewmodel.MainViewModel
import com.abdelrahman.rafaat.quizland.main.viewmodel.MainViewModelFactory
import com.abdelrahman.rafaat.quizland.model.Repository
import com.abdelrahman.rafaat.quizland.network.QuizClient
import com.abdelrahman.rafaat.quizland.playing.view.PlayingActivity
import com.abdelrahman.rafaat.quizland.playing.viewmodel.QuestionViewModel
import com.abdelrahman.rafaat.quizland.playing.viewmodel.QuestionViewModelFactory
import com.google.android.material.snackbar.Snackbar


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        observeViewModel()

        binding.circleImageView.setOnClickListener {
            //Open gallery and get Image from it
        }

        // Do not forget to write a user name Code
        binding.userNameTextView.setOnClickListener {
            showSnackBar()
        }

        binding.playButton.setOnClickListener {
            requireActivity().startActivity(Intent(requireContext(), PlayingActivity::class.java))
        }

        binding.totalQuestionTextView.text = "80"
        binding.multipleQuestionTextView.text = "50"
        binding.booleanQuestionTextView.text = "30"
        binding.correctlyQuestionTextView.text = "60"
        binding.incorrectlyQuestionTextView.text = "20"
        binding.finalScoreTextView.text = "90"


    }


    private fun initViewModel() {
        viewModelFactory = MainViewModelFactory(
            Repository.getRepositoryInstance(
                QuizClient.getQuizClient(),
                ConcreteLocaleSource.getInstance(requireContext()),
                requireActivity().application
            ), requireActivity().application
        )

        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[MainViewModel::class.java]

        viewModel.getSharedResult()
    }

    private fun observeViewModel() {
        viewModel.gameStatics.observe(viewLifecycleOwner) {
            val (totalOldQuestions, multipleOldQuestions, correctOldAnswers) = it

            binding.totalQuestionTextView.text = totalOldQuestions.toString()
            binding.multipleQuestionTextView.text = multipleOldQuestions.toString()
            binding.booleanQuestionTextView.text =
                (totalOldQuestions - multipleOldQuestions).toString()
            binding.correctlyQuestionTextView.text = correctOldAnswers.toString()
            binding.incorrectlyQuestionTextView.text =
                (totalOldQuestions - correctOldAnswers).toString()
            binding.finalScoreTextView.text = correctOldAnswers.toString()
        }
    }

    private fun showSnackBar() {
        val snackBar = Snackbar.make(
            binding.rootView,
            getString(R.string.change_name),
            Snackbar.LENGTH_SHORT
        ).setActionTextColor(Color.WHITE)

        snackBar.view.setBackgroundColor(Color.RED)
        snackBar.show()
    }

}