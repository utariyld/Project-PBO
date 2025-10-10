import React, { useState, useEffect } from 'react';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
} from 'chart.js';
import { Line, Bar, Doughnut } from 'react-chartjs-2';
import { 
  FiFilter, 
  FiDownload, 
  FiRefreshCw,
  FiTrendingUp,
  FiBarChart3,
  FiPieChart,
  FiMap
} from 'react-icons/fi';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement
);

const FilterPanel = ({ filters, onFilterChange }) => {
  return (
    <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100 mb-8">
      <div className="flex items-center space-x-2 mb-4">
        <FiFilter className="w-5 h-5 text-bps-primary" />
        <h3 className="text-lg font-semibold text-gray-900">Filter Data</h3>
      </div>
      
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div>
          <label className="form-label">Kategori</label>
          <select 
            className="form-input"
            value={filters.category}
            onChange={(e) => onFilterChange('category', e.target.value)}
          >
            <option value="all">Semua Kategori</option>
            <option value="ekonomi">Ekonomi</option>
            <option value="sosial">Sosial</option>
            <option value="lingkungan">Lingkungan</option>
            <option value="pemerintahan">Pemerintahan</option>
          </select>
        </div>
        
        <div>
          <label className="form-label">Tahun</label>
          <select 
            className="form-input"
            value={filters.year}
            onChange={(e) => onFilterChange('year', e.target.value)}
          >
            <option value="2024">2024</option>
            <option value="2023">2023</option>
            <option value="2022">2022</option>
            <option value="2021">2021</option>
            <option value="2020">2020</option>
          </select>
        </div>
        
        <div>
          <label className="form-label">Wilayah</label>
          <select 
            className="form-input"
            value={filters.region}
            onChange={(e) => onFilterChange('region', e.target.value)}
          >
            <option value="nasional">Nasional</option>
            <option value="jawa-barat">Jawa Barat</option>
            <option value="jawa-timur">Jawa Timur</option>
            <option value="jawa-tengah">Jawa Tengah</option>
            <option value="sumatra-utara">Sumatra Utara</option>
            <option value="sulawesi-selatan">Sulawesi Selatan</option>
          </select>
        </div>
        
        <div>
          <label className="form-label">Periode</label>
          <select 
            className="form-input"
            value={filters.period}
            onChange={(e) => onFilterChange('period', e.target.value)}
          >
            <option value="bulanan">Bulanan</option>
            <option value="kuartalan">Kuartalan</option>
            <option value="tahunan">Tahunan</option>
          </select>
        </div>
      </div>
    </div>
  );
};

const ChartCard = ({ title, type, data, options, icon: Icon }) => {
  const renderChart = () => {
    switch (type) {
      case 'line':
        return <Line data={data} options={options} />;
      case 'bar':
        return <Bar data={data} options={options} />;
      case 'doughnut':
        return <Doughnut data={data} options={options} />;
      default:
        return <Line data={data} options={options} />;
    }
  };

  return (
    <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
      <div className="flex items-center justify-between mb-6">
        <div className="flex items-center space-x-3">
          <div className="p-2 bg-bps-primary rounded-lg">
            <Icon className="w-5 h-5 text-white" />
          </div>
          <h3 className="text-lg font-semibold text-gray-900">{title}</h3>
        </div>
        <div className="flex space-x-2">
          <button className="p-2 text-gray-500 hover:text-bps-primary transition-colors duration-200">
            <FiDownload className="w-4 h-4" />
          </button>
          <button className="p-2 text-gray-500 hover:text-bps-primary transition-colors duration-200">
            <FiRefreshCw className="w-4 h-4" />
          </button>
        </div>
      </div>
      <div className="chart-container">
        {renderChart()}
      </div>
    </div>
  );
};

