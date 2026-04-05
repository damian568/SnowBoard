package com.example.snowboard.Fragments.Detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.snowboard.Constants.Constants
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentTipDetailBinding
import com.google.android.material.transition.MaterialContainerTransform

class TipDetailFragment : Fragment() {
    private lateinit var binding: FragmentTipDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup the exact animation from the video
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId =
                R.id.navHostFragment // The NavHost container from your activity_main.xml
            duration = Constants.DetailDoration
            scrimColor =
                Color.TRANSPARENT // Keeps the background from turning dark during the animation
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
        view.findViewById<View>(R.id.tip_detail_root).transitionName = transitionName

        // 2. Set the rest of your data...
        val title = arguments?.getString("TIP_TITLE")
        val desc = arguments?.getString("TIP_DESC")
        val imageRes = arguments?.getInt("TIP_IMAGE") ?: Constants.Detail_IMG

        binding.tipDetailTitle.text = title
        binding.tipDetailDescription.text = desc
        if (imageRes != Constants.Detail_IMG) {
            binding.tipDetailImg.setImageResource(imageRes)
        }

        binding.tipBtnClose.setOnClickListener {
            // This tells the NavController to go back to the list.
            // It will automatically play the reverse "shrinking" animation!
            findNavController().navigateUp()
        }
        textColors()
    }

    private fun textColors() {
        binding.apply {
            tipDetailTitle.setTextColor(resources.getColor(R.color.midnight))
            tipDetailDescription.setTextColor(resources.getColor(R.color.shadow_black))
        }
    }

    private fun hideTheToolbar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}