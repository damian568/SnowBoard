package com.example.snowboard.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentEquipmentDetailBinding
import com.google.android.material.transition.MaterialContainerTransform

class EquipmentDetailFragment : Fragment() {
    private lateinit var binding: FragmentEquipmentDetailBinding

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
        binding = FragmentEquipmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideTheToolbar()
        // 1. Get the transition name passed from the list and apply it to the root view
        val transitionName = arguments?.getString("TRANSITION_NAME")
        view.findViewById<View>(R.id.equipment_detail_root).transitionName = transitionName

        // 2. Set the rest of your data...
        val imageRes = arguments?.getInt("EQUIPMENT_IMAGE") ?: 0
        val title = arguments?.getString("EQUIPMENT_TITLE") ?: ""
        val desc = arguments?.getString("EQUIPMENT_DESC")

        binding.equipmentDetailTitle.text = title
        binding.equipmentDetailDescription.text = desc
        if (imageRes != 0) {
            binding.equipmentDetailImg.setImageResource(imageRes)
        }

        // This logic maps the clicked item to the correct list of 10+ brands
        val brandsHtml = when (title) {
            "Snowboard" -> getString(R.string.brands_snowboards)
            "Bindings" -> getString(R.string.brands_bindings)
            "Boots" -> getString(R.string.brands_boots)
            "Helmet" -> getString(R.string.brands_helmets)
            "Goggles" -> getString(R.string.brands_goggles)
            "Snowboard jacket" -> getString(R.string.brands_jackets)
            "Snowboard pants" -> getString(R.string.brands_pants)
            "Gloves" -> getString(R.string.brands_gloves)
            "Base layers" -> getString(R.string.brands_base_layers)
            "Snowboard socks" -> getString(R.string.brands_socks)
            else -> "Search for brands at &lt;a href='https://www.google.com'&gt;Google&lt;/a&gt;"
        }

        // Apply the text and make links clickable
        binding.equipmentDetailBrands.text = Html.fromHtml(brandsHtml)
        binding.equipmentDetailBrands.movementMethod = android.text.method.LinkMovementMethod.getInstance()

        binding.equipmentBtnClose.setOnClickListener {
            // This tells the NavController to go back to the list.
            // It will automatically play the reverse "shrinking" animation!
            findNavController().navigateUp()
        }
    }

    private fun hideTheToolbar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}