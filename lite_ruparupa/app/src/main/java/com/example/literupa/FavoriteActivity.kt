package com.example.literupa

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteActivity : AppCompatActivity() {

    private lateinit var rvFav: RecyclerView
    private val favList = mutableListOf<Pair<String, String>>()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        rvFav = findViewById(R.id.rvFavorite)
        findViewById<ImageView>(R.id.btnBackFav).setOnClickListener { finish() }

        loadFavorites()
        setupRecyclerView()
    }

    private fun loadFavorites() {
        val pref = getSharedPreferences("FAVORIT_APP", MODE_PRIVATE)

        // List produk yang sama dengan di MainActivity kamu
        val allProducts = listOf(
            Pair("Levi Kabinet", "Rp1.699.000"),
            Pair("Wadah Plastik", "Rp39.900"),
            Pair("Kursi Lipat", "Rp150.000"),
            Pair("Meja Kerja", "Rp899.000"),
            Pair("Lampu Belajar", "Rp120.000"),
            Pair("Rak Sepatu", "Rp250.000")
        )

        favList.clear()
        // Cek satu per satu di memori HP, mana yang ditandai favorit (true)
        for (i in allProducts.indices) {
            if (pref.getBoolean("fav_${i + 1}", false)) {
                favList.add(allProducts[i])
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = FavoriteAdapter(favList) { position ->
            // Logika saat tombol hati diklik (Hapus dari favorit)
            val pref = getSharedPreferences("FAVORIT_APP", MODE_PRIVATE)
            val editor = pref.edit()
            // Kita perlu cari tahu ini index ke berapa di list aslinya untuk set false
            // Namun untuk simulasi sederhana, kita hapus dari list layar saja:
            favList.removeAt(position)
            adapter.notifyItemRemoved(position)
        }

        rvFav.layoutManager = GridLayoutManager(this, 2) // Tampilan 2 Kolom
        rvFav.adapter = adapter
    }
}
