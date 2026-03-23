package com.example.snowboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snowboard.Adapters.SkiSlopesAdapter
import com.example.snowboard.Lists.SkiSlopesList
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentSkiSlopesScreenBinding

class SkiSlopesScreenFragment : Fragment() {
    private lateinit var binding: FragmentSkiSlopesScreenBinding
    private lateinit var skiSlopesArrayList: ArrayList<SkiSlopesList>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSkiSlopesScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)

        val skiSlopesAdapter = SkiSlopesAdapter(skiSlopesArrayList)
        binding.recyclerView.adapter = skiSlopesAdapter
    }

    override fun onResume() {
        super.onResume()
        // To HIDE the toolbar
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        // To SHOW the toolbar when leaving this fragment
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

    private fun dataInitialize() {
        skiSlopesArrayList = arrayListOf()

        skiSlopesArrayList.add(
            SkiSlopesList(
                R.drawable.ic_snowboard_logo,
                getString(R.string.title_ski_slopes_1),
                "Here is the detailed tip about bending your knees while snowboarding...",
                R.drawable.bansko,
                "https://www.banskoski.com/bg"
            )
        )
        skiSlopesArrayList.add(
            SkiSlopesList(
                R.drawable.ic_snowboard_logo,
                getString(R.string.title_ski_slopes_2),
                "Here is the detailed tip about bending your knees while snowboarding...",
                R.drawable.bansko,
                "https://www.banskoski.com/bg"
            )
        )
        skiSlopesArrayList.add(
            SkiSlopesList(
                R.drawable.ic_snowboard_logo,
                getString(R.string.title_ski_slopes_3),
                "Here is the detailed tip about bending your knees while snowboarding...",
                R.drawable.bansko,
                "https://www.banskoski.com/bg"
            )
        )
        skiSlopesArrayList.add(
            SkiSlopesList(
                R.drawable.ic_snowboard_logo,
                getString(R.string.title_ski_slopes_4),
                "Here is the detailed tip about bending your knees while snowboarding...",
                R.drawable.bansko,
                "https://www.banskoski.com/bg"
            )
        )
        skiSlopesArrayList.add(
            SkiSlopesList(
                R.drawable.ic_snowboard_logo,
                getString(R.string.title_ski_slopes_5),
                "Here is the detailed tip about bending your knees while snowboarding...",
                R.drawable.bansko,
                "https://www.banskoski.com/bg"
            )
        )
        skiSlopesArrayList.add(
            SkiSlopesList(
                R.drawable.ic_snowboard_logo,
                getString(R.string.title_ski_slopes_6),
                "Here is the detailed tip about bending your knees while snowboarding...",
                R.drawable.bansko,
                "https://www.banskoski.com/bg"
            )
        )
        skiSlopesArrayList.add(
            SkiSlopesList(
                R.drawable.ic_snowboard_logo,
                getString(R.string.title_ski_slopes_7),
                "Here is the detailed tip about bending your knees while snowboarding...",
                R.drawable.bansko,
                "https://www.banskoski.com/bg"
            )
        )
        skiSlopesArrayList.add(
            SkiSlopesList(
                R.drawable.ic_snowboard_logo,
                getString(R.string.title_ski_slopes_8),
                "Here is the detailed tip about bending your knees while snowboarding...",
                R.drawable.bansko,
                "https://www.banskoski.com/bg"
            )
        )
        skiSlopesArrayList.add(
            SkiSlopesList(
                R.drawable.ic_snowboard_logo,
                getString(R.string.title_ski_slopes_9),
                "Here is the detailed tip about bending your knees while snowboarding...",
                R.drawable.bansko,
                "https://www.banskoski.com/bg"
            )
        )
        skiSlopesArrayList.add(
            SkiSlopesList(
                R.drawable.ic_snowboard_logo,
                getString(R.string.title_ski_slopes_10),
                "Here is the detailed tip about bending your knees while snowboarding...",
                R.drawable.bansko,
                "https://www.banskoski.com/bg"
            )
        )
    }
}