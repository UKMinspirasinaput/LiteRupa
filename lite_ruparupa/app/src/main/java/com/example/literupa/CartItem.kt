package com.example.literupa

data class CartItem(
    val nama: String,
    val harga: String,
    var jumlah: Int = 1,
    var isChecked: Boolean = true,
    val gambar: Int
)
