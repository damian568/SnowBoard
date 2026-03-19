package com.example.snowboard.fragments

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.snowboard.Adapters.EquipmentAdapter
import com.example.snowboard.Lists.EquipmentList
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentEquipmentScreenBinding

class EquipmentScreenFragment : Fragment() {
    private lateinit var binding: FragmentEquipmentScreenBinding
    private lateinit var equipmentArrayList: ArrayList<EquipmentList>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEquipmentScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideToolbar()
        dataInitialize()

        // 1. Setup Grid Layout
        val layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)

        // 2. Initialize Adapter with the click listener logic
        val adapter = EquipmentAdapter(equipmentArrayList) { selectedItem, clickedView ->

            // This matches the logic we used for the Tips screen
            val transitionName = clickedView.transitionName

            val bundle = Bundle().apply {
                putInt("EQUIPMENT_IMAGE", selectedItem.equipmentImage)
                putString("EQUIPMENT_TITLE", selectedItem.equipmentTitle)
                putString("EQUIPMENT_DESC", selectedItem.equipmentDescription)
                putString("TRANSITION_NAME", transitionName)
            }

            val extras = FragmentNavigatorExtras(
                clickedView to transitionName
            )
            findNavController().navigate(R.id.equipmentDetailFragment, bundle, null, extras)
        }

        binding.recyclerView.adapter = adapter

        // 3. Add Spacing
        binding.recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val spacing = 16 // in pixels
                outRect.left = spacing
                outRect.right = spacing
                outRect.bottom = spacing
                outRect.top = spacing
            }
        })
    }

    private fun hideToolbar() {
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    private fun dataInitialize() {
        equipmentArrayList = arrayListOf()

        equipmentArrayList.add(
            EquipmentList(
                R.drawable.snowboard_pic_equipment,
                getString(R.string.equipment_title_1),
                getString(R.string.equipment_instruction)
            )
        )
        equipmentArrayList.add(
            EquipmentList(
                R.drawable.bindings_pic_equipment,
                getString(R.string.equipment_title_2),
                getString(R.string.equipment_instruction)
            )
        )
        equipmentArrayList.add(
            EquipmentList(
                R.drawable.boots_pic_equipment,
                getString(R.string.equipment_title_3),
                getString(R.string.equipment_instruction)
            )
        )
        equipmentArrayList.add(
            EquipmentList(
                R.drawable.helmet_pic_equipment,
                getString(R.string.equipment_title_4),
                getString(R.string.equipment_instruction)
            )
        )
        equipmentArrayList.add(
            EquipmentList(
                R.drawable.goggles_pic_equipment,
                getString(R.string.equipment_title_5),
                getString(R.string.equipment_instruction)
            )
        )
        equipmentArrayList.add(
            EquipmentList(
                R.drawable.jacket_pic_equipment,
                getString(R.string.equipment_title_6),
                getString(R.string.equipment_instruction)
            )
        )
        equipmentArrayList.add(
            EquipmentList(
                R.drawable.pants_pic_equipment,
                getString(R.string.equipment_title_7),
                getString(R.string.equipment_instruction)
            )
        )
        equipmentArrayList.add(
            EquipmentList(
                R.drawable.gloves_pic_equipment,
                getString(R.string.equipment_title_8),
                getString(R.string.equipment_instruction)
            )
        )
        equipmentArrayList.add(
            EquipmentList(
                R.drawable.base_layers_pic_equipment,
                getString(R.string.equipment_title_9),
                getString(R.string.equipment_instruction)
            )
        )
        equipmentArrayList.add(
            EquipmentList(
                R.drawable.socks_pic_equipment,
                getString(R.string.equipment_title_10),
                getString(R.string.equipment_instruction)
            )
        )
    }
}