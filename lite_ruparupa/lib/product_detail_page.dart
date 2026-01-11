import 'package:flutter/material.dart';

class ProductDetailPage extends StatelessWidget {
  final String id;
  final String title;
  final double price;
  final String description;

  const ProductDetailPage({Key? key, required this.id, required this.title, required this.price, required this.description}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Detail Produk')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
              height: 220,
              decoration: BoxDecoration(
                color: Colors.grey.shade200,
                borderRadius: BorderRadius.circular(8),
              ),
              child: const Center(child: Icon(Icons.image, size: 80, color: Colors.black26)),
            ),
            const SizedBox(height: 16),
            Text(title, style: const TextStyle(fontSize: 20, fontWeight: FontWeight.w700)),
            const SizedBox(height: 8),
            Text('Rp ${"" + price.toInt().toString()}', style: const TextStyle(fontSize: 16, color: Colors.black54)),
            const SizedBox(height: 12),
            const Text('Deskripsi', style: TextStyle(fontWeight: FontWeight.w600)),
            const SizedBox(height: 6),
            Text(description, style: const TextStyle(color: Colors.black87)),
            const Spacer(),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: () {
                  // no backend - simply show a snackbar
                  ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Produk ditambahkan (dummy)')));
                },
                child: const Text('Tambah ke Keranjang'),
              ),
            )
          ],
        ),
      ),
    );
  }
}

