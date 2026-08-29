package com.example.snowboard.User.Settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        selectUnits(AppPreferences.isMetricUnits(requireContext()))
        binding.unitsMetric.setOnClickListener { selectUnits(true) }
        binding.unitsImperial.setOnClickListener { selectUnits(false) }

        binding.switchNotifications.isChecked = AppPreferences.isNotificationsEnabled(requireContext())
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            AppPreferences.setNotificationsEnabled(requireContext(), isChecked)
        }

        binding.switchDarkMode.isChecked = AppPreferences.isDarkMode(requireContext())
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            AppPreferences.setDarkMode(requireContext(), isChecked)
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        binding.rowChangePassword.setOnClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.actionSettingsFragmentToChangePasswordScreenFragment()
            )
        }

        binding.rowChangeEmail.setOnClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.actionSettingsFragmentToChangeEmailScreenFragment()
            )
        }
    }

    private fun selectUnits(metric: Boolean) {
        AppPreferences.setMetricUnits(requireContext(), metric)

        binding.unitsMetric.setBackgroundResource(
            if (metric) R.drawable.bg_level_selected_left else android.R.color.transparent
        )
        binding.unitsImperial.setBackgroundResource(
            if (metric) android.R.color.transparent else R.drawable.bg_level_selected_right
        )

        val activeColor = ContextCompat.getColor(requireContext(), R.color.white)
        val inactiveColor = ContextCompat.getColor(requireContext(), R.color.black)

        binding.textUnitsMetric.setTextColor(if (metric) activeColor else inactiveColor)
        binding.textUnitsImperial.setTextColor(if (metric) inactiveColor else activeColor)
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
}
