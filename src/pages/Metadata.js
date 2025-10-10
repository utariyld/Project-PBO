import React, { useState } from 'react';
import { 
  FiSearch, 
  FiBook, 
  FiDownload,
  FiExternalLink,
  FiFilter,
  FiCalendar,
  FiUsers,
  FiDatabase
} from 'react-icons/fi';

const MetadataCard = ({ title, description, methodology, source, lastUpdate, category, downloadUrl }) => {
  return (
    <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100 hover:shadow-xl transition-shadow duration-300">
      <div className="flex items-start justify-between mb-4">
        <div className="flex-1">
          <div className="flex items-center space-x-2 mb-2">
            <span className={`px-3 py-1 text-xs font-medium rounded-full ${
              category === 'ekonomi' ? 'bg-blue-100 text-blue-800' :
              category === 'sosial' ? 'bg-green-100 text-green-800' :
              category === 'lingkungan' ? 'bg-yellow-100 text-yellow-800' :
              'bg-purple-100 text-purple-800'
            }`}>
              {category}
            </span>
          </div>
          <h3 className="text-lg font-semibold text-gray-900 mb-2">{title}</h3>
          <p className="text-sm text-gray-600 mb-4">{description}</p>
        </div>
      </div>
      
      <div className="space-y-3 mb-4">
        <div className="flex items-start space-x-2">
          <FiBook className="w-4 h-4 text-bps-primary mt-0.5 flex-shrink-0" />
          <div>
            <p className="text-xs font-medium text-gray-700">Metodologi:</p>
            <p className="text-sm text-gray-600">{methodology}</p>
          </div>
        </div>
        
        <div className="flex items-start space-x-2">
          <FiDatabase className="w-4 h-4 text-bps-primary mt-0.5 flex-shrink-0" />
          <div>
            <p className="text-xs font-medium text-gray-700">Sumber Data:</p>
            <p className="text-sm text-gray-600">{source}</p>
          </div>
        </div>
        
        <div className="flex items-center space-x-2">
          <FiCalendar className="w-4 h-4 text-bps-primary" />
          <div>
            <p className="text-xs font-medium text-gray-700">Terakhir Update:</p>
            <p className="text-sm text-gray-600">{lastUpdate}</p>
          </div>
        </div>
      </div>
      
      <div className="flex justify-between items-center pt-4 border-t border-gray-100">
        <button className="flex items-center space-x-2 text-sm text-bps-primary hover:text-blue-700 transition-colors duration-200">
          <FiExternalLink className="w-4 h-4" />
          <span>Lihat Detail</span>
        </button>
        
        <button className="flex items-center space-x-2 text-sm bg-gray-100 text-gray-700 px-3 py-1 rounded-md hover:bg-gray-200 transition-colors duration-200">
          <FiDownload className="w-4 h-4" />
          <span>Download</span>
        </button>
      </div>
    </div>
  );
};

