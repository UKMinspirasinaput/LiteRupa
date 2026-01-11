package com.example.literupa

import android.content.Context

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.literupa.databinding.ActivityTransactionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionBinding
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().getReference("Transaksi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Ambil Data dari Intent (Harga)
        val hargaDiterima = intent.getStringExtra("TOTAL_BAYAR") ?: "Rp 0"
        binding.tvTotalPembayaran.text = hargaDiterima

        // 2. Tombol Konfirmasi & Bayar
        binding.btnBayarSekarang.setOnClickListener {
            val nama = binding.etNamaPenerima.text.toString() // Pastikan ID etNamaPenerima ada di XML
            val nomorHp = binding.etNoHP.text.toString() // Pastikan ID etNomorHp ada di XML
            val currentUser = auth.currentUser


            if (nama.isEmpty() || nomorHp.isEmpty()) {
                Toast.makeText(this, "Nama dan Nomor HP wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // A. SIMPAN KE FIREBASE
            val transId = database.push().key ?: System.currentTimeMillis().toString()
            val dataTransaksi = mapOf(
                "namaPenerima" to nama,
                "nomorHp" to nomorHp,
                "totalBayar" to hargaDiterima,
                "tanggal" to SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(Date())
            )

            database.child(transId).setValue(dataTransaksi)
                .addOnSuccessListener {
                    // B. SIMPAN RIWAYAT LOKAL (SharedPref)
                    val riwayatPref = getSharedPreferences("RiwayatBelanja", Context.MODE_PRIVATE)
                    val riwayatLama = riwayatPref.getString("data", "") ?: ""
                    val dataBaru = "Pembelian: ${dataTransaksi["tanggal"]} | Total: $hargaDiterima\n$riwayatLama"
                    riwayatPref.edit().putString("data", dataBaru).apply()

                    // C. KOSONGKAN KERANJANG & SELESAI
                    getSharedPreferences("LocalCart", Context.MODE_PRIVATE).edit().clear().apply()

                    Toast.makeText(this, "Transaksi Berhasil Tersimpan!", Toast.LENGTH_SHORT).show()

                    val intentHome = Intent(this, MainActivity::class.java)
                    intentHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intentHome)
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal simpan ke Firebase: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        binding.btnBack.setOnClickListener { finish() }
    }
}
