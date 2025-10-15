# Sistem Pengelolaan Logistik UKM KSR

Sistem pengelolaan logistik dan pengurus harian markas UKM KSR yang komprehensif dengan fitur manajemen barang, peminjaman, jadwal jaga medis, dan dashboard statistik.

## 🚀 Fitur Utama

### 1. Manajemen Pengguna
- **Registrasi** - Pendaftaran akun pengguna baru
- **Login** - Autentikasi dengan JWT token
- **Profil** - Melihat dan mengedit profil pengguna
- **Ganti Password** - Mengubah password akun
- **Hapus Akun** - Menghapus akun pengguna

### 2. Manajemen Data Barang
- **CRUD Barang** - Tambah, ubah, hapus, dan pencarian data inventaris
- **Kategori Barang** - Pengelompokan barang (alat medis, logistik, seragam, perlengkapan kegiatan)
- **Status Barang** - Tracking kondisi barang (tersedia, dipinjam, rusak, hilang)
- **Pencarian** - Pencarian barang berdasarkan nama, brand, model, dll

### 3. Sistem Peminjaman & Pengembalian
- **Permintaan Peminjaman** - Pengajuan peminjaman barang oleh anggota
- **Persetujuan** - Approval peminjaman oleh admin/staff logistik
- **Riwayat Peminjaman** - Pencatatan lengkap transaksi peminjaman
- **Pengembalian** - Proses pengembalian barang
- **Tracking Keterlambatan** - Monitoring barang yang terlambat dikembalikan

### 4. Penjadwalan Jaga Medis 🩺
- **Buat Jadwal** - Penentuan jadwal jaga medis berdasarkan tanggal, waktu, dan lokasi
- **Assignment Anggota** - Plot anggota ke dalam jadwal (otomatis/manual)
- **Kalender Kegiatan** - Tampilan kalender jadwal jaga
- **Status Tracking** - Monitoring status jadwal (terjadwal, berlangsung, selesai, dibatalkan)

### 5. Dashboard Statistik
- **Visualisasi Data** - Grafik dan statistik inventaris
- **Laporan Peminjaman** - Statistik peminjaman bulanan
- **Statistik Jadwal** - Data kegiatan jaga medis
- **Analisis Pengguna** - Distribusi pengguna berdasarkan role

### 6. Manajemen Role & Akses
- **Admin** - Akses penuh ke semua fitur
- **Staff Logistik** - Manajemen barang dan persetujuan peminjaman
- **Anggota** - Peminjaman barang dan melihat jadwal

## 🛠️ Teknologi yang Digunakan

- **Backend**: Java 17, Spring Boot 3.2.0
- **Security**: Spring Security dengan JWT Authentication
- **Database**: H2 Database (development), JPA/Hibernate
- **Documentation**: OpenAPI 3 (Swagger)
- **Build Tool**: Maven
- **Testing**: Spring Boot Test

## 📋 Prasyarat

- Java 17 atau lebih tinggi
- Maven 3.6 atau lebih tinggi
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Instalasi dan Menjalankan Aplikasi

### 1. Clone Repository
```bash
git clone <repository-url>
cd logistics-management
```

### 2. Build Aplikasi
```bash
mvn clean install
```

### 3. Jalankan Aplikasi
```bash
mvn spring-boot:run
```

Atau jalankan file JAR:
```bash
java -jar target/logistics-management-0.0.1-SNAPSHOT.jar
```

### 4. Akses Aplikasi
- **API Base URL**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

## 👥 Akun Default

Sistem akan otomatis membuat akun default saat pertama kali dijalankan:

| Role | Username | Password | Email |
|------|----------|----------|-------|
| Admin | admin | admin123 | admin@ukmksr.ac.id |
| Staff Logistik | logistics | logistics123 | logistics@ukmksr.ac.id |
| Anggota | member | member123 | member@ukmksr.ac.id |

## 📚 API Documentation

### Authentication Endpoints
```
POST /api/auth/register     - Registrasi pengguna baru
POST /api/auth/login        - Login pengguna
GET  /api/auth/validate     - Validasi token JWT
```

