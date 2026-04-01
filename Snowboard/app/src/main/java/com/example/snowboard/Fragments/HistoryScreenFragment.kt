package com.example.snowboard.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentHistoryScreenBinding

class HistoryScreenFragment : Fragment() {
    private lateinit var binding: FragmentHistoryScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpHistoryTitles()
        setUpHistoryParagraphs()
    }

    private fun setUpHistoryTitles() {
        binding.historyTitle.text = getString(R.string.history_header)
        binding.titleHistory.text = getString(R.string.history_title_history)
        binding.titleStyles.text = getString(R.string.history_title_styles)
        binding.titleStyles1.text = getString(R.string.history_title_styles_1)
        binding.titleStyles2.text = getString(R.string.history_title_styles_2)
        binding.titleStyles3.text = getString(R.string.history_title_styles_3)
        binding.titleTricks.text = getString(R.string.history_title_tricks)
    }

    private fun setUpHistoryParagraphs() {
        binding.paragraphHistory1.text = getString(R.string.paragraph_history_1)
        binding.paragraphHistory2.text = getString(R.string.paragraph_history_2)
        binding.paragraphHistory3.text = getString(R.string.paragraph_history_3)
        binding.paragraphHistory4.text = getString(R.string.paragraph_history_4)
        binding.paragraphHistory5.text = getString(R.string.paragraph_history_5)
        binding.paragraphHistory6.text = getString(R.string.paragraph_history_6)
        binding.paragraphHistory7.text = getString(R.string.paragraph_history_7)
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
}