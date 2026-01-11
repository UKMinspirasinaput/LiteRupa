package com.example.literupa


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.literupa.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackCart.setOnClickListener { finish() }

        loadCartData()

        // PERBAIKAN: Tombol Checkout hanya pindah halaman SEKALI setelah diklik
        binding.btnCheckout.setOnClickListener {
            val totalBayar = binding.tvTotalPembayaran.text.toString()
            val sharedPref = getSharedPreferences("LocalCart", MODE_PRIVATE)
            val itemsRaw = sharedPref.getString("items", "") ?: ""

            if (itemsRaw.isNotEmpty()) {
                val intent = Intent(this, TransactionActivity::class.java)
                intent.putExtra("TOTAL_BAYAR", totalBayar) // Kirim total harga ke checkout
                startActivity(intent)
            } else {
                Toast.makeText(this, "Keranjang kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadCartData() {
        val sharedPref = getSharedPreferences("LocalCart", Context.MODE_PRIVATE)
        val itemsRaw = sharedPref.getString("items", "") ?: ""

        val listCartItem = mutableListOf<CartItem>()
        var totalHarga = 0

        if (itemsRaw.isNotEmpty()) {
            val items = itemsRaw.split(";").filter { it.contains("|") }
            for (item in items) {
                val detail = item.split("|")
                if (detail.size >= 2) {
                    val nama = detail[0]
                    val hargaStr = detail[1]
                    val hargaAngka = hargaStr.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0

                    // Menentukan gambar berdasarkan nama
                    val gambarRes = when {
                        nama.contains("Levi", true) -> R.drawable.levikabinet
                        nama.contains("Wadah", true) -> R.drawable.wadahplastik
                        nama.contains("Kursi", true) -> R.drawable.kursilipat
                        nama.contains("Meja", true) -> R.drawable.mejakerja
                        nama.contains("Lampu", true) -> R.drawable.lampubelajar
                        nama.contains("Rak", true) -> R.drawable.raksepatu
                        else -> R.drawable.ic_launcher_background
                    }

                    // Tambahkan ke list (URUTAN: nama, harga, jumlah, isChecked, gambar)
                    listCartItem.add(CartItem(nama, hargaStr, 1, true, gambarRes))
                    totalHarga += hargaAngka
                }
            }
        }

        // Tampilkan ke RecyclerView
        binding.rvCart.layoutManager = LinearLayoutManager(this)
        binding.rvCart.adapter = CartAdapter(listCartItem) {
            // Jika ada perubahan (+/-), hitung ulang total
            recalculateTotal(listCartItem)
        }

        binding.tvTotalPembayaran.text = "Rp $totalHarga"
    }

    // Fungsi tambahan agar hitung total sinkron saat tombol di klik
    private fun recalculateTotal(list: List<CartItem>) {
        var newTotal = 0
        for (item in list) {
            if (item.isChecked) {
                val hargaAngka = item.harga.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
                newTotal += (hargaAngka * item.jumlah)
            }
        }
        binding.tvTotalPembayaran.text = "Rp $newTotal"
    }
}
