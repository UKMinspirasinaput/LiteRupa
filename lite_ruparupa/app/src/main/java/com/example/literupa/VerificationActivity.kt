package com.example.literupa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VerificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        val etOTP = findViewById<EditText>(R.id.etOTP)
        val btnVerify = findViewById<Button>(R.id.btnVerify)
        val tvInfo = findViewById<TextView>(R.id.tvInfoVerify)

        // Ambil nomor HP dari halaman Login sebelumnya
        val phoneNumber = intent.getStringExtra("PHONE_NUMBER")
        tvInfo.text = "Kode OTP dikirim ke $phoneNumber"

        btnVerify.setOnClickListener {
            val otpCode = etOTP.text.toString()

            // SIMULASI: Kode OTP benar adalah 1234
            if (otpCode == "1234") {
                Toast.makeText(this, "Verifikasi Berhasil!", Toast.LENGTH_SHORT).show()

                // Masuk ke Halaman Utama
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                etOTP.error = "Kode salah! Gunakan 1234"
                Toast.makeText(this, "Kode OTP salah, coba lagi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
