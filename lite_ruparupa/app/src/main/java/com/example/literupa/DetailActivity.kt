package com.example.literupa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // 1. Inisialisasi View
        val imgDetail = findViewById<ImageView>(R.id.ivDetailGambar)
        val txtNama = findViewById<TextView>(R.id.tvDetailNama)
        val txtHarga = findViewById<TextView>(R.id.tvDetailHarga)
        val txtDeskripsi = findViewById<TextView>(R.id.tvDetailDeskripsi)
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnBeli = findViewById<Button>(R.id.btnBeliSekarang)
        val btnTambah = findViewById<Button>(R.id.btnTambahKeranjang)

        // 2. Ambil data dari intent
        val namaProduk = intent.getStringExtra("NAMA_PRODUK") ?: "Produk"
        val hargaProdukStr = intent.getStringExtra("HARGA_PRODUK") ?: "Rp 0"

        txtNama.text = namaProduk
        txtHarga.text = hargaProdukStr

        // 3. Setup Konten Gambar & Deskripsi
        setupContent(namaProduk, imgDetail, txtDeskripsi)

        // 4. Tombol Kembali
        btnBack.setOnClickListener { finish() }

        // 5. TOMBOL TAMBAH KERANJANG (SEKARANG LOKAL)
        btnTambah.setOnClickListener {
            saveToLocalCart(namaProduk, hargaProdukStr)
            Toast.makeText(this, "✅ $namaProduk masuk keranjang (Lokal)!", Toast.LENGTH_SHORT).show()
        }

        // 6. TOMBOL BELI SEKARANG (LOKAL + PINDAH HALAMAN)
        btnBeli.setOnClickListener {
            // Pastikan user sudah login (Cek via Firebase)
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                saveToLocalCart(namaProduk, hargaProdukStr)
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Login dulu ya Put!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // FUNGSI SIMPAN LOKAL (Shared Preferences)
    private fun saveToLocalCart(nama: String, harga: String) {
        val sharedPref = getSharedPreferences("LocalCart", Context.MODE_PRIVATE)
        val currentItems = sharedPref.getString("items", "")
        val editor = sharedPref.edit()

        // Simpan data dengan pemisah | dan ;
        val updatedItems = "$currentItems$nama|$harga;"
        editor.putString("items", updatedItems)
        editor.apply()
    }

    private fun setupContent(nama: String, img: ImageView, desc: TextView) {
        when {
            nama.contains("Levi", true) -> {
                img.setImageResource(R.drawable.levikabinet)
                desc.text = "Kabinet bergaya minimalis untuk ruang tamu Anda."
            }
            nama.contains("Wadah", true) -> {
                img.setImageResource(R.drawable.wadahplastik)
                desc.text = "Wadah serbaguna untuk menyimpan makanan agar tetap segar."
            }
            nama.contains("Kursi", true) -> {
                img.setImageResource(R.drawable.kursilipat)
                desc.text = "Kursi lipat yang kokoh dan mudah disimpan."
            }
            nama.contains("Meja", true) -> {
                img.setImageResource(R.drawable.mejakerja)
                desc.text = "Meja kerja luas dengan desain modern untuk produktivitas Anda."
            }
            nama.contains("Lampu", true) -> {
                img.setImageResource(R.drawable.lampubelajar)
                desc.text = "Lampu LED hemat energi dengan pencahayaan yang nyaman di mata."
            }
            nama.contains("Rak", true) -> {
                img.setImageResource(R.drawable.raksepatu)
                desc.text = "Rak sepatu bertingkat untuk menata koleksi alas kaki Anda."
            }
            else -> {
                img.setImageResource(R.drawable.ic_launcher_background)
                desc.text = "Produk berkualitas tinggi dari RupaRupa Lite."
            }
        }
    }
}
