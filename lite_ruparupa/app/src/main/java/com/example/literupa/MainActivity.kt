package com.example.literupa

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var viewPagerBanner: ViewPager2
    private val sliderHandler = Handler(Looper.getMainLooper())

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. LOGIKA PENCARIAN REAL-TIME
        val etSearch = findViewById<EditText>(R.id.et_search_main)
        etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase().trim()
                filterProduk(query)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // 2. TOMBOL HEADER & NAVIGASI
        findViewById<ImageView>(R.id.btn_favorite_header)?.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        findViewById<View>(R.id.nav_home)?.setOnClickListener {
            Toast.makeText(this, "Kamu di Beranda", Toast.LENGTH_SHORT).show()
        }

        findViewById<View>(R.id.nav_kategori)?.setOnClickListener {
            startActivity(Intent(this, SemuaProdukActivity::class.java))
        }

        findViewById<View>(R.id.nav_scan)?.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }

        findViewById<View>(R.id.nav_profile)?.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }

        // Navigasi ke Keranjang
        findViewById<View>(R.id.btn_cart)?.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        setupBanner()
        setupAllProducts()
    }

    // --- PERBAIKAN: FUNGSI UPDATE ANGKA KERANJANG (LOKAL) ---
    private fun updateCartBadge() {
        val tvCartCount = findViewById<TextView>(R.id.tv_cart_count)
        // Samakan nama SharedPreferences dengan DetailActivity ("LocalCart")
        val sharedPref = getSharedPreferences("LocalCart", Context.MODE_PRIVATE)
        val itemsRaw = sharedPref.getString("items", "") ?: ""

        // Hitung jumlah barang berdasarkan pemisah ";"
        val listBarang = itemsRaw.split(";").filter { it.isNotEmpty() }
        val jumlahBarang = listBarang.size

        if (tvCartCount != null) {
            if (jumlahBarang > 0) {
                tvCartCount.visibility = View.VISIBLE
                tvCartCount.text = jumlahBarang.toString()
            } else {
                tvCartCount.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Angka akan terupdate otomatis saat kembali ke halaman ini
        updateCartBadge()
    }

    private fun filterProduk(query: String) {
        for (i in 1..20) {
            val cardId = resources.getIdentifier("cardProduk$i", "id", packageName)
            val txtId = resources.getIdentifier("txt$i", "id", packageName)

            val cardView = findViewById<View>(cardId)
            val textView = findViewById<TextView>(txtId)

            if (cardView != null && textView != null) {
                val namaProduk = textView.text.toString().lowercase()
                if (namaProduk.contains(query)) {
                    cardView.visibility = View.VISIBLE
                } else {
                    cardView.visibility = View.GONE
                }
            }
        }
    }

    private fun setupBanner() {
        viewPagerBanner = findViewById(R.id.viewPagerBanner)
        val listIklan = listOf(R.drawable.iklan2, R.drawable.iklan3, R.drawable.iklan1)
        viewPagerBanner.adapter = BannerAdapter(listIklan)

        val sliderRunnable = object : Runnable {
            override fun run() {
                if (listIklan.isNotEmpty()) {
                    val nextIndex = (viewPagerBanner.currentItem + 1) % listIklan.size
                    viewPagerBanner.setCurrentItem(nextIndex, true)
                    sliderHandler.postDelayed(this, 3000)
                }
            }
        }
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }

    private fun setupAllProducts() {
        val dataProduk = listOf(
            Pair("Levi Kabinet", "Rp1.699.000"), Pair("Wadah Plastik", "Rp39.900"),
            Pair("Kursi Lipat", "Rp150.000"), Pair("Meja Kerja", "Rp899.000"),
            Pair("Lampu Belajar", "Rp75.000"), Pair("Rak Sepatu", "Rp120.000"),
            Pair("Panci Set", "Rp350.000"), Pair("Sapu Pengki", "Rp55.000"),
            Pair("Blender Juicer", "Rp450.000"), Pair("Timbangan", "Rp95.000"),
            Pair("Kotak Makan", "Rp45.000"), Pair("Botol Minum", "Rp85.000"),
            Pair("Gantungan", "Rp25.000"), Pair("Karpet", "Rp210.000"),
            Pair("Payung", "Rp65.000"), Pair("Cermin", "Rp185.000"),
            Pair("Keset Kaki", "Rp15.000"), Pair("Gorden", "Rp110.000"),
            Pair("Hiasan", "Rp35.000"), Pair("Box Kotak", "Rp60.000")
        )

        for (i in 0 until 20) {
            val nomor = i + 1
            val cardId = resources.getIdentifier("cardProduk$nomor", "id", packageName)
            val starId = resources.getIdentifier("btn_star_$nomor", "id", packageName)

            if (cardId != 0 && i < dataProduk.size) {
                findViewById<View>(cardId)?.setOnClickListener {
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("NAMA_PRODUK", dataProduk[i].first)
                    intent.putExtra("HARGA_PRODUK", dataProduk[i].second)
                    startActivity(intent)
                }
            }

            if (starId != 0 && i < dataProduk.size) {
                val star = findViewById<ImageView>(starId)
                star?.let { logicSimpanFavorit(it, nomor, dataProduk[i].first, dataProduk[i].second) }
            }
        }
    }

    private fun logicSimpanFavorit(star: ImageView, nomor: Int, namaProduk: String, hargaProduk: String) {
        val pref = getSharedPreferences("FAVORIT_APP", MODE_PRIVATE)
        var isFav = pref.getBoolean("fav_$nomor", false)

        if (isFav) {
            star.setImageResource(android.R.drawable.btn_star_big_on)
            star.setColorFilter(Color.parseColor("#F26522"))
        }

        star.setOnClickListener {
            isFav = !isFav
            val editor = pref.edit()
            if (isFav) {
                star.setImageResource(android.R.drawable.btn_star_big_on)
                star.setColorFilter(Color.parseColor("#F26522"))
                editor.putBoolean("fav_$nomor", true)
                Toast.makeText(this, "$namaProduk masuk favorit", Toast.LENGTH_SHORT).show()
            } else {
                star.setImageResource(android.R.drawable.btn_star_big_off)
                star.setColorFilter(Color.GRAY)
                editor.remove("fav_$nomor")
            }
            editor.apply()
        }
    }

    class BannerAdapter(private val items: List<Int>) : RecyclerView.Adapter<BannerAdapter.VH>() {
        class VH(v: View) : RecyclerView.ViewHolder(v) {
            val imgBanner: ImageView = ImageView(v.context).apply {
                layoutParams = ViewGroup.LayoutParams(-1, -1)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            init { (v as ViewGroup).addView(imgBanner) }
        }
        override fun onCreateViewHolder(p: ViewGroup, t: Int) = VH(androidx.cardview.widget.CardView(p.context).apply {
            layoutParams = ViewGroup.LayoutParams(-1, -1)
            radius = 30f
        })
        override fun onBindViewHolder(h: VH, p: Int) { h.imgBanner.setImageResource(items[p]) }
        override fun getItemCount() = items.size
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacksAndMessages(null)
    }
}
