package com.example.literupa

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        // 1. KONFIGURASI GOOGLE SIGN IN
        // requestIdToken akan mengambil ID dari strings.xml yang dihasilkan google-services.json
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // 2. KONEKSI KE UI
        val btnGoogle = findViewById<LinearLayout>(R.id.btnGoogleLogin)
        val tvDaftar = findViewById<TextView>(R.id.tvDaftarSekarang)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPass = findViewById<EditText>(R.id.etPassword)

        // 3. LOGIKA LOGIN GOOGLE
        btnGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        // 4. LOGIKA LOGIN EMAIL/PASS BIASA
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val pass = etPass.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) masukBeranda()
                    else Toast.makeText(this, "Gagal: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        tvDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // 5. TANGKAP HASIL LOGIN GOOGLE
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                // Kirim token ke Firebase
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Gagal mengambil akun Google", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 6. VERIFIKASI TOKEN GOOGLE KE FIREBASE
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login Google Berhasil!", Toast.LENGTH_SHORT).show()
                    masukBeranda()
                } else {
                    Toast.makeText(this, "Firebase Gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun masukBeranda() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
