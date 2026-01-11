package com.example.literupa

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteAdapter(
    private val items: MutableList<Pair<String, String>>,
    private val onRemove: (Int) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val ivProduct: ImageView = v.findViewById(R.id.ivFavProduct)
        val tvName: TextView = v.findViewById(R.id.tvFavName)
        val tvPrice: TextView = v.findViewById(R.id.tvFavPrice)
        val btnRemove: ImageView = v.findViewById(R.id.btnRemoveFav)
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int): VH {
        val view = LayoutInflater.from(p.context).inflate(R.layout.item_favorite, p, false)
        return VH(view)
    }

    override fun onBindViewHolder(h: VH, p: Int) {
        val item = items[p]
        h.tvName.text = item.first
        h.tvPrice.text = item.second

        // Set gambar berdasarkan nama
        val imgRes = when (item.first) {
            "Levi Kabinet" -> R.drawable.levikabinet
            "Kursi Lipat" -> R.drawable.kursilipat
            "Wadah Plastik" -> R.drawable.wadahplastik
            else -> R.drawable.ic_launcher_background
        }
        h.ivProduct.setImageResource(imgRes)

        // KLIK MASUK KE DETAIL
        h.itemView.setOnClickListener {
            val intent = Intent(h.itemView.context, DetailActivity::class.java)
            intent.putExtra("NAMA_PRODUK", item.first)
            intent.putExtra("HARGA_PRODUK", item.second)
            h.itemView.context.startActivity(intent)
        }

        // KLIK HAPUS DARI FAVORIT
        h.btnRemove.setOnClickListener { onRemove(p) }
    }

    override fun getItemCount() = items.size
}
