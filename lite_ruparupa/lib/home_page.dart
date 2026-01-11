import 'package:flutter/material.dart';
import 'product_detail_page.dart';

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  final List<Map<String, dynamic>> products = const [
    {
      'id': 'p1',
      'title': 'Kursi Minimalis',
      'price': 249000.0,
      'image': '',
      'description': 'Kursi kayu minimalis, cocok untuk ruang tamu.'
    },
    {
      'id': 'p2',
      'title': 'Meja Makan',
      'price': 899000.0,
      'image': '',
      'description': 'Meja makan untuk 4 orang, finishing halus.'
    },
    {
      'id': 'p3',
      'title': 'Lampu Gantung',
      'price': 129000.0,
      'image': '',
      'description': 'Lampu gantung modern untuk ruang makan.'
    },
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Beranda'),
        actions: [
          IconButton(
            icon: const Icon(Icons.person_outline),
            onPressed: () => Navigator.pushNamed(context, '/profile'),
          )
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(12.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text('Kategori populer', style: TextStyle(fontSize: 16, fontWeight: FontWeight.w600)),
            const SizedBox(height: 8),
            SizedBox(
              height: 92,
              child: ListView(
                scrollDirection: Axis.horizontal,
                children: [
                  _buildCategoryCard('Furniture'),
                  _buildCategoryCard('Dekorasi'),
                  _buildCategoryCard('Lampu'),
                ],
              ),
            ),
            const SizedBox(height: 12),
            const Text('Rekomendasi untuk Anda', style: TextStyle(fontSize: 16, fontWeight: FontWeight.w600)),
            const SizedBox(height: 8),
            Expanded(
              child: GridView.builder(
                gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 2,
                  mainAxisSpacing: 12,
                  crossAxisSpacing: 12,
                  childAspectRatio: 0.78,
                ),
                itemCount: products.length,
                itemBuilder: (context, index) {
                  final p = products[index];
                  return GestureDetector(
                    onTap: () => Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (_) => ProductDetailPage(
                          id: p['id'],
                          title: p['title'],
                          price: p['price'],
                          description: p['description'],
                        ),
                      ),
                    ),
                    child: Container(
                      decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(8),
                        boxShadow: [
                          BoxShadow(color: Colors.black.withOpacity(0.04), blurRadius: 6, offset: const Offset(0, 2)),
                        ],
                      ),
                      padding: const EdgeInsets.all(8),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Container(
                            height: 100,
                            decoration: BoxDecoration(
                              color: Colors.grey.shade200,
                              borderRadius: BorderRadius.circular(6),
                            ),
                            child: const Center(child: Icon(Icons.image, size: 40, color: Colors.black26)),
                          ),
                          const SizedBox(height: 8),
                          Text(p['title'], style: const TextStyle(fontWeight: FontWeight.w600)),
                          const SizedBox(height: 4),
                          Text('Rp ${"" + (p['price'] as double).toInt().toString()}', style: const TextStyle(color: Colors.black54)),
                        ],
                      ),
                    ),
                  );
                },
              ),
            )
          ],
        ),
      ),
    );
  }

  Widget _buildCategoryCard(String title) {
    return Container(
      width: 140,
      margin: const EdgeInsets.only(right: 12),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(8),
        boxShadow: [BoxShadow(color: Colors.black.withOpacity(0.04), blurRadius: 6, offset: const Offset(0, 2))],
      ),
      padding: const EdgeInsets.all(12),
      child: Center(child: Text(title, style: const TextStyle(fontWeight: FontWeight.w600))),
    );
  }
}