### User Management Endpoints
```
GET    /api/users/profile           - Lihat profil pengguna
PUT    /api/users/profile           - Update profil pengguna
PUT    /api/users/change-password   - Ganti password
DELETE /api/users/account           - Hapus akun
GET    /api/users/all              - Lihat semua pengguna (Admin)
GET    /api/users/{id}             - Lihat pengguna berdasarkan ID (Admin)
```

### Item Management Endpoints
```
POST   /api/items                  - Tambah barang baru
GET    /api/items                  - Lihat semua barang
GET    /api/items/{id}             - Lihat detail barang
PUT    /api/items/{id}             - Update barang
DELETE /api/items/{id}             - Hapus barang
GET    /api/items/search           - Cari barang
GET    /api/items/category/{cat}   - Barang berdasarkan kategori
GET    /api/items/status/{status}  - Barang berdasarkan status
```

### Borrowing Management Endpoints
```
POST   /api/borrowing/request      - Buat permintaan peminjaman
PUT    /api/borrowing/{id}/approve - Setujui peminjaman
PUT    /api/borrowing/{id}/reject  - Tolak peminjaman
PUT    /api/borrowing/{id}/return  - Kembalikan barang
GET    /api/borrowing              - Lihat semua catatan peminjaman
GET    /api/borrowing/my-records   - Lihat catatan peminjaman sendiri
GET    /api/borrowing/overdue      - Lihat peminjaman terlambat
```

### Medical Schedule Endpoints
```
POST   /api/medical-schedules              - Buat jadwal jaga medis
GET    /api/medical-schedules              - Lihat semua jadwal
GET    /api/medical-schedules/{id}         - Lihat detail jadwal
PUT    /api/medical-schedules/{id}         - Update jadwal
DELETE /api/medical-schedules/{id}         - Hapus jadwal
GET    /api/medical-schedules/my-schedules - Lihat jadwal sendiri
PUT    /api/medical-schedules/{id}/assign-members - Assign anggota ke jadwal
```

### Dashboard Endpoints
```
GET    /api/dashboard/stats        - Lihat statistik dashboard
```

## 🔐 Authentication

Semua endpoint (kecuali `/api/auth/register` dan `/api/auth/login`) memerlukan authentication menggunakan JWT token.

### Cara Menggunakan Token:
1. Login melalui `/api/auth/login` untuk mendapatkan token
2. Sertakan token di header request:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

## 🗄️ Database Schema

### Tabel Utama:
- **users** - Data pengguna dan role
- **items** - Data inventaris barang
- **borrowing_records** - Catatan peminjaman barang
- **medical_schedules** - Jadwal jaga medis
- **schedule_assignments** - Assignment anggota ke jadwal

## 🔧 Konfigurasi

### Application Properties
```yaml
# Database Configuration
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password: password

# JWT Configuration
jwt:
  secret: mySecretKey123456789012345678901234567890
  expiration: 86400000 # 24 hours

# Server Configuration
server:
  port: 8080
```

## 🧪 Testing

Jalankan unit test:
```bash
mvn test
```

## 📈 Monitoring dan Logging

- Log level dapat dikonfigurasi di `application.yml`
- H2 Console tersedia untuk monitoring database
- Swagger UI untuk testing API endpoints

## 🤝 Kontribusi

1. Fork repository
2. Buat feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push ke branch (`git push origin feature/AmazingFeature`)
5. Buat Pull Request

## 📝 License

Distributed under the MIT License. See `LICENSE` for more information.

## 📞 Kontak

UKM KSR Development Team - dev@ukmksr.ac.id

Project Link: [https://github.com/ukmksr/logistics-management](https://github.com/ukmksr/logistics-management)

---

**Catatan**: Aplikasi ini dikembangkan khusus untuk kebutuhan UKM KSR dalam mengelola logistik dan pengurus harian markas. Untuk deployment production, pastikan untuk menggunakan database yang lebih robust seperti PostgreSQL atau MySQL, dan konfigurasi keamanan yang sesuai.