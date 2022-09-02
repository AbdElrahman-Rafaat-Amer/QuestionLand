package com.abdelrahman.rafaat.quizland.ui.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.abdelrahman.rafaat.quizland.R
import com.abdelrahman.rafaat.quizland.databinding.FragmentPlayingBinding
import com.abdelrahman.rafaat.quizland.model.Repository
import com.abdelrahman.rafaat.quizland.model.Result
import com.abdelrahman.rafaat.quizland.network.QuizClient
import com.abdelrahman.rafaat.quizland.ui.viewmodel.QuestionViewModel
import com.abdelrahman.rafaat.quizland.ui.viewmodel.ViewModelFactory
import com.abdelrahman.rafaat.quizland.utils.ConnectionLiveData

private const val TAG = "PlayingFragment"

class PlayingFragment : Fragment() {

    private lateinit var binding: FragmentPlayingBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: QuestionViewModel
    private lateinit var questions: List<Result>
    private var questionNumber = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkConnection()
        initViewModel()
        observeViewModel()

        binding.nextButton.setOnClickListener {
            if (questionNumber < questions.size - 1) {
                questionNumber++
                showQuestions()
            } else {
                Log.i(TAG, "onViewCreated: you reach the end of questions")
                Toast.makeText(requireContext(), "Your result is ", Toast.LENGTH_SHORT).show()
            }
        }

        binding.firstAnswer.setOnClickListener {
            if (binding.firstAnswer.text == questions[questionNumber].correct_answer) {
                binding.firstAnswer.setBackgroundColor(Color.GREEN)
                binding.nextButton.isEnabled = true
            } else
                binding.firstAnswer.setBackgroundColor(Color.RED)
        }

        binding.secondAnswer.setOnClickListener {
            if (binding.secondAnswer.text == questions[questionNumber].correct_answer) {
                binding.secondAnswer.setBackgroundColor(Color.GREEN)
                binding.nextButton.isEnabled = true
            } else
                binding.secondAnswer.setBackgroundColor(Color.RED)
        }

        binding.thirdAnswer.setOnClickListener {
            if (binding.thirdAnswer.text == questions[questionNumber].correct_answer) {
                binding.thirdAnswer.setBackgroundColor(Color.GREEN)
                binding.nextButton.isEnabled = true
            } else
                binding.thirdAnswer.setBackgroundColor(Color.RED)
        }

        binding.fourthAnswer.setOnClickListener {
            if (binding.fourthAnswer.text == questions[questionNumber].correct_answer) {
                binding.fourthAnswer.setBackgroundColor(Color.GREEN)
                binding.nextButton.isEnabled = true
            } else
                binding.fourthAnswer.setBackgroundColor(Color.RED)
        }


    }

    private fun checkConnection() {
        ConnectionLiveData.getInstance(requireContext()).observe(viewLifecycleOwner) {
            if (it) {
                viewModel.getQuestions()
                binding.noConnectionLayout.noInternetAnimation.visibility = View.GONE
                binding.noConnectionLayout.enableConnection.visibility = View.GONE
                binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.VISIBLE
                binding.shimmerAnimationLayout.shimmerFrameLayout.startShimmerAnimation()
            } else {
                Log.i(TAG, "checkConnection: no internet connection")
                binding.noConnectionLayout.noInternetAnimation.visibility = View.VISIBLE
                binding.noConnectionLayout.enableConnection.visibility = View.VISIBLE
                binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.GONE
                binding.shimmerAnimationLayout.shimmerFrameLayout.stopShimmerAnimation()
            }
        }
    }

    private fun initViewModel() {
        viewModelFactory = ViewModelFactory(
            Repository.getRepositoryInstance(
                QuizClient.getQuizClient(), requireActivity().application
            ), requireActivity().application
        )

        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[QuestionViewModel::class.java]
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            if (it.response_code == 0 && it.results.isNotEmpty()) {
                Log.i(TAG, "observeViewModel: there is data")
                binding.constrainLayout.visibility = View.VISIBLE
                questions = it.results
                binding.noDataLayout.noDataAnimation.visibility = View.GONE
                binding.noDataLayout.noDataTextView.visibility = View.GONE
                showQuestions()
            } else {
                Log.i(TAG, "observeViewModel: there is no data")
                binding.constrainLayout.visibility = View.GONE
                binding.noDataLayout.noDataAnimation.visibility = View.VISIBLE
                binding.noDataLayout.noDataTextView.visibility = View.VISIBLE
            }
            binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.GONE
            binding.shimmerAnimationLayout.shimmerFrameLayout.stopShimmerAnimation()
        }
    }

    private fun showQuestions() {
        binding.questionNumber.text = "${questionNumber + 1} / ${questions.size}"
        binding.question.text = questions[questionNumber].question
        if (questions[questionNumber].incorrect_answers.size == 1) {
            binding.firstAnswer.text = resources.getString(R.string.true_answer)
            binding.secondAnswer.text = resources.getString(R.string.false_answer)
            binding.thirdAnswer.visibility = View.INVISIBLE
            binding.fourthAnswer.visibility = View.INVISIBLE
        } else {
            val randomNumbers = (0..3).shuffled()
            val questionAnswers =
                questions[questionNumber].incorrect_answers.plus(questions[questionNumber].correct_answer)
            binding.firstAnswer.text = questionAnswers[randomNumbers[0]]
            binding.secondAnswer.text = questionAnswers[randomNumbers[1]]
            binding.thirdAnswer.text = questionAnswers[randomNumbers[2]]
            binding.fourthAnswer.text = questionAnswers[randomNumbers[3]]
            binding.thirdAnswer.visibility = View.VISIBLE
            binding.fourthAnswer.visibility = View.VISIBLE
        }

        binding.firstAnswer.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_question_button_background, null)
        binding.secondAnswer.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_question_button_background, null)
        binding.thirdAnswer.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_question_button_background, null)
        binding.fourthAnswer.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_question_button_background, null)
    }

}