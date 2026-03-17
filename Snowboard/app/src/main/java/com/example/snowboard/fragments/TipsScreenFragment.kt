package com.example.snowboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snowboard.Adapters.TipsAdapter
import com.example.snowboard.Lists.TipsList
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentTipsScreenBinding

class TipsScreenFragment : Fragment() {
    private lateinit var binding: FragmentTipsScreenBinding
    private lateinit var tipsArrayList: ArrayList<TipsList>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTipsScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataInitialize()

        // 1. Setup the RecyclerView
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)

        // 2. Attach the adapter
        val tipsAdapter = TipsAdapter(tipsArrayList) { selectedTip, clickedCard ->

            val transitionName = clickedCard.transitionName

            val bundle = Bundle().apply {
                putString("TIP_TITLE", selectedTip.title)
                putString("TIP_DESC", selectedTip.description)
                putInt("TIP_IMAGE", selectedTip.imageResource)
                putString("TRANSITION_NAME", transitionName) // Pass the name to the next screen!
            }

            // Tell Navigation Component to link this specific card to the next screen
            val extras = FragmentNavigatorExtras(
                clickedCard to transitionName
            )

            findNavController().navigate(R.id.tipDetailFragment, bundle, null, extras)
        }

        binding.recyclerView.adapter = tipsAdapter
    }

    private fun dataInitialize() {
        tipsArrayList = arrayListOf()

        // Add your dummy data here to test it out!
        tipsArrayList.add(
            TipsList(
                title = getString(R.string.title_detail_tip_1),
                description = getString(R.string.description_detail_tip_1),
                imageResource = R.drawable.ic_equipment // Replace with your actual drawable
            )
        )

        tipsArrayList.add(
            TipsList(
                title = getString(R.string.title_detail_tip_2),
                description = getString(R.string.description_detail_tip_2),
                imageResource = R.drawable.ic_snowboard_logo
            )
        )

        tipsArrayList.add(
            TipsList(
                title = getString(R.string.title_detail_tip_3),
                description = getString(R.string.description_detail_tip_3),
                imageResource = R.drawable.ic_snowboard_logo
            )
        )

        tipsArrayList.add(
            TipsList(
                title = getString(R.string.title_detail_tip_4),
                description = getString(R.string.description_detail_tip_4),
                imageResource = R.drawable.ic_snowboard_logo
            )
        )

        tipsArrayList.add(
            TipsList(
                title = getString(R.string.title_detail_tip_5),
                description = getString(R.string.description_detail_tip_5),
                imageResource = R.drawable.ic_snowboard_logo
            )
        )

        tipsArrayList.add(
            TipsList(
                title = getString(R.string.title_detail_tip_6),
                description = getString(R.string.description_detail_tip_6),
                imageResource = R.drawable.ic_snowboard_logo
            )
        )

        tipsArrayList.add(
            TipsList(
                title = getString(R.string.title_detail_tip_7),
                description = getString(R.string.description_detail_tip_7),
                imageResource = R.drawable.ic_snowboard_logo
            )
        )

        tipsArrayList.add(
            TipsList(
                title = getString(R.string.title_detail_tip_8),
                description = getString(R.string.description_detail_tip_8),
                imageResource = R.drawable.ic_snowboard_logo
            )
        )

        tipsArrayList.add(
            TipsList(
                title = getString(R.string.title_detail_tip_9),
                description = getString(R.string.description_detail_tip_9),
                imageResource = R.drawable.ic_snowboard_logo
            )
        )

        tipsArrayList.add(
            TipsList(
                title = getString(R.string.title_detail_tip_10),
                description = getString(R.string.description_detail_tip_10),
                imageResource = R.drawable.ic_snowboard_logo
            )
        )
    }
}