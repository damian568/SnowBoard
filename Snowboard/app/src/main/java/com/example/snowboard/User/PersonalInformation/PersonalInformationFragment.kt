package com.example.snowboard.User.PersonalInformation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentPersonalInformationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PersonalInformationFragment : Fragment() {

    private enum class SkillLevel { BEGINNER, ADVANCE }

    private lateinit var binding: FragmentPersonalInformationBinding
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private var selectedLevel = SkillLevel.BEGINNER

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.levelBeginner.setOnClickListener { selectLevel(SkillLevel.BEGINNER) }
        binding.levelAdvance.setOnClickListener { selectLevel(SkillLevel.ADVANCE) }
        binding.btnSave.setOnClickListener { saveChanges() }

        loadUserProfile()
    }

    private fun loadUserProfile() {
        val user = auth.currentUser ?: return
        binding.textEmail.text = user.email

        firestore.collection("users").document(user.uid).get()
            .addOnSuccessListener { document ->
                if (!isAdded) return@addOnSuccessListener
                document.getString("fullName")?.let { binding.editFullName.setText(it) }
                val level = if (document.getString("level") == "ADVANCE") {
                    SkillLevel.ADVANCE
                } else {
                    SkillLevel.BEGINNER
                }
                selectLevel(level)
            }
    }

    private fun selectLevel(level: SkillLevel) {
        selectedLevel = level
        val beginnerSelected = level == SkillLevel.BEGINNER

        binding.levelBeginner.setBackgroundResource(
            if (beginnerSelected) R.drawable.bg_level_selected_left else android.R.color.transparent
        )
        binding.levelAdvance.setBackgroundResource(
            if (beginnerSelected) android.R.color.transparent else R.drawable.bg_level_selected_right
        )

        val activeColor = ContextCompat.getColor(requireContext(), R.color.white)
        val inactiveColor = ContextCompat.getColor(requireContext(), R.color.black)

        binding.iconBeginner.imageTintList =
            ColorStateList.valueOf(if (beginnerSelected) activeColor else inactiveColor)
        binding.textBeginner.setTextColor(if (beginnerSelected) activeColor else inactiveColor)

        binding.iconAdvance.imageTintList =
            ColorStateList.valueOf(if (beginnerSelected) inactiveColor else activeColor)
        binding.textAdvance.setTextColor(if (beginnerSelected) inactiveColor else activeColor)
    }

    private fun saveChanges() {
        val user = auth.currentUser ?: return
        val fullName = binding.editFullName.text.toString().trim()

        if (fullName.isEmpty()) {
            binding.editFullName.error = getString(R.string.error_full_name_required)
            return
        }

        setLoading(true)
        firestore.collection("users").document(user.uid)
            .update(mapOf("fullName" to fullName, "level" to selectedLevel.name))
            .addOnSuccessListener {
                setLoading(false)
                if (isAdded) {
                    Toast.makeText(requireContext(), getString(R.string.msg_profile_updated), Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
            .addOnFailureListener { e ->
                setLoading(false)
                if (isAdded) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun setLoading(loading: Boolean) {
        binding.btnSave.isEnabled = !loading
        binding.btnSave.text = if (loading) "" else getString(R.string.btn_save_changes)
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