const DefinitionCard = ({ term, definition, category, relatedTerms }) => {
  return (
    <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
      <div className="mb-4">
        <div className="flex items-center justify-between mb-2">
          <h3 className="text-lg font-semibold text-gray-900">{term}</h3>
          <span className={`px-2 py-1 text-xs font-medium rounded-full ${
            category === 'ekonomi' ? 'bg-blue-100 text-blue-800' :
            category === 'sosial' ? 'bg-green-100 text-green-800' :
            category === 'lingkungan' ? 'bg-yellow-100 text-yellow-800' :
            'bg-purple-100 text-purple-800'
          }`}>
            {category}
          </span>
        </div>
        <p className="text-gray-600 leading-relaxed">{definition}</p>
      </div>
      
      {relatedTerms && relatedTerms.length > 0 && (
        <div className="border-t border-gray-100 pt-4">
          <p className="text-sm font-medium text-gray-700 mb-2">Istilah Terkait:</p>
          <div className="flex flex-wrap gap-2">
            {relatedTerms.map((term, index) => (
              <span 
                key={index}
                className="px-2 py-1 text-xs bg-gray-100 text-gray-700 rounded-md cursor-pointer hover:bg-bps-primary hover:text-white transition-colors duration-200"
              >
                {term}
              </span>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

const Metadata = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [activeTab, setActiveTab] = useState('metodologi');

  const categories = [
    { value: 'all', label: 'Semua Kategori' },
    { value: 'ekonomi', label: 'Ekonomi' },
    { value: 'sosial', label: 'Sosial' },
    { value: 'lingkungan', label: 'Lingkungan' },
    { value: 'pemerintahan', label: 'Pemerintahan' }
  ];

  const methodologyData = [
    {
      title: "Indeks Harga Konsumen (IHK)",
      description: "Indikator untuk mengukur rata-rata perubahan harga dari suatu paket barang dan jasa yang dikonsumsi oleh rumah tangga dalam kurun waktu tertentu.",
      methodology: "Survei Harga Konsumen dengan pendekatan modified Laspeyres menggunakan tahun dasar 2018=100. Data dikumpulkan dari pasar tradisional, modern, dan online di 90 kota.",
      source: "Survei Harga Konsumen - BPS",
      lastUpdate: "November 2024",
      category: "ekonomi",
      downloadUrl: "#"
    },
    {
      title: "Tingkat Pengangguran Terbuka (TPT)",
      description: "Persentase jumlah pengangguran terhadap jumlah angkatan kerja.",
      methodology: "Survei Angkatan Kerja Nasional (Sakernas) dengan pendekatan labor force concept sesuai standar ILO. Survei dilakukan dua kali dalam setahun.",
      source: "Survei Angkatan Kerja Nasional - BPS",
      lastUpdate: "Agustus 2024",
      category: "sosial",
      downloadUrl: "#"
    },
    {
      title: "Produk Domestik Bruto (PDB)",
      description: "Nilai keseluruhan semua barang dan jasa yang diproduksi dalam suatu negara dalam periode waktu tertentu.",
      methodology: "Pendekatan produksi, pengeluaran, dan pendapatan dengan tahun dasar 2010. Data dikompilasi dari berbagai sumber termasuk survei ekonomi dan data administrasi.",
      source: "Survei Ekonomi Nasional - BPS",
      lastUpdate: "November 2024",
      category: "ekonomi",
      downloadUrl: "#"
    },
    {
      title: "Indeks Pembangunan Manusia (IPM)",
      description: "Indeks komposit yang mengukur pencapaian rata-rata suatu negara dalam tiga dimensi dasar pembangunan manusia.",
      methodology: "Menggabungkan indikator harapan hidup, pendidikan (rata-rata lama sekolah dan harapan lama sekolah), dan standar hidup (pengeluaran per kapita).",
      source: "Susenas, Survei Pendidikan, Data Vital - BPS",
      lastUpdate: "September 2024",
      category: "sosial",
      downloadUrl: "#"
    }
  ];

  const definitions = [
    {
      term: "Inflasi",
      definition: "Kenaikan harga barang dan jasa secara umum dan terus menerus dalam jangka waktu tertentu. Inflasi dapat disebabkan oleh meningkatnya permintaan atau berkurangnya penawaran.",
      category: "ekonomi",
      relatedTerms: ["Deflasi", "IHK", "Daya Beli"]
    },
    {
      term: "Kemiskinan",
      definition: "Kondisi dimana seseorang atau sekelompok orang tidak mampu memenuhi kebutuhan dasar hidupnya untuk mempertahankan dan mengembangkan kehidupan yang bermartabat.",
      category: "sosial",
      relatedTerms: ["Garis Kemiskinan", "Indeks Kedalaman Kemiskinan", "Indeks Keparahan Kemiskinan"]
    },
    {
      term: "Angkatan Kerja",
      definition: "Penduduk usia kerja (15 tahun ke atas) yang bekerja, atau punya pekerjaan namun sementara tidak bekerja dan pengangguran.",
      category: "sosial",
      relatedTerms: ["TPAK", "TPT", "Pekerja"]
    },
    {
      term: "PDB Nominal",
      definition: "Nilai keseluruhan barang dan jasa yang diproduksi suatu negara dalam periode tertentu berdasarkan harga yang berlaku pada periode tersebut (harga nominal).",
      category: "ekonomi",
      relatedTerms: ["PDB Riil", "Pertumbuhan Ekonomi", "Deflator PDB"]
    }
  ];

  const filteredMethodology = methodologyData.filter(item => {
    const matchesSearch = item.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         item.description.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCategory = selectedCategory === 'all' || item.category === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  const filteredDefinitions = definitions.filter(item => {
    const matchesSearch = item.term.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         item.definition.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCategory = selectedCategory === 'all' || item.category === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-4">Metadata</h1>
          <p className="text-xl text-gray-600">
            Pelajari metodologi, sumber data, dan definisi indikator statistik BPS
          </p>
        </div>

        {/* Search and Filter */}
        <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100 mb-8">
          <div className="flex flex-col lg:flex-row gap-4">
            <div className="flex-1">
              <div className="relative">
                <FiSearch className="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5 text-gray-400" />
                <input
                  type="text"
                  placeholder="Cari metodologi atau definisi..."
                  className="form-input pl-10"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
              </div>
            </div>
            
            <div className="lg:w-64">
              <select
                className="form-input"
                value={selectedCategory}
                onChange={(e) => setSelectedCategory(e.target.value)}
              >
                {categories.map(category => (
                  <option key={category.value} value={category.value}>
                    {category.label}
                  </option>
                ))}
              </select>
            </div>
          </div>
        </div>

        {/* Tabs */}
        <div className="mb-8">
          <div className="border-b border-gray-200">
            <nav className="-mb-px flex space-x-8">
              <button
                onClick={() => setActiveTab('metodologi')}
                className={`py-2 px-1 border-b-2 font-medium text-sm ${
                  activeTab === 'metodologi'
                    ? 'border-bps-primary text-bps-primary'
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                }`}
              >
                <div className="flex items-center space-x-2">
                  <FiBook className="w-4 h-4" />
                  <span>Metodologi & Sumber Data</span>
                </div>
              </button>
              
              <button
                onClick={() => setActiveTab('definisi')}
                className={`py-2 px-1 border-b-2 font-medium text-sm ${
                  activeTab === 'definisi'
                    ? 'border-bps-primary text-bps-primary'
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                }`}
              >
                <div className="flex items-center space-x-2">
                  <FiUsers className="w-4 h-4" />
                  <span>Definisi Indikator</span>
                </div>
              </button>
            </nav>
          </div>
        </div>

        {/* Content */}
        {activeTab === 'metodologi' && (
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
            {filteredMethodology.map((item, index) => (
              <MetadataCard key={index} {...item} />
            ))}
          </div>
        )}

        {activeTab === 'definisi' && (
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
            {filteredDefinitions.map((item, index) => (
              <DefinitionCard key={index} {...item} />
            ))}
          </div>
        )}

        {/* Summary Statistics */}
        <div className="mt-12 grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
            <div className="flex items-center space-x-3 mb-4">
              <div className="p-2 bg-bps-primary rounded-lg">
                <FiBook className="w-5 h-5 text-white" />
              </div>
              <h3 className="font-semibold text-gray-900">Total Metodologi</h3>
            </div>
            <p className="text-3xl font-bold text-bps-primary mb-2">{methodologyData.length}</p>
            <p className="text-sm text-gray-600">Metodologi tersedia</p>
          </div>
          
          <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
            <div className="flex items-center space-x-3 mb-4">
              <div className="p-2 bg-green-500 rounded-lg">
                <FiUsers className="w-5 h-5 text-white" />
              </div>
              <h3 className="font-semibold text-gray-900">Definisi Tersedia</h3>
            </div>
            <p className="text-3xl font-bold text-green-600 mb-2">{definitions.length}</p>
            <p className="text-sm text-gray-600">Istilah dan definisi</p>
          </div>
          
          <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
            <div className="flex items-center space-x-3 mb-4">
              <div className="p-2 bg-purple-500 rounded-lg">
                <FiDatabase className="w-5 h-5 text-white" />
              </div>
              <h3 className="font-semibold text-gray-900">Kategori Data</h3>
            </div>
            <p className="text-3xl font-bold text-purple-600 mb-2">{categories.length - 1}</p>
            <p className="text-sm text-gray-600">Kategori utama</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Metadata;