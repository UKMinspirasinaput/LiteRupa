import 'package:flutter/material.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({Key? key}) : super(key: key);

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  void _login() {
    // No backend: simply navigate to home
    Navigator.pushReplacementNamed(context, '/home');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              const SizedBox(height: 40),
              const Text(
                'Lite Ruparupa',
                textAlign: TextAlign.center,
                style: TextStyle(fontSize: 28, fontWeight: FontWeight.w600),
              ),
              const SizedBox(height: 8),
              const Text(
                'Produk rumah & furnitur - versi LITE',
                textAlign: TextAlign.center,
                style: TextStyle(fontSize: 14, color: Colors.black54),
              ),
              const SizedBox(height: 48),
              TextField(
                controller: _emailController,
                keyboardType: TextInputType.emailAddress,
                decoration: const InputDecoration(
                  labelText: 'Email',
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 16),
              TextField(
                controller: _passwordController,
                obscureText: true,
                decoration: const InputDecoration(
                  labelText: 'Password',
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 24),
              ElevatedButton(
                onPressed: _login,
                style: ElevatedButton.styleFrom(padding: const EdgeInsets.symmetric(vertical: 14)),
                child: const Text('Masuk', style: TextStyle(fontSize: 16)),
              ),
              const SizedBox(height: 12),
              TextButton(
                onPressed: () {},
                child: const Text('Daftar akun baru'),
              ),
              const Spacer(),
              const Text(
                'Versi LITE - UI only',
                textAlign: TextAlign.center,
                style: TextStyle(color: Colors.black45),
              ),
              const SizedBox(height: 12),
            ],
          ),
        ),
      ),
    );
  }
}

