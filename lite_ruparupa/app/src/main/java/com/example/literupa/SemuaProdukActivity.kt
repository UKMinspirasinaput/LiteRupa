package com.example.literupa

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.Toast
import androidx.cardview.widget.CardView

class SemuaProdukActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semua_produk)

        val container = findViewById<LinearLayout>(R.id.containerSemuaProduk)

        // 1. TANGKAP KATA KUNCI DARI SEARCH BAR
        val kataKunci = intent.getStringExtra("QUERY_CARI")?.lowercase()

        // 2. DATA LENGKAP 20 PRODUK
        val dataProduk = listOf(
            "Levi Kabinet", "Wadah Plastik", "Kursi Lipat", "Meja Kerja", "Lampu Belajar",
            "Rak Sepatu", "Panci Set", "Sapu Pengki", "Blender Juicer", "Timbangan",
            "Kotak Makan", "Botol Minum", "Gantungan", "Karpet", "Payung",
            "Cermin", "Keset Kaki", "Gorden", "Hiasan", "Box Kotak"
        )

        // 3. LOGIKA FILTER (PENCARIAN)
        // Jika ada kata kunci, kita saring datanya. Jika tidak, ambil semua.
        val hasilFilter = if (!kataKunci.isNullOrEmpty()) {
            dataProduk.filter { it.lowercase().contains(kataKunci) }
        } else {
            dataProduk
        }

        // Tampilkan pesan jika barang tidak ditemukan
        if (hasilFilter.isEmpty()) {
            val tvKosong = TextView(this)
            tvKosong.text = "Produk '$kataKunci' tidak ditemukan"
            tvKosong.gravity = Gravity.CENTER
            tvKosong.setPadding(0, 100, 0, 0)
            container.addView(tvKosong)
        }

        // 4. TAMPILKAN HASIL KE LAYAR
        for (produk in hasilFilter) {
            val card = CardView(this)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 30) // Jarak antar kartu
            card.layoutParams = params
            card.radius = 20f
            card.cardElevation = 6f
            card.setCardBackgroundColor(Color.WHITE)

            val text = TextView(this)
            text.text = produk
            text.setPadding(60, 50, 60, 50)
            text.textSize = 18f
            text.setTypeface(null, Typeface.BOLD)
            text.setTextColor(Color.parseColor("#333333"))

            card.addView(text)

            // Tambahkan klik simulasi ke detail
            card.setOnClickListener {
                Toast.makeText(this, "Membuka Detail: $produk", Toast.LENGTH_SHORT).show()
            }

            container.addView(card)
        }
    }
}
