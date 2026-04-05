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
        textColors()
    }

    private fun setUpHistoryTitles() {
        binding.apply {
            historyTitle.text = getString(R.string.history_header)
            titleHistory.text = getString(R.string.history_title_history)
            titleStyles.text = getString(R.string.history_title_styles)
            titleStyles1.text = getString(R.string.history_title_styles_1)
            titleStyles2.text = getString(R.string.history_title_styles_2)
            titleStyles3.text = getString(R.string.history_title_styles_3)
            titleTricks.text = getString(R.string.history_title_tricks)
        }
    }

    private fun setUpHistoryParagraphs() {
        binding.apply {
            paragraphHistory1.text = getString(R.string.paragraph_history_1)
            paragraphHistory2.text = getString(R.string.paragraph_history_2)
            paragraphHistory3.text = getString(R.string.paragraph_history_3)
            paragraphHistory4.text = getString(R.string.paragraph_history_4)
            paragraphHistory5.text = getString(R.string.paragraph_history_5)
            paragraphHistory6.text = getString(R.string.paragraph_history_6)
            paragraphHistory7.text = getString(R.string.paragraph_history_7)
        }
    }

    private fun textColors() {
        binding.apply {
            historyTitle.setTextColor(resources.getColor(R.color.black))
            titleHistory.setTextColor(resources.getColor(R.color.black))
            titleStyles.setTextColor(resources.getColor(R.color.black))
            titleStyles1.setTextColor(resources.getColor(R.color.black))
            titleStyles2.setTextColor(resources.getColor(R.color.black))
            titleStyles3.setTextColor(resources.getColor(R.color.black))
            titleTricks.setTextColor(resources.getColor(R.color.black))

            paragraphHistory1.setTextColor(resources.getColor(R.color.shadow_black))
            paragraphHistory2.setTextColor(resources.getColor(R.color.shadow_black))
            paragraphHistory3.setTextColor(resources.getColor(R.color.shadow_black))
            paragraphHistory4.setTextColor(resources.getColor(R.color.shadow_black))
            paragraphHistory5.setTextColor(resources.getColor(R.color.shadow_black))
            paragraphHistory6.setTextColor(resources.getColor(R.color.shadow_black))
            paragraphHistory7.setTextColor(resources.getColor(R.color.shadow_black))
        }
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