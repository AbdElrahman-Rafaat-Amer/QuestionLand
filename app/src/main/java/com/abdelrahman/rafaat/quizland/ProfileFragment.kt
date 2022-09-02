package com.abdelrahman.rafaat.quizland

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdelrahman.rafaat.quizland.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.circleImageView.setOnClickListener {
            //Open gallery and get Image from it
        }

        // Do not forget to write a user name Code
        binding.userNameTextView.setOnClickListener {
            showSnackBar()
        }

        binding.playButton.setOnClickListener {
            //Go to playingFragment
        }




    }

    private fun showSnackBar() {
        TODO("Not yet implemented")
    }

}