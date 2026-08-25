package com.example.snowboard.User.Login

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentLoginScreenBinding

class LoginScreenFragment : Fragment() {

    private lateinit var binding: FragmentLoginScreenBinding
    private var passwordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
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

        binding.signUp.setOnClickListener { goToRegisterScreen() }
    }

    private fun goToRegisterScreen(){
        val action = LoginScreenFragmentDirections.actionLoginScreenFragmentToRegisterScreenFragment()
        findNavController().navigate(action)
    }
}
