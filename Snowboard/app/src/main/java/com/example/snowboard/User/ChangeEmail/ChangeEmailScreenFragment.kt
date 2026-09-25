package com.example.snowboard.User.ChangeEmail

import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentChangeEmailScreenBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class ChangeEmailScreenFragment : Fragment() {

    private lateinit var binding: FragmentChangeEmailScreenBinding
    private val auth = FirebaseAuth.getInstance()
    private var currentPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeEmailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.toggleCurrentPassword.setOnClickListener {
            currentPasswordVisible = !currentPasswordVisible
            val cursorPosition = binding.editCurrentPassword.selectionStart
            binding.editCurrentPassword.inputType = if (currentPasswordVisible) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            binding.toggleCurrentPassword.setImageResource(
                if (currentPasswordVisible) R.drawable.ic_eye else R.drawable.ic_eye_off
            )
            binding.editCurrentPassword.setSelection(cursorPosition)
        }

        binding.btnUpdateEmail.setOnClickListener { attemptUpdateEmail() }
    }

    private fun attemptUpdateEmail() {
        val user = auth.currentUser ?: return
        val currentEmail = user.email ?: return
        val currentPassword = binding.editCurrentPassword.text.toString()
        val newEmail = binding.editNewEmail.text.toString().trim()

        if (currentPassword.isEmpty()) {
            binding.editCurrentPassword.error = getString(R.string.error_current_password_required)
            return
        }
        if (newEmail.isEmpty()) {
            binding.editNewEmail.error = getString(R.string.error_email_required)
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            binding.editNewEmail.error = getString(R.string.error_email_invalid)
            return
        }
        if (newEmail.equals(currentEmail, ignoreCase = true)) {
            binding.editNewEmail.error = getString(R.string.error_new_email_same_as_old)
            return
        }

        setLoading(true)
        val credential = EmailAuthProvider.getCredential(currentEmail, currentPassword)
        user.reauthenticate(credential)
            .addOnSuccessListener {
                user.verifyBeforeUpdateEmail(newEmail)
                    .addOnSuccessListener {
                        setLoading(false)
                        if (isAdded) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.msg_verification_email_sent_new_address, newEmail),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().popBackStack()
                        }
                    }
                    .addOnFailureListener { e ->
                        setLoading(false)
                        if (e is FirebaseAuthUserCollisionException) {
                            binding.editNewEmail.error = getString(R.string.error_email_already_in_use)
                        } else {
                            showError(e.message)
                        }
                    }
            }
            .addOnFailureListener { e ->
                setLoading(false)
                if (e is FirebaseAuthInvalidCredentialsException) {
                    binding.editCurrentPassword.error = getString(R.string.error_current_password_incorrect)
                } else {
                    showError(e.message)
                }
            }
    }

    private fun setLoading(loading: Boolean) {
        binding.btnUpdateEmail.isEnabled = !loading
        binding.btnUpdateEmail.text = if (loading) "" else getString(R.string.btn_update_email)
    }

    private fun showError(message: String?) {
        if (isAdded) {
            Toast.makeText(requireContext(), message ?: return, Toast.LENGTH_LONG).show()
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