const MapVisualization = () => {
  return (
    <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
      <div className="flex items-center justify-between mb-6">
        <div className="flex items-center space-x-3">
          <div className="p-2 bg-bps-primary rounded-lg">
            <FiMap className="w-5 h-5 text-white" />
          </div>
          <h3 className="text-lg font-semibold text-gray-900">Peta Interaktif Indonesia</h3>
        </div>
        <div className="flex space-x-2">
          <button className="btn-secondary text-sm">Legenda</button>
          <button className="btn-primary text-sm">Fullscreen</button>
        </div>
      </div>
      
      <div className="h-96 bg-gradient-to-br from-blue-50 to-green-50 rounded-lg flex items-center justify-center">
        <div className="text-center">
          <FiMap className="w-16 h-16 text-bps-primary mx-auto mb-4" />
          <h4 className="text-xl font-semibold text-gray-700 mb-2">Peta Interaktif</h4>
          <p className="text-gray-500 mb-4">
            Visualisasi data berdasarkan wilayah Indonesia
          </p>
          <div className="grid grid-cols-2 gap-4 max-w-md mx-auto">
            <div className="bg-white p-4 rounded-lg shadow">
              <div className="text-2xl font-bold text-green-600">34</div>
              <div className="text-sm text-gray-600">Provinsi</div>
            </div>
            <div className="bg-white p-4 rounded-lg shadow">
              <div className="text-2xl font-bold text-blue-600">514</div>
              <div className="text-sm text-gray-600">Kab/Kota</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const VisualisasiData = () => {
  const [filters, setFilters] = useState({
    category: 'all',
    year: '2024',
    region: 'nasional',
    period: 'bulanan'
  });

  const handleFilterChange = (key, value) => {
    setFilters(prev => ({
      ...prev,
      [key]: value
    }));
  };

  // Sample data untuk grafik
  const inflationData = {
    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'Mei', 'Jun', 'Jul', 'Agt', 'Sep', 'Okt', 'Nov', 'Des'],
    datasets: [
      {
        label: 'Inflasi (%)',
        data: [2.68, 2.75, 2.53, 3.47, 3.55, 4.35, 4.94, 4.69, 5.95, 5.71, 4.81, 2.18],
        borderColor: '#1e40af',
        backgroundColor: 'rgba(30, 64, 175, 0.1)',
        tension: 0.4,
        fill: true
      }
    ]
  };

  const povertyData = {
    labels: ['2020', '2021', '2022', '2023', '2024'],
    datasets: [
      {
        label: 'Tingkat Kemiskinan (%)',
        data: [10.19, 9.71, 9.54, 9.36, 9.54],
        backgroundColor: ['#ef4444', '#f97316', '#f59e0b', '#eab308', '#84cc16'],
        borderWidth: 2
      }
    ]
  };

  const economicGrowthData = {
    labels: ['Q1 2023', 'Q2 2023', 'Q3 2023', 'Q4 2023', 'Q1 2024', 'Q2 2024', 'Q3 2024'],
    datasets: [
      {
        label: 'Pertumbuhan Ekonomi (%)',
        data: [5.03, 5.17, 4.94, 5.04, 5.11, 5.05, 4.95],
        backgroundColor: 'rgba(16, 185, 129, 0.8)',
        borderColor: '#10b981',
        borderWidth: 2
      }
    ]
  };

  const sectorData = {
    labels: ['Pertanian', 'Industri', 'Konstruksi', 'Perdagangan', 'Transportasi', 'Keuangan', 'Jasa'],
    datasets: [
      {
        data: [12.4, 19.7, 5.1, 13.6, 3.8, 4.7, 40.7],
        backgroundColor: [
          '#10b981',
          '#3b82f6', 
          '#f59e0b',
          '#ef4444',
          '#8b5cf6',
          '#06b6d4',
          '#f97316'
        ]
      }
    ]
  };

  const chartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'top',
      },
    },
    scales: {
      y: {
        beginAtZero: true,
        grid: {
          color: 'rgba(156, 163, 175, 0.2)'
        }
      },
      x: {
        grid: {
          color: 'rgba(156, 163, 175, 0.2)'
        }
      }
    }
  };

  const doughnutOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'right',
      },
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-4">Visualisasi Data</h1>
          <p className="text-xl text-gray-600">
            Jelajahi data statistik Indonesia dalam bentuk grafik dan peta interaktif
          </p>
        </div>

        {/* Filter Panel */}
        <FilterPanel filters={filters} onFilterChange={handleFilterChange} />

        {/* Charts Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-8">
          <ChartCard
            title="Tren Inflasi Bulanan"
            type="line"
            data={inflationData}
            options={chartOptions}
            icon={FiTrendingUp}
          />
          
          <ChartCard
            title="Tingkat Kemiskinan per Tahun"
            type="bar"
            data={povertyData}
            options={chartOptions}
            icon={FiBarChart3}
          />
          
          <ChartCard
            title="Pertumbuhan Ekonomi Kuartalan"
            type="bar"
            data={economicGrowthData}
            options={chartOptions}
            icon={FiBarChart3}
          />
          
          <ChartCard
            title="Kontribusi Sektor Ekonomi"
            type="doughnut"
            data={sectorData}
            options={doughnutOptions}
            icon={FiPieChart}
          />
        </div>

        {/* Map Visualization */}
        <MapVisualization />

        {/* Data Summary */}
        <div className="mt-8 grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
            <div className="flex items-center space-x-3 mb-4">
              <div className="p-2 bg-green-500 rounded-lg">
                <FiTrendingUp className="w-5 h-5 text-white" />
              </div>
              <h3 className="font-semibold text-gray-900">Data Terbaru</h3>
            </div>
            <p className="text-sm text-gray-600 mb-2">Terakhir diperbarui:</p>
            <p className="font-semibold text-bps-primary">November 2024</p>
          </div>
          
          <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
            <div className="flex items-center space-x-3 mb-4">
              <div className="p-2 bg-blue-500 rounded-lg">
                <FiBarChart3 className="w-5 h-5 text-white" />
              </div>
              <h3 className="font-semibold text-gray-900">Total Indikator</h3>
            </div>
            <p className="text-sm text-gray-600 mb-2">Jumlah indikator tersedia:</p>
            <p className="font-semibold text-bps-primary">1,250+</p>
          </div>
          
          <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
            <div className="flex items-center space-x-3 mb-4">
              <div className="p-2 bg-purple-500 rounded-lg">
                <FiMap className="w-5 h-5 text-white" />
              </div>
              <h3 className="font-semibold text-gray-900">Cakupan Wilayah</h3>
            </div>
            <p className="text-sm text-gray-600 mb-2">Area geografis:</p>
            <p className="font-semibold text-bps-primary">34 Provinsi</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default VisualisasiData;