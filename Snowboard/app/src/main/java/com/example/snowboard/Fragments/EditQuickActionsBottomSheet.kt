package com.example.snowboard.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import com.example.snowboard.Lists.QuickAction
import com.example.snowboard.R
import com.example.snowboard.User.Settings.AppPreferences
import com.example.snowboard.databinding.BottomsheetEditQuickActionsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditQuickActionsBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetEditQuickActionsBinding
    private val checkboxByAction = mutableMapOf<QuickAction, CheckBox>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetEditQuickActionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkboxByAction[QuickAction.SKI_SLOPES] = binding.chkSkiSlopes
        checkboxByAction[QuickAction.EQUIPMENT] = binding.chkEquipment
        checkboxByAction[QuickAction.TIPS] = binding.chkTips
        checkboxByAction[QuickAction.VIDEOS] = binding.chkVideos
        checkboxByAction[QuickAction.WEATHER] = binding.chkWeather
        checkboxByAction[QuickAction.HISTORY] = binding.chkHistory

        val selected = AppPreferences.getQuickActions(requireContext()).mapNotNull { QuickAction.fromId(it) }
        checkboxByAction.forEach { (action, checkbox) ->
            checkbox.isChecked = selected.contains(action)
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && selectedCount() > AppPreferences.MAX_QUICK_ACTIONS) {
                    checkbox.isChecked = false
                    Toast.makeText(requireContext(), R.string.quick_actions_max_reached, Toast.LENGTH_SHORT).show()
                } else {
                    saveSelection()
                }
            }
        }

        binding.imgClose.setOnClickListener { dismiss() }
    }

    private fun selectedCount() = checkboxByAction.values.count { it.isChecked }

    private fun saveSelection() {
        val selectedIds = checkboxByAction.filterValues { it.isChecked }.keys.map { it.id }
        AppPreferences.setQuickActions(requireContext(), selectedIds)
        setFragmentResult(REQUEST_KEY, Bundle())
    }

    companion object {
        const val REQUEST_KEY = "edit_quick_actions_request"
    }
}
