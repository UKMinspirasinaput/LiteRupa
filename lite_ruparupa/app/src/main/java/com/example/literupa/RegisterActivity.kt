package com.example.literupa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        // Ambil ID dari XML
        val etNama = findViewById<EditText>(R.id.etNama)
        val etEmail = findViewById<EditText>(R.id.etEmailDaftar)
        val etPass = findViewById<EditText>(R.id.etPasswordDaftar)
        val btnDaftar = findViewById<Button>(R.id.btnProsesDaftar)

        btnDaftar.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass = etPass.text.toString().trim()

            if (nama.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()) {

                // 1. PROSES DAFTAR AKUN KE FIREBASE AUTH
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val uid = auth.currentUser?.uid

                            // 2. KODE KILAT: SIMPAN DATA PROFIL KE URL DATABASE LANGSUNG
                            val databaseUrl = "https://lite-ruparupa-default-rtdb.firebaseio.com/"
                            val database = FirebaseDatabase.getInstance(databaseUrl).getReference("Users")

                            val userMap = mapOf(
                                "nama" to nama,
                                "email" to email,
                                "uid" to uid
                            )

                            if (uid != null) {
                                database.child(uid).child("profile").setValue(userMap)
                                    .addOnSuccessListener {
                                        // 3. SIMPAN JUGA KE LOCAL HP
                                        val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
                                        val editor = sharedPref.edit()
                                        editor.putString("nama_user", nama)
                                        editor.putString("email_user", email)
                                        editor.apply()

                                        Toast.makeText(this, "Registrasi Sukses!", Toast.LENGTH_SHORT).show()

                                        // Pindah ke Login
                                        startActivity(Intent(this, LoginActivity::class.java))
                                        finish()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this, "Gagal simpan database: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Toast.makeText(this, "Gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Harap isi semua kolom!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
