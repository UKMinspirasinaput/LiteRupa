package com.example.literupa

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ScanActivity : AppCompatActivity() {

    private lateinit var ivPreview: ImageView
    private lateinit var btnAllow: MaterialButton
    private lateinit var cardResult: LinearLayout
    private lateinit var tvHasilTitle: TextView
    private lateinit var tvProductName: TextView
    private lateinit var tvProductPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        // Inisialisasi View
        ivPreview = findViewById(R.id.ivPreview)
        btnAllow = findViewById(R.id.btnAllow)
        cardResult = findViewById(R.id.cardResult)
        tvHasilTitle = findViewById(R.id.tvHasilTitle)
        tvProductName = findViewById(R.id.tvProductName)
        tvProductPrice = findViewById(R.id.tvProductPrice)

        // Tombol Kembali
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        // Trigger Pilih Gambar dari Galeri
        btnAllow.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            val selectedImage = data?.data

            // 1. Tampilkan gambar pilihan di area preview atas
            ivPreview.setImageURI(selectedImage)
            ivPreview.setPadding(0, 0, 0, 0)

            // 2. Simulasi Proses Berjalan (Scanning Internal)
            btnAllow.text = "MENCOCOKKAN..."
            btnAllow.isEnabled = false
            tvHasilTitle.visibility = View.GONE
            cardResult.visibility = View.GONE

            Toast.makeText(this, "Mencari produk di database internal...", Toast.LENGTH_SHORT).show()

            // 3. Delay 1.5 Detik (Simulasi Pencocokan UAS)
            ivPreview.postDelayed({

                // Anggap gambar cocok dengan salah satu dari 20 produk tetap
                btnAllow.text = "AMBIL ULANG"
                btnAllow.isEnabled = true

                // Tampilkan Hasil (Hanya 1 Item, sama seperti di Beranda)
                tvHasilTitle.visibility = View.VISIBLE
                cardResult.visibility = View.VISIBLE

                // Set Data Produk secara Statis (Simulasi Berhasil)
                tvProductName.text = "Stora 36.5x26x8 Cm Kotak Penyimpanan"
                tvProductPrice.text = "Rp 12.913"

                Toast.makeText(this, "Produk Ditemukan!", Toast.LENGTH_SHORT).show()

            }, 1500)
        }
    }
}
