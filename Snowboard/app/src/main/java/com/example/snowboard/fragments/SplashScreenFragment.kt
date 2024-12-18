package com.example.snowboard.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.snowboard.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private var delayMills = 4000

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
        Handler(Looper.getMainLooper()).postDelayed({
            goToMainScreen()
        }, delayMills.toLong())
    }

    private fun goToMainScreen() {
        val action =
            SplashScreenFragmentDirections.actionSplashScreenFragmentToMainScreenFragment()
        findNavController().navigate(action)
    }
}