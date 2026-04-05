package com.example.snowboard.Fragments.Detail

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.snowboard.Constants.Constants
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentEquipmentDetailBinding
import com.google.android.material.transition.MaterialContainerTransform

class EquipmentDetailFragment : Fragment() {
    private lateinit var binding: FragmentEquipmentDetailBinding

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
        val imageRes = arguments?.getInt("EQUIPMENT_IMAGE") ?: Constants.Detail_IMG
        val title = arguments?.getString("EQUIPMENT_TITLE") ?: ""

        binding.equipmentDetailTitle.text = title
        if (imageRes != Constants.Detail_IMG) {
            binding.equipmentDetailImg.setImageResource(imageRes)
        }
        changeDescDetail(title)
        brandsHTML(title)

        binding.equipmentBtnClose.setOnClickListener {
            // This tells the NavController to go back to the list.
            // It will automatically play the reverse "shrinking" animation!
            findNavController().navigateUp()
        }
        textColors()
    }

    private fun changeDescDetail(title: String): String {
        val changeDesc = when (title) {
            //ENG
            "Snowboard" -> getString(R.string.equipment_desc_detail_1)
            "Bindings" -> getString(R.string.equipment_desc_detail_2)
            "Boots" -> getString(R.string.equipment_desc_detail_3)
            "Helmet" -> getString(R.string.equipment_desc_detail_4)
            "Goggles" -> getString(R.string.equipment_desc_detail_5)
            "Jacket" -> getString(R.string.equipment_desc_detail_6)
            "Pants" -> getString(R.string.equipment_desc_detail_7)
            "Gloves" -> getString(R.string.equipment_desc_detail_8)
            "Base Layers" -> getString(R.string.equipment_desc_detail_9)
            "Socks" -> getString(R.string.equipment_desc_detail_10)

            //BG
            "Сноуборд" -> getString(R.string.equipment_desc_detail_1)
            "Автомати" -> getString(R.string.equipment_desc_detail_2)
            "Обувки" -> getString(R.string.equipment_desc_detail_3)
            "Каска" -> getString(R.string.equipment_desc_detail_4)
            "Очила" -> getString(R.string.equipment_desc_detail_5)
            "Яке" -> getString(R.string.equipment_desc_detail_6)
            "Панталон" -> getString(R.string.equipment_desc_detail_7)
            "Ръкавици" -> getString(R.string.equipment_desc_detail_8)
            "Термо Бельо" -> getString(R.string.equipment_desc_detail_9)
            "Чорапи" -> getString(R.string.equipment_desc_detail_10)
            else -> getString(R.string.equipment_instruction_google)
        }
        return changeDesc.also { binding.equipmentDetailDescription.text = it }
    }

    // This logic maps the clicked item to the correct list of 10+ brands
    private fun brandsHTML(title: String) {
        val brandsHtml = when (title) {
            //ENG
            "Snowboard" -> getString(R.string.brands_snowboards)
            "Bindings" -> getString(R.string.brands_bindings)
            "Boots" -> getString(R.string.brands_boots)
            "Helmet" -> getString(R.string.brands_helmets)
            "Goggles" -> getString(R.string.brands_goggles)
            "Jacket" -> getString(R.string.brands_jackets)
            "Pants" -> getString(R.string.brands_pants)
            "Gloves" -> getString(R.string.brands_gloves)
            "Base Layers" -> getString(R.string.brands_base_layers)
            "Socks" -> getString(R.string.brands_socks)

            //BG
            "Сноуборд" -> getString(R.string.brands_snowboards)
            "Автомати" -> getString(R.string.brands_bindings)
            "Обувки" -> getString(R.string.brands_boots)
            "Каска" -> getString(R.string.brands_helmets)
            "Очила" -> getString(R.string.brands_goggles)
            "Яке" -> getString(R.string.brands_jackets)
            "Панталон" -> getString(R.string.brands_pants)
            "Ръкавици" -> getString(R.string.brands_gloves)
            "Термо Бельо" -> getString(R.string.brands_base_layers)
            "Чорапи" -> getString(R.string.brands_socks)
            else -> getString(R.string.equipment_instruction_google)
        }

        // Apply the text and make links clickable
        binding.equipmentDetailBrands.text = Html.fromHtml(brandsHtml)
        binding.equipmentDetailBrands.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun textColors() {
        binding.apply {
            equipmentDetailTitle.setTextColor(resources.getColor(R.color.midnight))
            equipmentDetailDescription.setTextColor(resources.getColor(R.color.shadow_black))
            equipmentDetailBrands.setTextColor(resources.getColor(R.color.shadow_black))
        }
    }

    private fun hideTheToolbar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}