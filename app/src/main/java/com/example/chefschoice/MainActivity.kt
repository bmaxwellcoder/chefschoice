package com.example.chefschoice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.SignInButton
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import java.security.SecureRandom
import android.util.Base64

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var signUpBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var googleSignInBtn: SignInButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        emailEt = findViewById(R.id.email_edt_text)
        passwordEt = findViewById(R.id.pass_edt_text)
        loginBtn = findViewById(R.id.login_btn)
        signUpBtn = findViewById(R.id.signup_btn)
        googleSignInBtn = findViewById(R.id.google_sign_in)
    }

    private fun setupClickListeners() {
        signUpBtn.setOnClickListener {
            handleSignUp()
        }

        loginBtn.setOnClickListener {
            handleLogin()
        }

        googleSignInBtn.setOnClickListener{
            googleSignIn()
//            val intent = Intent(this, GoogleSignInActivity::class.java)
//            startActivity(intent)
//            finish()
        }

        val skipSignUp = findViewById<TextView>(R.id.tV2)
        skipSignUp.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun googleSignIn() {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(getString(R.string.default_web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager.create(this)
        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = this@MainActivity,
                    request = request
                )
                handleSignInWithGoogle(result)
            } catch (e: GetCredentialException) {
                Log.e("MainActivity", "Sign-in failed", e)
                Toast.makeText(this@MainActivity, "Sign-in failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun generateNonce(): String {
        val random = SecureRandom()
        val nonceBytes = ByteArray(32)
        random.nextBytes(nonceBytes)
        return Base64.encodeToString(nonceBytes, Base64.NO_WRAP)
    }

    private fun handleSignInWithGoogle(result: GetCredentialResponse) {
        val credential = result.credential

        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken

                        // Use the ID token to sign in with Firebase
                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    val isNewUser = task.result?.additionalUserInfo?.isNewUser ?: false
                                    if (isNewUser) {
                                        Toast.makeText(this, "New account created with Google", Toast.LENGTH_LONG).show()
                                    } else {
                                        Toast.makeText(this, "Welcome back!", Toast.LENGTH_LONG).show()
                                    }
                                    navigateToMainActivity2()
                                } else {
                                    Toast.makeText(this, "Google Sign-In Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Received an invalid google id token response", e)
                        Toast.makeText(this, "Google Sign-In Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Log.e("MainActivity", "Unexpected type of credential")
                    Toast.makeText(this, "Unexpected credential type", Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                Log.e("MainActivity", "Unexpected type of credential")
                Toast.makeText(this, "Unexpected credential type", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun handleSignUp() {
        val email = emailEt.text.toString()
        val password = passwordEt.text.toString()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                    navigateToMainActivity2()
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun handleLogin() {
        val email = emailEt.text.toString()
        val password = passwordEt.text.toString()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                    navigateToMainActivity2()
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun navigateToMainActivity2() {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
        finish()
    }
}