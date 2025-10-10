import React from 'react';
import { 
  FiTrendingUp, 
  FiTrendingDown, 
  FiUsers, 
  FiDollarSign,
  FiHome,
  FiBook,
  FiBarChart3
} from 'react-icons/fi';

const StatCard = ({ title, value, unit, change, changeType, icon: Icon, color }) => {
  return (
    <div className={`card-stat relative overflow-hidden`}>
      <div className="flex items-start justify-between">
        <div className="flex-1">
          <p className="text-sm font-medium text-gray-600 mb-1">{title}</p>
          <div className="flex items-baseline space-x-2">
            <p className="text-2xl font-bold text-gray-900">{value}</p>
            <span className="text-sm text-gray-500">{unit}</span>
          </div>
          {change && (
            <div className={`flex items-center space-x-1 mt-2 ${
              changeType === 'increase' ? 'text-green-600' : 'text-red-600'
            }`}>
              {changeType === 'increase' ? 
                <FiTrendingUp className="w-4 h-4" /> : 
                <FiTrendingDown className="w-4 h-4" />
              }
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

const QuickAccessCard = ({ title, description, icon: Icon, color, onClick }) => {
  return (
    <div 
      className="card cursor-pointer hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1"
      onClick={onClick}
    >
      <div className="flex items-center space-x-4">
        <div className={`p-3 rounded-full ${color}`}>
          <Icon className="w-6 h-6 text-white" />
        </div>
        <div className="flex-1">
          <h3 className="font-semibold text-gray-900 mb-1">{title}</h3>
          <p className="text-sm text-gray-600">{description}</p>
        </div>
      </div>
    </div>
  );
};

const NewsCard = ({ title, date, category, summary }) => {
  return (
    <div className="card hover:shadow-xl transition-shadow duration-300">
      <div className="mb-3">
        <span className="inline-block px-3 py-1 text-xs font-medium bg-bps-primary text-white rounded-full mb-2">
          {category}
        </span>
        <p className="text-xs text-gray-500">{date}</p>
      </div>
      <h3 className="font-semibold text-gray-900 mb-2 line-clamp-2">{title}</h3>
      <p className="text-sm text-gray-600 line-clamp-3">{summary}</p>
    </div>
  );
};

const Home = () => {
  const strategicIndicators = [
    {
      title: "Tingkat Inflasi",
      value: "2.18",
      unit: "%",
      change: "+0.15% dari bulan lalu",
      changeType: "increase",
      icon: FiTrendingUp,
      color: "bg-red-500"
    },
    {
      title: "Tingkat Kemiskinan",
      value: "9.54",
      unit: "%",
      change: "-0.23% dari tahun lalu",
      changeType: "decrease",
      icon: FiUsers,
      color: "bg-orange-500"
    },
    {
      title: "Indeks Pembangunan Manusia",
      value: "73.61",
      unit: "",
      change: "+0.15 dari tahun lalu",
      changeType: "increase",
      icon: FiBook,
      color: "bg-green-500"
    },
    {
      title: "Pertumbuhan Ekonomi",
      value: "5.31",
      unit: "%",
      change: "+0.08% dari kuartal lalu",
      changeType: "increase",
      icon: FiBarChart3,
      color: "bg-blue-500"
    }
  ];

  const quickAccess = [
    {
      title: "Visualisasi Data",
      description: "Jelajahi data dalam bentuk grafik dan peta interaktif",
      icon: FiBarChart3,
      color: "bg-bps-primary"
    },
    {
      title: "Metadata",
      description: "Pelajari metodologi dan definisi indikator",
      icon: FiBook,
      color: "bg-bps-accent"
    },
    {
      title: "Berikan Feedback",
      description: "Sampaikan saran dan laporan terkait data",
      icon: FiUsers,
      color: "bg-green-500"
    }
  ];

  const recentNews = [
    {
      title: "Inflasi Indonesia Bulan Oktober 2024 Sebesar 0,13 Persen",
      date: "5 November 2024",
      category: "Inflasi",
      summary: "Badan Pusat Statistik (BPS) mencatat inflasi Indonesia pada Oktober 2024 sebesar 0,13 persen (mtm) dengan inflasi tahunan 2,18 persen (yoy)."
    },
    {
      title: "Nilai Tukar Petani Nasional Oktober 2024 Naik 0,06 Persen",
      date: "4 November 2024",
      category: "Pertanian",
      summary: "Nilai Tukar Petani (NTP) Nasional pada Oktober 2024 sebesar 103,21 atau naik 0,06 persen dibandingkan September 2024."
    },
    {
      title: "Indeks Harga Konsumen 34 Kota Oktober 2024",
      date: "3 November 2024",
      category: "Ekonomi",
      summary: "IHK gabungan 34 kota pada Oktober 2024 naik sebesar 0,13 persen dibandingkan September 2024."
    }
  ];

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Hero Section */}
      <section className="bg-gradient-to-r from-bps-primary to-bps-secondary text-white py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center">
            <h1 className="text-4xl md:text-5xl font-bold mb-4">
              Dashboard Data Strategis BPS
            </h1>
            <p className="text-xl md:text-2xl text-blue-100 mb-8">
              Portal Resmi Data Pembangunan Nasional dan Daerah
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <button className="bg-white text-bps-primary px-8 py-3 rounded-lg font-semibold hover:bg-gray-100 transition-colors duration-200">
                Jelajahi Data
              </button>
              <button className="border-2 border-white text-white px-8 py-3 rounded-lg font-semibold hover:bg-white hover:text-bps-primary transition-colors duration-200">
                Pelajari Lebih Lanjut
              </button>
            </div>
          </div>
        </div>
      </section>

      {/* Strategic Indicators */}
      <section className="py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-gray-900 mb-4">Indikator Strategis</h2>
            <p className="text-xl text-gray-600">Data terkini pembangunan nasional Indonesia</p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            {strategicIndicators.map((indicator, index) => (
              <StatCard key={index} {...indicator} />
            ))}
          </div>
        </div>
      </section>

      {/* Quick Access */}
      <section className="py-16 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-gray-900 mb-4">Akses Cepat</h2>
            <p className="text-xl text-gray-600">Temukan informasi yang Anda butuhkan dengan mudah</p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {quickAccess.map((item, index) => (
              <QuickAccessCard 
                key={index} 
                {...item}
                onClick={() => console.log(`Navigate to ${item.title}`)}
              />
            ))}
          </div>
        </div>
      </section>

      {/* Recent News & Updates */}
      <section className="py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center mb-12">
            <div>
              <h2 className="text-3xl font-bold text-gray-900 mb-4">Berita & Update Terbaru</h2>
              <p className="text-xl text-gray-600">Informasi terkini dari Badan Pusat Statistik</p>
            </div>
            <button className="btn-primary">
              Lihat Semua Berita
            </button>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {recentNews.map((news, index) => (
              <NewsCard key={index} {...news} />
            ))}
          </div>
        </div>
      </section>

      {/* Stats Overview */}
      <section className="py-16 bg-bps-primary">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-8 text-center text-white">
            <div>
              <div className="text-4xl font-bold mb-2">34</div>
              <div className="text-blue-100">Provinsi</div>
            </div>
            <div>
              <div className="text-4xl font-bold mb-2">514</div>
              <div className="text-blue-100">Kabupaten/Kota</div>
            </div>
            <div>
              <div className="text-4xl font-bold mb-2">1,000+</div>
              <div className="text-blue-100">Indikator Data</div>
            </div>
            <div>
              <div className="text-4xl font-bold mb-2">24/7</div>
              <div className="text-blue-100">Akses Data</div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Home;