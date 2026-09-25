package com.example.snowboard.User.ForgotPassword

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentForgotPasswordScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class ForgotPasswordScreenFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordScreenBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSendResetLink.setOnClickListener { attemptSendResetLink() }
        binding.backToLogIn.setOnClickListener { findNavController().popBackStack() }
    }

    private fun attemptSendResetLink() {
        val email = binding.editEmail.text.toString().trim()

        if (email.isEmpty()) {
            binding.editEmail.error = getString(R.string.error_email_required)
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editEmail.error = getString(R.string.error_email_invalid)
            return
        }

        setLoading(true)
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                setLoading(false)
                if (isAdded) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.msg_reset_email_sent, email),
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().popBackStack()
                }
            }
            .addOnFailureListener { e ->
                setLoading(false)
                if (isAdded) {
                    val message = if (e is FirebaseAuthInvalidUserException) {
                        getString(R.string.error_no_account_for_email)
                    } else {
                        e.message
                    }
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun setLoading(loading: Boolean) {
        binding.btnSendResetLink.isEnabled = !loading
        binding.btnSendResetLink.text = if (loading) "" else getString(R.string.btn_send_reset_link)
    }
}
