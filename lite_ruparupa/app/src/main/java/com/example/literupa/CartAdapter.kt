package com.example.literupa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val items: MutableList<CartItem>,
    private val onUpdate: () -> Unit
) : RecyclerView.Adapter<CartAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val cb: CheckBox = v.findViewById(R.id.cbItemCart)
        val tvNama: TextView = v.findViewById(R.id.tvCartNama)
        val tvHarga: TextView = v.findViewById(R.id.tvCartHarga)
        val img: ImageView = v.findViewById(R.id.ivCartGambar)
        val tvQty: TextView = v.findViewById(R.id.tvQty)
        val btnPlus: TextView = v.findViewById(R.id.btnPlus)
        val btnMinus: TextView = v.findViewById(R.id.btnMinus)
        val btnDelete: ImageView = v.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        val context = holder.itemView.context

        holder.tvNama.text = item.nama
        holder.tvHarga.text = item.harga
        holder.tvQty.text = item.jumlah.toString()
        holder.img.setImageResource(item.gambar)

        // Supaya listener tidak kepanggil saat scroll
        holder.cb.setOnCheckedChangeListener(null)
        holder.cb.isChecked = item.isChecked

        // 1. LOGIKA CENTANG
        holder.cb.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
            onUpdate()
        }

        // 2. LOGIKA TAMBAH (+)
        holder.btnPlus.setOnClickListener {
            item.jumlah++
            holder.tvQty.text = item.jumlah.toString()
            saveToLocal(context) // Simpan perubahan ke HP
            onUpdate()
        }

        // 3. LOGIKA KURANG (-)
        holder.btnMinus.setOnClickListener {
            if (item.jumlah > 1) {
                item.jumlah--
                holder.tvQty.text = item.jumlah.toString()
                saveToLocal(context) // Simpan perubahan ke HP
                onUpdate()
            }
        }

        // 4. LOGIKA HAPUS
        holder.btnDelete.setOnClickListener {
            items.removeAt(position)
            notifyDataSetChanged()
            saveToLocal(context) // Simpan perubahan ke HP
            onUpdate()
        }
    }

    override fun getItemCount(): Int = items.size

    // FUNGSI SIMPAN PERUBAHAN KE MEMORI HP (PENTING!)
    private fun saveToLocal(context: Context) {
        val sharedPref = context.getSharedPreferences("LocalCart", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val sb = StringBuilder()

        for (item in items) {
            // Simpan sebanyak jumlah barang yang ada
            for (i in 1..item.jumlah) {
                sb.append("${item.nama}|${item.harga};")
            }
        }
        editor.putString("items", sb.toString())
        editor.apply()
    }
}
