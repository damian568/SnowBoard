package com.example.snowboard.User

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentProfileScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileScreenFragment : Fragment() {

    private lateinit var binding: FragmentProfileScreenBinding
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val pickAvatarImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { setAvatarImage(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenu.setOnClickListener { openDrawer() }
        binding.btnCamera.setOnClickListener { pickAvatarImage.launch("image/*") }
        binding.rowLogOut.setOnClickListener { logOut() }

        loadUserProfile()
    }

    private fun setAvatarImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .centerCrop()
            .into(binding.imgAvatar)
    }

    private fun openDrawer() {
        requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
            ?.openDrawer(GravityCompat.START)
    }

    private fun loadUserProfile() {
        val user = auth.currentUser ?: return
        binding.userEmail.text = user.email

        firestore.collection("users").document(user.uid).get()
            .addOnSuccessListener { document ->
                if (!isAdded) return@addOnSuccessListener
                val fullName = document.getString("fullName")
                if (!fullName.isNullOrBlank()) {
                    binding.userName.text = fullName
                }
                val level = document.getString("level")
                binding.statLevelValue.text = if (level == "BEGINNER") {
                    getString(R.string.level_beginner_label)
                } else {
                    getString(R.string.level_advanced_label)
                }
            }
    }

    private fun logOut() {
        auth.signOut()
        val action = ProfileScreenFragmentDirections.actionProfileScreenFragmentToLoginScreenFragment()
        findNavController().navigate(action)
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
