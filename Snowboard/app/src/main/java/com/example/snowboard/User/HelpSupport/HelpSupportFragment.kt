package com.example.snowboard.User.HelpSupport

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentHelpSupportBinding

class HelpSupportFragment : Fragment() {

    private lateinit var binding: FragmentHelpSupportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelpSupportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.rowContactSupport.setOnClickListener { contactSupport() }

        val versionName = try {
            requireContext().packageManager
                .getPackageInfo(requireContext().packageName, 0).versionName
        } catch (e: Exception) {
            null
        }
        binding.textAppVersion.text = getString(R.string.help_app_version_value, versionName ?: "-")
    }

    private fun contactSupport() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_email)))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.help_email_subject))
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            if (isAdded) {
                Toast.makeText(requireContext(), getString(R.string.error_no_email_app), Toast.LENGTH_SHORT).show()
            }
        }
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
