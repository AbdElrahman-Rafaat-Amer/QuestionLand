package com.abdelrahman.rafaat.quizland.playing.view

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.abdelrahman.rafaat.quizland.R
import com.abdelrahman.rafaat.quizland.database.ConcreteLocaleSource
import com.abdelrahman.rafaat.quizland.databinding.ActivityPlayingBinding
import com.abdelrahman.rafaat.quizland.model.Question
import com.abdelrahman.rafaat.quizland.model.Repository
import com.abdelrahman.rafaat.quizland.network.QuizClient
import com.abdelrahman.rafaat.quizland.playing.viewmodel.QuestionViewModel
import com.abdelrahman.rafaat.quizland.playing.viewmodel.QuestionViewModelFactory
import com.abdelrahman.rafaat.quizland.utils.ConnectionLiveData

class PlayingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayingBinding
    private lateinit var viewModelFactory: QuestionViewModelFactory
    private lateinit var viewModel: QuestionViewModel
    private lateinit var questions: List<Question>
    private var questionNumber = 0
    private var multipleQuestion = 0
    private var correctAnswer = 0
    private var isCorrectFromFirstOne = true
    private var isDialogShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.noDataLayout.noDataTextView.text = getString(R.string.no_data_found)
        checkConnection()
        initViewModel()
        observeViewModel()

        binding.nextButton.setOnClickListener {
            if (questionNumber < questions.size - 1) {
                questionNumber++
                if (questionNumber == 9)
                    binding.nextButton.text = getString(R.string.submit)
                showQuestions()
            } else {
                calculateResult()
                showDialog()
            }
        }

        binding.firstAnswer.setOnClickListener {
            if (binding.firstAnswer.text == Html.fromHtml(
                    questions[questionNumber].correct_answer,
                    Html.FROM_HTML_MODE_COMPACT
                )
            ) {
                binding.firstAnswer.setBackgroundColor(Color.GREEN)
                binding.nextButton.visibility = View.VISIBLE
                binding.secondAnswer.isEnabled = false
                binding.thirdAnswer.isEnabled = false
                binding.fourthAnswer.isEnabled = false
                if (isCorrectFromFirstOne) {
                    correctAnswer++
                }
            } else {
                binding.firstAnswer.setBackgroundColor(Color.RED)
                isCorrectFromFirstOne = false
            }
        }

        binding.secondAnswer.setOnClickListener {
            if (binding.secondAnswer.text == Html.fromHtml(
                    questions[questionNumber].correct_answer,
                    Html.FROM_HTML_MODE_COMPACT
                )
            ) {
                binding.secondAnswer.setBackgroundColor(Color.GREEN)
                binding.nextButton.visibility = View.VISIBLE
                binding.firstAnswer.isEnabled = false
                binding.thirdAnswer.isEnabled = false
                binding.fourthAnswer.isEnabled = false
                if (isCorrectFromFirstOne) {
                    correctAnswer++
                }
            } else {
                binding.secondAnswer.setBackgroundColor(Color.RED)
                isCorrectFromFirstOne = false
            }

        }

        binding.thirdAnswer.setOnClickListener {
            if (binding.thirdAnswer.text == Html.fromHtml(
                    questions[questionNumber].correct_answer,
                    Html.FROM_HTML_MODE_COMPACT
                )
            ) {
                binding.thirdAnswer.setBackgroundColor(Color.GREEN)
                binding.nextButton.visibility = View.VISIBLE
                binding.firstAnswer.isEnabled = false
                binding.secondAnswer.isEnabled = false
                binding.fourthAnswer.isEnabled = false
                if (isCorrectFromFirstOne) {
                    correctAnswer++
                }
            } else {
                binding.thirdAnswer.setBackgroundColor(Color.RED)
                isCorrectFromFirstOne = false
            }
        }

        binding.fourthAnswer.setOnClickListener {
            if (binding.fourthAnswer.text == Html.fromHtml(
                    questions[questionNumber].correct_answer,
                    Html.FROM_HTML_MODE_COMPACT
                )
            ) {
                binding.fourthAnswer.setBackgroundColor(Color.GREEN)
                binding.nextButton.visibility = View.VISIBLE
                binding.firstAnswer.isEnabled = false
                binding.secondAnswer.isEnabled = false
                binding.thirdAnswer.isEnabled = false
                if (isCorrectFromFirstOne) {
                    correctAnswer++
                }
            } else {
                binding.fourthAnswer.setBackgroundColor(Color.RED)
                isCorrectFromFirstOne = false
            }
        }


    }

    private fun checkConnection() {
        ConnectionLiveData.getInstance(this).observe(this) {
            if (it) {
                viewModel.getQuestions()
                binding.noConnectionLayout.noInternetAnimation.visibility = View.GONE
                binding.noConnectionLayout.enableConnection.visibility = View.GONE
                binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.VISIBLE
                binding.shimmerAnimationLayout.shimmerFrameLayout.startShimmer()
            } else {
                binding.noConnectionLayout.noInternetAnimation.visibility = View.VISIBLE
                binding.noConnectionLayout.enableConnection.visibility = View.VISIBLE
                binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.GONE
                binding.shimmerAnimationLayout.shimmerFrameLayout.stopShimmer()
            }
        }
    }

    private fun initViewModel() {
        viewModelFactory = QuestionViewModelFactory(
            Repository.getRepositoryInstance(
                QuizClient.getQuizClient(), ConcreteLocaleSource.getInstance(this), this.application
            ), this.application
        )

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[QuestionViewModel::class.java]
    }

    private fun observeViewModel() {
        viewModel.question.observe(this) {
            if (it.response_code == 0 && it.results.isNotEmpty()) {
                questions = it.results
                binding.questionProgressBar.max = questions.size
                binding.constrainLayout.visibility = View.VISIBLE
                binding.noDataLayout.noDataAnimation.visibility = View.GONE
                binding.noDataLayout.noDataTextView.visibility = View.GONE
                showQuestions()
            } else {
                binding.constrainLayout.visibility = View.GONE
                binding.noDataLayout.noDataAnimation.visibility = View.VISIBLE
                binding.noDataLayout.noDataTextView.visibility = View.VISIBLE
            }
            binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.GONE
            binding.shimmerAnimationLayout.shimmerFrameLayout.stopShimmer()
        }
    }

    private fun showQuestions() {

        binding.questionCategory.text = questions[questionNumber].category
        binding.questionProgressBar.progress = questionNumber + 1
        binding.questionNumber.text = "${questionNumber + 1} / ${questions.size}"
        binding.question.text =
            Html.fromHtml(questions[questionNumber].question, Html.FROM_HTML_MODE_COMPACT)
        if (questions[questionNumber].incorrect_answers.size == 1) {
            binding.firstAnswer.text = Html.fromHtml("True", Html.FROM_HTML_MODE_COMPACT)
            binding.secondAnswer.text = Html.fromHtml("False", Html.FROM_HTML_MODE_COMPACT)
            binding.thirdAnswer.visibility = View.INVISIBLE
            binding.fourthAnswer.visibility = View.INVISIBLE
        } else {
            val randomNumbers = (0..3).shuffled()
            val questionAnswers =
                questions[questionNumber].incorrect_answers.plus(questions[questionNumber].correct_answer)
            binding.firstAnswer.text =
                Html.fromHtml(questionAnswers[randomNumbers[0]], Html.FROM_HTML_MODE_COMPACT)
            binding.secondAnswer.text =
                Html.fromHtml(questionAnswers[randomNumbers[1]], Html.FROM_HTML_MODE_COMPACT)
            binding.thirdAnswer.text =
                Html.fromHtml(questionAnswers[randomNumbers[2]], Html.FROM_HTML_MODE_COMPACT)
            binding.fourthAnswer.text =
                Html.fromHtml(questionAnswers[randomNumbers[3]], Html.FROM_HTML_MODE_COMPACT)
            binding.thirdAnswer.visibility = View.VISIBLE
            binding.fourthAnswer.visibility = View.VISIBLE
            multipleQuestion++
        }

        binding.firstAnswer.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_question_button_background, null)
        binding.secondAnswer.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_question_button_background, null)
        binding.thirdAnswer.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_question_button_background, null)
        binding.fourthAnswer.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_question_button_background, null)

        binding.firstAnswer.isEnabled = true
        binding.secondAnswer.isEnabled = true
        binding.thirdAnswer.isEnabled = true
        binding.fourthAnswer.isEnabled = true
     //   binding.nextButton.isEnabled = false
        binding.nextButton.visibility = View.INVISIBLE
        isCorrectFromFirstOne = true
    }

    private fun calculateResult() {
        val totalQuestions = questions.size
        viewModel.updateResult(totalQuestions, multipleQuestion, correctAnswer)
        viewModel.insertQuestionsToRoom(questions)
    }

    private fun showDialog() {
        val view: View = layoutInflater.inflate(R.layout.show_score_layout, null)
        val scoreTextView = view.findViewById<TextView>(R.id.score_textView)
        val imageView = view.findViewById<ImageView>(R.id.score_imageView)
        val finishButton = view.findViewById<Button>(R.id.finish_button)
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).setCancelable(false)
        builder.setView(view)
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
        isDialogShowing = true
        val window = alertDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)

        scoreTextView.text = "$correctAnswer / ${questions.size}"
        if (correctAnswer > questions.size / 2) {
            imageView.setImageResource(R.drawable.ic_happy_face)
        }
        finishButton.setOnClickListener {
            alertDialog.dismiss()
            finish()
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        var alertDialog: AlertDialog? = null
        if (!isDialogShowing) {
            builder.setTitle(R.string.end_playing)
                .setMessage(R.string.warning_message).setCancelable(false)
                .setPositiveButton(R.string.yes) { _, _ -> finish() }
                .setNegativeButton(R.string.no) { _, _ -> alertDialog!!.dismiss() }
            alertDialog = builder.create()
            alertDialog.show()
        }
    }
}