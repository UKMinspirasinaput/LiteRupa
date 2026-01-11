package com.example.literupa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AccountActivity : AppCompatActivity() {

    private val databaseUrl = "https://lite-ruparupa-default-rtdb.firebaseio.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        // 1. Inisialisasi View sesuai XML kamu
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvNama = findViewById<TextView>(R.id.tvNamaUser)
        val tvEmail = findViewById<TextView>(R.id.tvEmailUser)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // Menu-menu klik
        val menuAlamat = findViewById<LinearLayout>(R.id.menuAlamat)
        val menuRiwayat = findViewById<LinearLayout>(R.id.menuRiwayat)

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid != null) {
            // Ambil Nama & Email dari Firebase
            val db = FirebaseDatabase.getInstance(databaseUrl).getReference("Users").child(uid).child("profile")
            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        tvNama.text = snapshot.child("nama").value.toString()
                        tvEmail.text = snapshot.child("email").value.toString()
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        }

        // Navigasi Tombol Back
        btnBack.setOnClickListener { finish() }

        // Navigasi ke Halaman Alamat
        menuAlamat.setOnClickListener {
            startActivity(Intent(this, AddressActivity::class.java))
        }

        // Navigasi ke Halaman Riwayat
        menuRiwayat.setOnClickListener {
            startActivity(Intent(this, RiwayatActivity::class.java))
        }

        // Tombol Logout
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
