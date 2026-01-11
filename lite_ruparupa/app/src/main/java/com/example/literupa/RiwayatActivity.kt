package com.example.literupa

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.literupa.databinding.ActivityRiwayatBinding
import java.text.NumberFormat
import java.util.Locale

class RiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatBinding
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // GUNAKAN VIEW BINDING
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol Back
        binding.btnBackRiwayat.setOnClickListener {
            finish()
        }

        // Ambil data dari memori
        val pref = getSharedPreferences("RIWAYAT_PREF", Context.MODE_PRIVATE)
        val listRiwayat = pref.getStringSet("list_riwayat", setOf()) ?: setOf()

        if (listRiwayat.isEmpty()) {
            binding.tvDataRiwayat.text = "Belum ada riwayat pesanan.\nSilakan belanja terlebih dahulu."
        } else {
            // Ambil pesanan yang paling baru
            val dataTerakhir = listRiwayat.toList().last()
            mulaiSimulasiStatus(dataTerakhir)
        }
    }

    private fun mulaiSimulasiStatus(data: String) {
        try {
            val parts = data.split("|")
            if (parts.size >= 2) {
                val produk = parts[0]
                val hargaStr = parts[1].replace(Regex("[^0-9]"), "")
                val harga = hargaStr.toIntOrNull() ?: 0
                val tgl = if (parts.size > 2) parts[2] else "-"
                val metode = if (parts.size > 3) parts[3] else "COD"

                val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                val hargaFmt = formatRupiah.format(harga).replace("Rp", "Rp ")

                // TAHAP 1: Diproses
                binding.tvDataRiwayat.text = formatTeks(produk, hargaFmt, metode, tgl, "Pesanan Diproses 📦")

                // TAHAP 2: Dikirim (Setelah 5 Detik)
                handler.postDelayed({
                    binding.tvDataRiwayat.text = formatTeks(produk, hargaFmt, metode, tgl, "Pesanan Sedang Dikirim 🚚")

                    // TAHAP 3: Selesai (Setelah 5 Detik lagi)
                    handler.postDelayed({
                        binding.tvDataRiwayat.text = formatTeks(produk, hargaFmt, metode, tgl, "Pesanan Selesai ✅")
                    }, 5000)

                }, 5000)
            }
        } catch (e: Exception) {
            binding.tvDataRiwayat.text = "Gagal menampilkan data."
        }
    }

    private fun formatTeks(p: String, h: String, m: String, t: String, s: String): String {
        return "📦 Produk: $p\n" +
                "💰 Total: $h\n" +
                "💳 Metode: $m\n" +
                "🕒 Waktu: $t\n\n" +
                "---------------------------\n" +
                "📝 STATUS: $s\n" +
                "---------------------------\n\n" +
                "(Status berubah otomatis dalam 5 detik)"
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
