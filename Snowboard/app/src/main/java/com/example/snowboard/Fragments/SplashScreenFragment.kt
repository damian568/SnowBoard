package com.example.snowboard.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.snowboard.Constants.Constants
import com.example.snowboard.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideTheToolbar()
        showToFullScreen()
        slowFragment()
    }

    private fun hideTheToolbar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun showToFullScreen() {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun slowFragment() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(Constants.DelayMills_Splash)

            if (isAdded) {
                goToMainScreen()
            }
        }
    }

    private fun goToMainScreen() {
        if (isAdded) {
            val action =
                SplashScreenFragmentDirections.actionSplashScreenFragmentToMainScreenFragment()
            findNavController().navigate(action)
        }
    }
}