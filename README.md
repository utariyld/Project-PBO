# Dashboard Data Strategis BPS

Portal resmi Badan Pusat Statistik untuk data pembangunan nasional dan daerah yang interaktif dan informatif.

## 🚀 Fitur Utama

### Untuk Pengguna Umum
- **Beranda**: Ringkasan indikator strategis (inflasi, kemiskinan, IPM) dengan kartu interaktif dan grafik
- **Visualisasi Data**: Grafik dan peta interaktif dengan filter berdasarkan:
  - Kategori (ekonomi, sosial, lingkungan, pemerintahan)
  - Tahun dan periode
  - Wilayah geografis
- **Metadata**: Metodologi, sumber data, dan definisi indikator yang lengkap
- **Feedback**: Formulir untuk saran, laporan masalah, dan koreksi data
- **Tentang Kami**: Profil BPS, struktur organisasi, dan kontak resmi

### Untuk Admin/Petugas BPS
- **Dashboard Admin**: Statistik sistem dan aktivitas pengguna
- **Manajemen Indikator**: Kelola data dan indikator statistik
- **Manajemen Pengguna**: Administrasi akun dan hak akses
- **Kelola Feedback**: Monitor dan respons feedback dari pengguna
- **Pengaturan Sistem**: Konfigurasi sistem dan keamanan

## 🛠️ Teknologi yang Digunakan

- **Frontend**: React 18, Tailwind CSS
- **Routing**: React Router DOM
- **Grafik**: Chart.js, React-Chartjs-2, Recharts
- **Peta**: Leaflet, React-Leaflet
- **Icons**: React Icons (Feather Icons)
- **Animasi**: Framer Motion
- **HTTP Client**: Axios

## 🎨 Desain & UI/UX

### Tema Khas BPS
- **Warna Utama**: Biru BPS (#1e40af)
- **Warna Sekunder**: Biru terang (#3b82f6)
- **Warna Aksen**: Kuning/Emas (#f59e0b)
- **Font**: Inter (modern dan profesional)

### Responsif & Aksesibilitas
- Desain mobile-first yang responsif
- Navigasi yang mudah dipahami
- Kontras warna yang baik untuk keterbacaan
- Loading states dan feedback visual

## 🚀 Instalasi dan Menjalankan Proyek

### Prerequisites
- Node.js (versi 14 atau lebih baru)
- npm atau yarn

### Langkah Instalasi

1. Clone repository
```bash
git clone <repository-url>
cd dashboard-bps
```

2. Install dependencies
```bash
npm install
```

3. Jalankan aplikasi dalam mode development
```bash
npm start
```

4. Buka [http://localhost:3000](http://localhost:3000) di browser

### Build untuk Production
```bash
npm run build
```

## 👥 Kredensial Demo

Untuk mencoba fitur admin, gunakan kredensial berikut:

- **Admin**: 
  - Username: `admin`
  - Password: `admin123`

- **Petugas**: 
  - Username: `petugas`
  - Password: `petugas123`

## 📁 Struktur Proyek

```
src/
├── components/          # Komponen React yang dapat digunakan ulang
│   ├── Header.js       # Navigasi utama dan menu
│   └── Footer.js       # Footer dengan kontak dan media sosial
├── pages/              # Halaman-halaman utama
│   ├── Home.js         # Halaman beranda dengan indikator strategis
│   ├── VisualisasiData.js  # Grafik dan peta interaktif
│   ├── Metadata.js     # Metodologi dan definisi
│   ├── Feedback.js     # Formulir feedback dan saran
│   ├── TentangKami.js  # Profil BPS dan informasi organisasi
│   ├── Login.js        # Halaman login untuk petugas
│   └── Admin.js        # Dashboard admin untuk manajemen
├── index.css           # Styling global dengan Tailwind CSS
├── App.js              # Komponen utama dan routing
└── index.js            # Entry point aplikasi
```

## 🎯 Fitur yang Diimplementasikan

### ✅ Sudah Selesai
- [x] Struktur proyek dan konfigurasi dasar
- [x] Header dengan navigasi utama dan logo BPS
- [x] Footer dengan kontak dan media sosial
- [x] Halaman Beranda dengan kartu indikator dan grafik
- [x] Halaman Visualisasi Data dengan filter dan grafik interaktif
- [x] Halaman Metadata dengan metodologi dan definisi
- [x] Halaman Feedback dengan formulir lengkap
- [x] Halaman Tentang Kami dengan profil BPS
- [x] Sistem login dengan role-based access
- [x] Dashboard Admin untuk manajemen data
- [x] Desain responsif dan tema BPS

### 🔄 Pengembangan Lanjutan (Opsional)
- [ ] Integrasi dengan API BPS yang sesungguhnya
- [ ] Implementasi peta Indonesia yang interaktif
- [ ] Sistem notifikasi real-time
- [ ] Export data ke berbagai format (PDF, Excel, CSV)
- [ ] Pencarian dan filter data yang lebih advanced
- [ ] Dashboard analytics untuk tracking penggunaan
- [ ] Multilingual support (Indonesia/English)

## 📊 Data dan Visualisasi

### Indikator Strategis yang Ditampilkan
1. **Inflasi**: Tren bulanan dengan grafik line chart
2. **Tingkat Kemiskinan**: Data tahunan dengan bar chart
3. **IPM (Indeks Pembangunan Manusia)**: Progress per provinsi
4. **Pertumbuhan Ekonomi**: Data kuartalan dan sektoral

### Jenis Visualisasi
- Line charts untuk tren waktu
- Bar charts untuk perbandingan
- Doughnut charts untuk komposisi/proporsi
- Peta interaktif untuk data geospasial
- Cards dan metrics untuk ringkasan data

## 🔐 Keamanan

- Autentikasi berbasis role (Admin/Petugas)
- Session management
- Input validation dan sanitization
- Secure headers dan HTTPS ready
- Audit logging untuk aktivitas admin

## 📱 Kompatibilitas Browser

- Chrome (terbaru)
- Firefox (terbaru)
- Safari (terbaru)
- Edge (terbaru)
- Mobile browsers (iOS Safari, Chrome Mobile)

## 🤝 Kontribusi

Proyek ini dikembangkan khusus untuk Badan Pusat Statistik. Untuk kontribusi atau pertanyaan teknis, silakan hubungi tim pengembang internal BPS.

## 📄 Lisensi

© 2024 Badan Pusat Statistik Republik Indonesia. Hak Cipta Dilindungi.

---

**Catatan**: Dashboard ini adalah implementasi modern untuk meningkatkan aksesibilitas dan interaktivitas data statistik BPS kepada masyarakat luas dengan tetap mempertahankan akurasi dan integritas data yang menjadi standar BPS.