package com.example.snowboard.User.Register

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentRegisterScreenBinding

class RegisterScreenFragment : Fragment() {

    private enum class SkillLevel { BEGINNER, ADVANCE }

    private lateinit var binding: FragmentRegisterScreenBinding
    private var passwordVisible = false
    private var confirmPasswordVisible = false
    private var selectedLevel = SkillLevel.BEGINNER

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.togglePassword.setOnClickListener {
            passwordVisible = !passwordVisible
            val cursorPosition = binding.editPassword.selectionStart
            binding.editPassword.inputType = if (passwordVisible) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            binding.togglePassword.setImageResource(
                if (passwordVisible) R.drawable.ic_eye else R.drawable.ic_eye_off
            )
            binding.editPassword.setSelection(cursorPosition)
        }

        binding.toggleConfirmPassword.setOnClickListener {
            confirmPasswordVisible = !confirmPasswordVisible
            val cursorPosition = binding.editConfirmPassword.selectionStart
            binding.editConfirmPassword.inputType = if (confirmPasswordVisible) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            binding.toggleConfirmPassword.setImageResource(
                if (confirmPasswordVisible) R.drawable.ic_eye else R.drawable.ic_eye_off
            )
            binding.editConfirmPassword.setSelection(cursorPosition)
        }

        binding.levelBeginner.setOnClickListener { selectLevel(SkillLevel.BEGINNER) }
        binding.levelAdvance.setOnClickListener { selectLevel(SkillLevel.ADVANCE) }

        binding.logIn.setOnClickListener { goToLoginScreen() }
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

    private fun goToLoginScreen() {
        val action = RegisterScreenFragmentDirections.actionRegisterScreenFragmentToLoginScreenFragment()
        findNavController().navigate(action)
    }
}
