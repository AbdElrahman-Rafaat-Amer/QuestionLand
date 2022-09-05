package com.abdelrahman.rafaat.quizland.history.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdelrahman.rafaat.quizland.R
import com.abdelrahman.rafaat.quizland.database.ConcreteLocaleSource
import com.abdelrahman.rafaat.quizland.databinding.FragmentHistoryBinding
import com.abdelrahman.rafaat.quizland.history.viewmodel.HistoryViewModel
import com.abdelrahman.rafaat.quizland.history.viewmodel.HistoryViewModelFactory
import com.abdelrahman.rafaat.quizland.model.Repository
import com.abdelrahman.rafaat.quizland.network.QuizClient

private const val TAG = "HistoryFragment"

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val adapter = HistoryRecyclerAdapter()
    private lateinit var viewModelFactory: HistoryViewModelFactory
    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(TAG, "onCreateView: ")
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ")
        binding.noDataLayout.noDataTextView.text = getString(R.string.no_history_found)
        initRecyclerView()
        initViewModel()
        observeViewModel()
    }

    private fun initRecyclerView() {
        Log.i(TAG, "initRecyclerView: ")
        binding.historyRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.historyRecyclerview.adapter = adapter
        val resId: Int = R.anim.lat
        val animation: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(context, resId)
        binding.historyRecyclerview.layoutAnimation = animation
    }

    private fun initViewModel() {
        Log.i(TAG, "initViewModel: ")
        viewModelFactory = HistoryViewModelFactory(
            Repository.getRepositoryInstance(
                QuizClient.getQuizClient(),
                ConcreteLocaleSource.getInstance(requireContext()),
                requireActivity().application
            ), requireActivity().application
        )

        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[HistoryViewModel::class.java]

        viewModel.getQuestions()
    }

    private fun observeViewModel() {
        Log.i(TAG, "observeViewModel: ")
        viewModel.questions.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Log.i(TAG, "observeViewModel: in if")
                adapter.setList(it)
                binding.noDataLayout.noDataAnimation.visibility = View.GONE
                binding.noDataLayout.noDataTextView.visibility = View.GONE
                binding.historyRecyclerview.visibility = View.VISIBLE
            } else {
                Log.i(TAG, "observeViewModel: in else")
                adapter.setList(emptyList())
                binding.historyRecyclerview.visibility = View.GONE
                binding.noDataLayout.noDataAnimation.visibility = View.VISIBLE
                binding.noDataLayout.noDataTextView.visibility = View.VISIBLE
            }


            Log.i(TAG, "observeViewModel: size--------------> ${it.size}")
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getQuestions()
    }
}