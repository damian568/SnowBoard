package com.example.snowboard.User

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.example.snowboard.R
import com.example.snowboard.User.Model.UserProfile
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Shared Google / Facebook sign-in flow for the Login and Register screens.
 * Both screens end up doing the same thing: authenticate with Firebase, then
 * make sure a Firestore user profile exists.
 */
class SocialAuthHelper(
    private val fragment: Fragment,
    private val googleSignInLauncher: ActivityResultLauncher<Intent>,
    private val onLoading: (Boolean) -> Unit,
    private val onError: (String?) -> Unit,
    private val onSuccess: () -> Unit
) {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val facebookCallbackManager = CallbackManager.Factory.create()

    init {
        LoginManager.getInstance().registerCallback(
            facebookCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                    signInWithCredential(credential)
                }

                override fun onCancel() {
                    onLoading(false)
                }

                override fun onError(error: FacebookException) {
                    onLoading(false)
                    onError(error.message)
                }
            }
        )
    }

    /** Forward from the hosting Fragment's onActivityResult (Facebook SDK). */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun signInWithFacebook() {
        onLoading(true)
        LoginManager.getInstance()
            .logInWithReadPermissions(fragment, listOf("email", "public_profile"))
    }

    fun signInWithGoogle() {
        onLoading(true)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(fragment.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(fragment.requireContext(), gso)
        googleSignInLauncher.launch(client.signInIntent)
    }

    /** Call from the launcher registered with ActivityResultContracts.StartActivityForResult(). */
    fun handleGoogleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken == null) {
                onLoading(false)
                onError("Google sign-in failed: missing ID token")
                return
            }
            signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
        } catch (e: ApiException) {
            onLoading(false)
            if (e.statusCode != com.google.android.gms.common.api.CommonStatusCodes.CANCELED) {
                onError("Google sign-in failed (${e.statusCode})")
            }
        }
    }

    private fun signInWithCredential(credential: AuthCredential) {
        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                val user = result.user
                if (user == null) {
                    onLoading(false)
                    onError(null)
                    return@addOnSuccessListener
                }
                val userDoc = firestore.collection("users").document(user.uid)
                userDoc.get()
                    .addOnSuccessListener { snapshot ->
                        if (snapshot.exists()) {
                            onLoading(false)
                            onSuccess()
                        } else {
                            val profile = UserProfile(
                                fullName = user.displayName ?: "",
                                email = user.email ?: "",
                                level = "BEGINNER"
                            )
                            userDoc.set(profile)
                                .addOnSuccessListener {
                                    onLoading(false)
                                    onSuccess()
                                }
                                .addOnFailureListener { e ->
                                    onLoading(false)
                                    onError(e.message)
                                }
                        }
                    }
                    .addOnFailureListener { e ->
                        onLoading(false)
                        onError(e.message)
                    }
            }
            .addOnFailureListener { e ->
                onLoading(false)
                onError(e.message)
            }
    }
}
