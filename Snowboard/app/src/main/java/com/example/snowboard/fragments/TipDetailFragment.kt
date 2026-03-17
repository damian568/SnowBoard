package com.example.snowboard.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentTipDetailBinding
import com.google.android.material.transition.MaterialContainerTransform

class TipDetailFragment : Fragment() {
    private lateinit var binding: FragmentTipDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup the exact animation from the video
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.navHostFragment // The NavHost container from your activity_main.xml
            duration = 300L
            scrimColor = Color.TRANSPARENT // Keeps the background from turning dark during the animation
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTipDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideTheToolbar()
        // 1. Get the transition name passed from the list and apply it to the root view
        val transitionName = arguments?.getString("TRANSITION_NAME")
        view.findViewById<View>(R.id.detail_root).transitionName = transitionName

        // 2. Set the rest of your data...
        val title = arguments?.getString("TIP_TITLE")
        val desc = arguments?.getString("TIP_DESC")
        val imageRes = arguments?.getInt("TIP_IMAGE") ?: 0

        binding.detailTitle.text = title
        binding.detailDescription.text = desc
        if (imageRes != 0) {
            binding.detailImage.setImageResource(imageRes)
        }

        binding.btnClose.setOnClickListener {
            // This tells the NavController to go back to the list.
            // It will automatically play the reverse "shrinking" animation!
            findNavController().navigateUp()
        }
    }

    private fun hideTheToolbar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}