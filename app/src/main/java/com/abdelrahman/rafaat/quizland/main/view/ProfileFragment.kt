package com.abdelrahman.rafaat.quizland.main.view


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abdelrahman.rafaat.quizland.R
import com.abdelrahman.rafaat.quizland.database.ConcreteLocaleSource
import com.abdelrahman.rafaat.quizland.databinding.FragmentProfileBinding
import com.abdelrahman.rafaat.quizland.main.viewmodel.MainViewModel
import com.abdelrahman.rafaat.quizland.main.viewmodel.MainViewModelFactory
import com.abdelrahman.rafaat.quizland.model.Repository
import com.abdelrahman.rafaat.quizland.network.QuizClient
import com.abdelrahman.rafaat.quizland.playing.view.PlayingActivity


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("LifeCycle", "onCreateView: ProfileFragment")
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("LifeCycle", "onViewCreated: ProfileFragment")
        initViewModel()
        observeViewModel()

        /* binding.circleImageView.setOnClickListener {
             openGallery()
         }*/

        // Do not forget to write a user name Code
        binding.userNameTextView.setOnClickListener {
            showDialog()
        }

        binding.playButton.setOnClickListener {
            requireActivity().startActivity(Intent(requireContext(), PlayingActivity::class.java))
        }
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

            binding.totalQuestionTextView.text = it.totalQuestions.toString()
            binding.multipleQuestionTextView.text = it.multipleQuestion.toString()
            binding.booleanQuestionTextView.text =
                (it.totalQuestions - it.multipleQuestion).toString()
            binding.correctlyQuestionTextView.text = it.correctAnswer.toString()
            binding.incorrectlyQuestionTextView.text =
                (it.totalQuestions - it.correctAnswer).toString()
            binding.finalScoreTextView.text = it.correctAnswer.toString()
            binding.userNameTextView.text = it.userName
        }
    }

    private fun showDialog() {
        val view: View = layoutInflater.inflate(R.layout.change_name_layout, null)
        val userName = view.findViewById<EditText>(R.id.user_name_editText)
        val saveButton = view.findViewById<Button>(R.id.save_Button)
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
        builder.setView(view)
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
        val window = alertDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.BOTTOM)

        saveButton.setOnClickListener {
            if (userName.text.trim().length < 6) {
                userName.error = getString(R.string.user_name_error)
            } else {
                binding.userNameTextView.text = userName.text.toString()
                alertDialog.dismiss()
                viewModel.updateUserName(userName.text.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("LifeCycle", "onResume: ProfileFragment")
        viewModel.getSharedResult()
    }
}

