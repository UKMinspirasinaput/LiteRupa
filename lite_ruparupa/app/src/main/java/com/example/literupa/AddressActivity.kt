package com.example.literupa

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        val etAlamatLengkap = findViewById<EditText>(R.id.etAlamatLengkap)
        val btnSimpanAlamat = findViewById<Button>(R.id.btnSimpanAlamat)

        btnSimpanAlamat.setOnClickListener {
            val alamat = etAlamatLengkap.text.toString().trim()
            val uid = FirebaseAuth.getInstance().currentUser?.uid

            if (uid != null && alamat.isNotEmpty()) {
                // TEMBAK LANGSUNG KE URL DATABASE KAMU
                val databaseUrl = "https://lite-ruparupa-default-rtdb.firebaseio.com/"
                val db = FirebaseDatabase.getInstance(databaseUrl).getReference("Users")

                db.child(uid).child("profile").child("alamat").setValue(alamat)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Alamat Tersimpan!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Isi alamat dulu!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
