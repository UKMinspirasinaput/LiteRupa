import 'package:flutter/material.dart';

class ProfilePage extends StatelessWidget {
  const ProfilePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Profil')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            Container(
              width: 92,
              height: 92,
              decoration: BoxDecoration(
                color: Colors.grey.shade200,
                borderRadius: BorderRadius.circular(46),
              ),
              child: const Center(child: Icon(Icons.person, size: 48, color: Colors.black26)),
            ),
            const SizedBox(height: 12),
            const Text('Nama Pengguna', style: TextStyle(fontSize: 18, fontWeight: FontWeight.w700)),
            const SizedBox(height: 4),
            const Text('user@example.com', style: TextStyle(color: Colors.black54)),
            const SizedBox(height: 20),
            Card(
              child: ListTile(
                leading: const Icon(Icons.settings_outlined),
                title: const Text('Pengaturan'),
                onTap: () {},
              ),
            ),
            const SizedBox(height: 8),
            Card(
              child: ListTile(
                leading: const Icon(Icons.logout),
                title: const Text('Keluar'),
                onTap: () => Navigator.pushReplacementNamed(context, '/login'),
              ),
            ),
            const Spacer(),
            const Text('Lite Ruparupa - UI only', style: TextStyle(color: Colors.black45)),
            const SizedBox(height: 12),
          ],
        ),
      ),
    );
  }
}

