package com.example.literupa

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

// Model data kategori
data class CategoryItem(val name: String, val image: Int)

class FragmentCategory : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        val gridView = view.findViewById<GridView>(R.id.gridCategory)

        // Data 4 Kategori Sederhana
        val categories = listOf(
            CategoryItem("Buku", R.drawable.cat_books),
            CategoryItem("Alat Tulis", R.drawable.cat_stationery),
            CategoryItem("Meja Belajar", R.drawable.cat_art),
            CategoryItem("Aksesori", R.drawable.cat_tech)
        )

        val adapter = object : BaseAdapter() {
            override fun getCount(): Int = categories.size
            override fun getItem(position: Int) = categories[position]
            override fun getItemId(position: Int): Long = position.toLong()

            @SuppressLint("MissingInflatedId")
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                // Baris ini mencari file item_category.xml
                val v = layoutInflater.inflate(R.layout.item_category, parent, false)
                val txt = v.findViewById<TextView>(R.id.txtCategoryName)
                val img = v.findViewById<ImageView>(R.id.imgCategory)

                txt?.text = categories[position].name
                img?.setImageResource(categories[position].image)
                return v
            }
        }

        gridView?.adapter = adapter

        gridView?.setOnItemClickListener { _, _, position, _ ->
            val selected = categories[position].name
            Toast.makeText(context, "Membuka Kategori $selected...", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
