import React, { useState } from 'react';
import { 
  FiBarChart3, 
  FiUsers, 
  FiDatabase,
  FiSettings,
  FiMessageSquare,
  FiTrendingUp,
  FiDownload,
  FiPlus,
  FiEdit,
  FiTrash2,
  FiSearch,
  FiFilter,
  FiRefreshCw,
  FiEye,
  FiCheckCircle,
  FiAlertCircle
} from 'react-icons/fi';

const AdminCard = ({ title, value, change, changeType, icon: Icon, color }) => {
  return (
    <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
      <div className="flex items-center justify-between">
        <div>
          <p className="text-sm font-medium text-gray-600 mb-1">{title}</p>
          <p className="text-2xl font-bold text-gray-900">{value}</p>
          {change && (
            <div className={`flex items-center space-x-1 mt-2 ${
              changeType === 'increase' ? 'text-green-600' : 'text-red-600'
            }`}>
              <FiTrendingUp className="w-4 h-4" />
              <span className="text-sm font-medium">{change}</span>
            </div>
          )}
        </div>
        <div className={`p-3 rounded-full ${color}`}>
          <Icon className="w-6 h-6 text-white" />
        </div>
      </div>
    </div>
  );
};

const DataTable = ({ title, data, columns, onEdit, onDelete, onAdd }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 10;

  const filteredData = data.filter(item =>
    Object.values(item).some(value =>
      value?.toString().toLowerCase().includes(searchTerm.toLowerCase())
    )
  );

  const totalPages = Math.ceil(filteredData.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const paginatedData = filteredData.slice(startIndex, startIndex + itemsPerPage);

  return (
    <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-6">
        <h3 className="text-lg font-semibold text-gray-900">{title}</h3>
        <div className="flex space-x-2 mt-4 sm:mt-0">
          <div className="relative">
            <FiSearch className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400" />
            <input
              type="text"
              placeholder="Cari..."
              value={searchTerm}
              onChange={(e) => {
                setSearchTerm(e.target.value);
                setCurrentPage(1);
              }}
              className="pl-10 pr-4 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-bps-primary focus:border-transparent"
            />
          </div>
          <button
            onClick={onAdd}
            className="btn-primary text-sm flex items-center space-x-2"
          >
            <FiPlus className="w-4 h-4" />
            <span>Tambah</span>
          </button>
        </div>
      </div>

      <div className="overflow-x-auto">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              {columns.map((column, index) => (
                <th
                  key={index}
                  className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  {column.header}
                </th>
              ))}
              <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                Aksi
              </th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {paginatedData.map((item, index) => (
              <tr key={item.id || index} className="hover:bg-gray-50">
                {columns.map((column, colIndex) => (
                  <td key={colIndex} className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {column.render ? column.render(item[column.key], item) : item[column.key]}
                  </td>
                ))}
                <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                  <div className="flex justify-end space-x-2">
                    <button
                      onClick={() => onEdit(item)}
                      className="text-bps-primary hover:text-blue-700 transition-colors duration-200"
                    >
                      <FiEdit className="w-4 h-4" />
                    </button>
                    <button
                      onClick={() => onDelete(item)}
                      className="text-red-600 hover:text-red-800 transition-colors duration-200"
                    >
                      <FiTrash2 className="w-4 h-4" />
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Pagination */}
      {totalPages > 1 && (
        <div className="flex items-center justify-between mt-6">
          <div className="text-sm text-gray-700">
            Menampilkan {startIndex + 1} - {Math.min(startIndex + itemsPerPage, filteredData.length)} dari {filteredData.length} data
          </div>
          <div className="flex space-x-2">
            <button
              onClick={() => setCurrentPage(prev => Math.max(prev - 1, 1))}
              disabled={currentPage === 1}
              className="px-3 py-1 border border-gray-300 rounded text-sm disabled:opacity-50 disabled:cursor-not-allowed"
            >
              Sebelumnya
            </button>
            <button
              onClick={() => setCurrentPage(prev => Math.min(prev + 1, totalPages))}
              disabled={currentPage === totalPages}
              className="px-3 py-1 border border-gray-300 rounded text-sm disabled:opacity-50 disabled:cursor-not-allowed"
            >
              Selanjutnya
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

const FeedbackList = ({ feedbacks, onMarkResolved }) => {
  return (
    <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
      <div className="flex items-center justify-between mb-6">
        <h3 className="text-lg font-semibold text-gray-900">Feedback Terbaru</h3>
        <button className="btn-secondary text-sm">Lihat Semua</button>
      </div>
      
      <div className="space-y-4">
        {feedbacks.map((feedback) => (
          <div key={feedback.id} className="border border-gray-200 rounded-lg p-4">
            <div className="flex items-start justify-between">
              <div className="flex-1">
                <div className="flex items-center space-x-2 mb-2">
                  <span className={`px-2 py-1 text-xs rounded-full ${
                    feedback.type === 'suggestion' ? 'bg-blue-100 text-blue-800' :
                    feedback.type === 'bug' ? 'bg-red-100 text-red-800' :
                    feedback.type === 'data' ? 'bg-yellow-100 text-yellow-800' :
                    'bg-gray-100 text-gray-800'
                  }`}>
                    {feedback.typeLabel}
                  </span>
                  <span className={`px-2 py-1 text-xs rounded-full ${
                    feedback.status === 'resolved' ? 'bg-green-100 text-green-800' :
                    'bg-orange-100 text-orange-800'
                  }`}>
                    {feedback.status === 'resolved' ? 'Selesai' : 'Pending'}
                  </span>
                </div>
                <h4 className="font-medium text-gray-900 mb-1">{feedback.subject}</h4>
                <p className="text-sm text-gray-600 mb-2">{feedback.message}</p>
                <p className="text-xs text-gray-500">
                  Dari: {feedback.name} ({feedback.email}) - {feedback.date}
                </p>
              </div>
              {feedback.status !== 'resolved' && (
                <button
                  onClick={() => onMarkResolved(feedback.id)}
                  className="ml-4 text-green-600 hover:text-green-800 transition-colors duration-200"
                >
                  <FiCheckCircle className="w-5 h-5" />
                </button>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

const Admin = ({ user }) => {
  const [activeTab, setActiveTab] = useState('dashboard');

  // Sample data
  const dashboardStats = [
    {
      title: "Total Pengguna",
      value: "1,847",
      change: "+12% bulan ini",
      changeType: "increase",
      icon: FiUsers,
      color: "bg-blue-500"
    },
    {
      title: "Dataset Aktif",
      value: "156",
      change: "+3 dataset baru",
      changeType: "increase",
      icon: FiDatabase,
      color: "bg-green-500"
    },
    {
      title: "Visualisasi",
      value: "89",
      change: "+8 visualisasi",
      changeType: "increase",
      icon: FiBarChart3,
      color: "bg-purple-500"
    },
    {
      title: "Feedback Pending",
      value: "23",
      change: "-5 dari minggu lalu",
      changeType: "decrease",
      icon: FiMessageSquare,
      color: "bg-orange-500"
    }
  ];

  const indicators = [
    { id: 1, name: "Inflasi", category: "Ekonomi", source: "BPS", lastUpdate: "Nov 2024", status: "Aktif" },
    { id: 2, name: "Kemiskinan", category: "Sosial", source: "BPS", lastUpdate: "Okt 2024", status: "Aktif" },
    { id: 3, name: "IPM", category: "Sosial", source: "BPS", lastUpdate: "Sep 2024", status: "Aktif" },
    { id: 4, name: "Pertumbuhan Ekonomi", category: "Ekonomi", source: "BPS", lastUpdate: "Nov 2024", status: "Aktif" }
  ];

  const users = [
    { id: 1, name: "Ahmad Surya", email: "ahmad@bps.go.id", role: "Admin", status: "Aktif", lastLogin: "2 jam lalu" },
    { id: 2, name: "Siti Nurhaliza", email: "siti@bps.go.id", role: "Petugas", status: "Aktif", lastLogin: "1 hari lalu" },
    { id: 3, name: "Bambang Sutrisno", email: "bambang@bps.go.id", role: "Petugas", status: "Aktif", lastLogin: "3 hari lalu" }
  ];

  const feedbacks = [
    {
      id: 1,
      name: "John Doe",
      email: "john@example.com",
      type: "suggestion",
      typeLabel: "Saran",
      subject: "Perbaikan UI Dashboard",
      message: "Dashboard bisa ditambahkan fitur filter yang lebih detail...",
      date: "2 jam lalu",
      status: "pending"
    },
    {
      id: 2,
      name: "Jane Smith",
      email: "jane@example.com",
      type: "bug",
      typeLabel: "Bug",
      subject: "Error saat download data",
      message: "Terjadi error ketika mencoba download data inflasi...",
      date: "5 jam lalu",
      status: "pending"
    }
  ];

  const indicatorColumns = [
    { header: "Nama Indikator", key: "name" },
    { header: "Kategori", key: "category" },
    { header: "Sumber", key: "source" },
    { header: "Update Terakhir", key: "lastUpdate" },
    { 
      header: "Status", 
      key: "status",
      render: (value) => (
        <span className={`px-2 py-1 text-xs rounded-full ${
          value === 'Aktif' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
        }`}>
          {value}
        </span>
      )
    }
  ];

  const userColumns = [
    { header: "Nama", key: "name" },
    { header: "Email", key: "email" },
    { header: "Role", key: "role" },
    { 
      header: "Status", 
      key: "status",
      render: (value) => (
        <span className={`px-2 py-1 text-xs rounded-full ${
          value === 'Aktif' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
        }`}>
          {value}
        </span>
      )
    },
    { header: "Login Terakhir", key: "lastLogin" }
  ];

  const tabs = [
    { id: 'dashboard', label: 'Dashboard', icon: FiBarChart3 },
    { id: 'indicators', label: 'Manajemen Indikator', icon: FiDatabase },
    { id: 'users', label: 'Manajemen Pengguna', icon: FiUsers },
    { id: 'feedback', label: 'Daftar Feedback', icon: FiMessageSquare },
    { id: 'settings', label: 'Pengaturan Sistem', icon: FiSettings }
  ];

  const handleEdit = (item) => {
    console.log('Edit item:', item);
    // Implement edit functionality
  };

  const handleDelete = (item) => {
    console.log('Delete item:', item);
    // Implement delete functionality
  };

  const handleAdd = () => {
    console.log('Add new item');
    // Implement add functionality
  };

  const handleMarkResolved = (feedbackId) => {
    console.log('Mark feedback as resolved:', feedbackId);
    // Implement mark as resolved functionality
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <div className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-2xl font-bold text-gray-900">Dashboard Admin</h1>
              <p className="text-gray-600">Selamat datang, {user?.name}</p>
            </div>
            <div className="flex items-center space-x-4">
              <button className="btn-secondary flex items-center space-x-2">
                <FiDownload className="w-4 h-4" />
                <span>Export Data</span>
              </button>
              <button className="btn-primary flex items-center space-x-2">
                <FiRefreshCw className="w-4 h-4" />
                <span>Refresh</span>
              </button>
            </div>
          </div>
        </div>
      </div>

      {/* Navigation Tabs */}
      <div className="bg-white border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <nav className="flex space-x-8">
            {tabs.map((tab) => {
              const Icon = tab.icon;
              return (
                <button
                  key={tab.id}
                  onClick={() => setActiveTab(tab.id)}
                  className={`flex items-center space-x-2 py-4 px-1 border-b-2 font-medium text-sm ${
                    activeTab === tab.id
                      ? 'border-bps-primary text-bps-primary'
                      : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                  }`}
                >
                  <Icon className="w-4 h-4" />
                  <span>{tab.label}</span>
                </button>
              );
            })}
          </nav>
        </div>
      </div>

      {/* Content */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {activeTab === 'dashboard' && (
          <div className="space-y-8">
            {/* Stats Grid */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
              {dashboardStats.map((stat, index) => (
                <AdminCard key={index} {...stat} />
              ))}
            </div>

            {/* Recent Activity */}
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
              <FeedbackList feedbacks={feedbacks} onMarkResolved={handleMarkResolved} />
              
              <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
                <h3 className="text-lg font-semibold text-gray-900 mb-4">Aktivitas Sistem</h3>
                <div className="space-y-4">
                  <div className="flex items-center space-x-3">
                    <div className="w-2 h-2 bg-green-500 rounded-full"></div>
                    <span className="text-sm text-gray-600">Data inflasi berhasil diperbarui</span>
                    <span className="text-xs text-gray-400">10 menit lalu</span>
                  </div>
                  <div className="flex items-center space-x-3">
                    <div className="w-2 h-2 bg-blue-500 rounded-full"></div>
                    <span className="text-sm text-gray-600">Pengguna baru terdaftar</span>
                    <span className="text-xs text-gray-400">1 jam lalu</span>
                  </div>
                  <div className="flex items-center space-x-3">
                    <div className="w-2 h-2 bg-yellow-500 rounded-full"></div>
                    <span className="text-sm text-gray-600">Backup data dijadwalkan</span>
                    <span className="text-xs text-gray-400">2 jam lalu</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}

        {activeTab === 'indicators' && (
          <DataTable
            title="Daftar Indikator"
            data={indicators}
            columns={indicatorColumns}
            onEdit={handleEdit}
            onDelete={handleDelete}
            onAdd={handleAdd}
          />
        )}

        {activeTab === 'users' && (
          <DataTable
            title="Daftar Pengguna"
            data={users}
            columns={userColumns}
            onEdit={handleEdit}
            onDelete={handleDelete}
            onAdd={handleAdd}
          />
        )}

        {activeTab === 'feedback' && (
          <FeedbackList feedbacks={feedbacks} onMarkResolved={handleMarkResolved} />
        )}

        {activeTab === 'settings' && (
          <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
            <h3 className="text-lg font-semibold text-gray-900 mb-6">Pengaturan Sistem</h3>
            <div className="space-y-6">
              <div>
                <h4 className="font-medium text-gray-900 mb-4">Konfigurasi Umum</h4>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <label className="form-label">Nama Sistem</label>
                    <input type="text" className="form-input" value="Dashboard Data Strategis BPS" />
                  </div>
                  <div>
                    <label className="form-label">Zona Waktu</label>
                    <select className="form-input">
                      <option>Asia/Jakarta (WIB)</option>
                    </select>
                  </div>
                </div>
              </div>
              
              <div>
                <h4 className="font-medium text-gray-900 mb-4">Keamanan</h4>
                <div className="space-y-4">
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="font-medium text-gray-700">Autentikasi Dua Faktor</p>
                      <p className="text-sm text-gray-500">Aktifkan 2FA untuk semua pengguna admin</p>
                    </div>
                    <input type="checkbox" className="toggle" />
                  </div>
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="font-medium text-gray-700">Audit Log</p>
                      <p className="text-sm text-gray-500">Catat semua aktivitas sistem</p>
                    </div>
                    <input type="checkbox" className="toggle" defaultChecked />
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Admin;