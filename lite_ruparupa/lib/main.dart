import 'package:flutter/material.dart';
import 'login_page.dart';
import 'home_page.dart';
import 'product_detail_page.dart';
import 'profile_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  static const Color primary = Color(0xFF0D47A1);
  static const Color accent = Color(0xFFFF6F00);
  static const Color background = Color(0xFFF5F5F5);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Lite Ruparupa',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primaryColor: primary,
        colorScheme: ColorScheme.fromSwatch(primarySwatch: Colors.blue).copyWith(secondary: accent),
        scaffoldBackgroundColor: background,
        appBarTheme: const AppBarTheme(
          backgroundColor: primary,
          foregroundColor: Colors.white,
          elevation: 2,
        ),
      ),
      home: const LoginPage(),
      routes: {
        '/login': (context) => const LoginPage(),
        '/home': (context) => const HomePage(),
        '/profile': (context) => const ProfilePage(),
      },
    );
  }
}

