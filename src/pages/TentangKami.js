import React, { useState } from 'react';
import { 
  FiUsers, 
  FiMapPin, 
  FiPhone, 
  FiMail, 
  FiGlobe,
  FiTarget,
  FiEye,
  FiAward,
  FiCalendar,
  FiBuilding,
  FiChevronDown,
  FiChevronRight
} from 'react-icons/fi';

const StatCard = ({ icon: Icon, title, value, description }) => {
  return (
    <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100 text-center">
      <div className="w-12 h-12 bg-bps-primary rounded-full flex items-center justify-center mx-auto mb-4">
        <Icon className="w-6 h-6 text-white" />
      </div>
      <h3 className="text-2xl font-bold text-gray-900 mb-2">{value}</h3>
      <p className="font-medium text-gray-700 mb-1">{title}</p>
      <p className="text-sm text-gray-600">{description}</p>
    </div>
  );
};

const TimelineItem = ({ year, title, description, isLast }) => {
  return (
    <div className="flex">
      <div className="flex flex-col items-center mr-4">
        <div className="w-4 h-4 bg-bps-primary rounded-full"></div>
        {!isLast && <div className="w-0.5 h-16 bg-gray-300 mt-2"></div>}
      </div>
      <div className="pb-8">
        <div className="flex items-center space-x-2 mb-1">
          <span className="text-sm font-semibold text-bps-primary">{year}</span>
        </div>
        <h3 className="font-semibold text-gray-900 mb-2">{title}</h3>
        <p className="text-gray-600">{description}</p>
      </div>
    </div>
  );
};

const OrganizationCard = ({ name, leader, description, isExpanded, onToggle }) => {
  return (
    <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
      <div 
        className="flex items-center justify-between cursor-pointer"
        onClick={onToggle}
      >
        <div className="flex items-center space-x-3">
          <FiBuilding className="w-5 h-5 text-bps-primary" />
          <div>
            <h3 className="font-semibold text-gray-900">{name}</h3>
            <p className="text-sm text-gray-600">Kepala: {leader}</p>
          </div>
        </div>
        {isExpanded ? 
          <FiChevronDown className="w-5 h-5 text-gray-400" /> : 
          <FiChevronRight className="w-5 h-5 text-gray-400" />
        }
      </div>
      
      {isExpanded && (
        <div className="mt-4 pt-4 border-t border-gray-100">
          <p className="text-gray-600">{description}</p>
        </div>
      )}
    </div>
  );
};

const TentangKami = () => {
  const [expandedOrg, setExpandedOrg] = useState(null);

  const statistics = [
    {
      icon: FiCalendar,
      title: "Tahun Berdiri",
      value: "1960",
      description: "Lebih dari 60 tahun mengabdi"
    },
    {
      icon: FiUsers,
      title: "Pegawai",
      value: "16,000+",
      description: "Statistisi profesional"
    },
    {
      icon: FiMapPin,
      title: "Kantor Cabang",
      value: "540+",
      description: "Di seluruh Indonesia"
    },
    {
      icon: FiAward,
      title: "Survei Aktif",
      value: "100+",
      description: "Survei rutin tahunan"
    }
  ];

  const timeline = [
    {
      year: "1960",
      title: "Pembentukan BPS",
      description: "Badan Pusat Statistik dibentuk berdasarkan UU No. 7 Tahun 1960"
    },
    {
      year: "1997",
      title: "Lembaga Pemerintah Non Departemen",
      description: "BPS ditetapkan sebagai Lembaga Pemerintah Non Departemen (LPND)"
    },
    {
      year: "2009",
      title: "UU Statistik Terbaru",
      description: "Pengesahan UU No. 16 Tahun 2009 tentang Statistik"
    },
    {
      year: "2020",
      title: "Transformasi Digital",
      description: "Implementasi sistem digital dan big data analytics"
    }
  ];

  const organizationStructure = [
    {
      name: "Kepala BPS",
      leader: "Margo Yuwono",
      description: "Memimpin seluruh kegiatan statistik nasional dan bertanggung jawab atas kebijakan strategis BPS."
    },
    {
      name: "Deputi Bidang Statistik Sosial",
      leader: "Setianto",
      description: "Menangani statistik kependudukan, ketenagakerjaan, pendidikan, kesehatan, dan kesejahteraan sosial."
    },
    {
      name: "Deputi Bidang Statistik Produksi",
      leader: "Sri Bekti Istiyanto",
      description: "Bertanggung jawab atas statistik pertanian, industri, konstruksi, dan perdagangan."
    },
    {
      name: "Deputi Bidang Statistik Distribusi",
      leader: "Yunita Rusanti",
      description: "Mengelola statistik transportasi, komunikasi, pariwisata, dan keuangan."
    },
    {
      name: "Deputi Bidang Neraca dan Analisis Statistik",
      leader: "Nata Irawan",
      description: "Menyusun sistem neraca nasional dan melakukan analisis statistik makro."
    },
    {
      name: "Deputi Bidang Metodologi dan Informasi Statistik",
      leader: "Abdul Hadi Ilman",
      description: "Mengembangkan metodologi statistik dan sistem informasi statistik."
    }
  ];

  const handleOrgToggle = (index) => {
    setExpandedOrg(expandedOrg === index ? null : index);
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Hero Section */}
      <section className="bg-gradient-to-r from-bps-primary to-bps-secondary text-white py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center">
            <h1 className="text-4xl md:text-5xl font-bold mb-4">Tentang Badan Pusat Statistik</h1>
            <p className="text-xl text-blue-100 mb-8 max-w-3xl mx-auto">
              Lembaga pemerintah yang bertanggung jawab menyelenggarakan kegiatan statistik di Indonesia
            </p>
          </div>
        </div>
      </section>

      {/* Statistics */}
      <section className="py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            {statistics.map((stat, index) => (
              <StatCard key={index} {...stat} />
            ))}
          </div>
        </div>
      </section>

      {/* Profile Section */}
      <section className="py-16 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
            <div>
              <h2 className="text-3xl font-bold text-gray-900 mb-6">Profil BPS</h2>
              <div className="space-y-6">
                <p className="text-gray-600 leading-relaxed">
                  Badan Pusat Statistik (BPS) adalah Lembaga Pemerintah Non Departemen yang bertanggung jawab 
                  langsung kepada Presiden. BPS bertugas menyelenggarakan kegiatan statistik di Indonesia 
                  sesuai dengan Undang-Undang No. 16 Tahun 2009 tentang Statistik.
                </p>
                
                <div className="bg-gray-50 p-6 rounded-lg">
                  <div className="flex items-center space-x-3 mb-4">
                    <FiTarget className="w-6 h-6 text-bps-primary" />
                    <h3 className="text-lg font-semibold text-gray-900">Visi</h3>
                  </div>
                  <p className="text-gray-700 italic">
                    "Pelopor data statistik terpercaya untuk semua"
                  </p>
                </div>
                
                <div className="bg-gray-50 p-6 rounded-lg">
                  <div className="flex items-center space-x-3 mb-4">
                    <FiEye className="w-6 h-6 text-bps-primary" />
                    <h3 className="text-lg font-semibold text-gray-900">Misi</h3>
                  </div>
                  <ul className="text-gray-700 space-y-2">
                    <li>• Menyediakan statistik berkualitas yang berstandar nasional dan internasional</li>
                    <li>• Membina K/L/D/I dalam penyelenggaraan kegiatan statistik</li>
                    <li>• Mengembangkan dan mempromosikan standar dan interoperabilitas statistik</li>
                    <li>• Membangun sistem perstatistikan nasional yang andal, efektif dan efisien</li>
                  </ul>
                </div>
              </div>
            </div>
            
            <div>
              <div className="bg-bps-primary p-8 rounded-xl text-white">
                <h3 className="text-2xl font-bold mb-6">Nilai-Nilai BPS</h3>
                <div className="space-y-4">
                  <div className="flex items-start space-x-3">
                    <div className="w-2 h-2 bg-white rounded-full mt-2 flex-shrink-0"></div>
                    <div>
                      <h4 className="font-semibold mb-1">Profesional</h4>
                      <p className="text-blue-100 text-sm">Bekerja dengan keahlian tinggi dan dedikasi penuh</p>
                    </div>
                  </div>
                  <div className="flex items-start space-x-3">
                    <div className="w-2 h-2 bg-white rounded-full mt-2 flex-shrink-0"></div>
                    <div>
                      <h4 className="font-semibold mb-1">Integritas</h4>
                      <p className="text-blue-100 text-sm">Menjunjung tinggi kejujuran dan transparansi</p>
                    </div>
                  </div>
                  <div className="flex items-start space-x-3">
                    <div className="w-2 h-2 bg-white rounded-full mt-2 flex-shrink-0"></div>
                    <div>
                      <h4 className="font-semibold mb-1">Amanah</h4>
                      <p className="text-blue-100 text-sm">Bertanggung jawab atas kepercayaan yang diberikan</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Timeline */}
      <section className="py-16">
        <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-gray-900 mb-4">Sejarah Perkembangan</h2>
            <p className="text-xl text-gray-600">
              Perjalanan BPS dalam membangun sistem statistik nasional
            </p>
          </div>
          
          <div className="bg-white p-8 rounded-xl shadow-lg border border-gray-100">
            {timeline.map((item, index) => (
              <TimelineItem
                key={index}
                {...item}
                isLast={index === timeline.length - 1}
              />
            ))}
          </div>
        </div>
      </section>

      {/* Organization Structure */}
      <section className="py-16 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-gray-900 mb-4">Struktur Organisasi</h2>
            <p className="text-xl text-gray-600">
              Kepemimpinan dan struktur organisasi BPS
            </p>
          </div>
          
          <div className="space-y-4">
            {organizationStructure.map((org, index) => (
              <OrganizationCard
                key={index}
                {...org}
                isExpanded={expandedOrg === index}
                onToggle={() => handleOrgToggle(index)}
              />
            ))}
          </div>
        </div>
      </section>

      {/* Contact Information */}
      <section className="py-16 bg-bps-primary text-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold mb-4">Hubungi Kami</h2>
            <p className="text-xl text-blue-100">
              Informasi kontak resmi Badan Pusat Statistik
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
            <div className="text-center">
              <div className="w-12 h-12 bg-white rounded-full flex items-center justify-center mx-auto mb-4">
                <FiMapPin className="w-6 h-6 text-bps-primary" />
              </div>
              <h3 className="font-semibold mb-2">Alamat Kantor Pusat</h3>
              <p className="text-blue-100">
                Jl. Dr. Sutomo No. 6-8<br />
                Jakarta Pusat 10710
              </p>
            </div>
            
            <div className="text-center">
              <div className="w-12 h-12 bg-white rounded-full flex items-center justify-center mx-auto mb-4">
                <FiPhone className="w-6 h-6 text-bps-primary" />
              </div>
              <h3 className="font-semibold mb-2">Telepon</h3>
              <p className="text-blue-100">
                (021) 3841195<br />
                (021) 3842508
              </p>
            </div>
            
            <div className="text-center">
              <div className="w-12 h-12 bg-white rounded-full flex items-center justify-center mx-auto mb-4">
                <FiMail className="w-6 h-6 text-bps-primary" />
              </div>
              <h3 className="font-semibold mb-2">Email</h3>
              <p className="text-blue-100">
                bpshq@bps.go.id<br />
                info@bps.go.id
              </p>
            </div>
            
            <div className="text-center">
              <div className="w-12 h-12 bg-white rounded-full flex items-center justify-center mx-auto mb-4">
                <FiGlobe className="w-6 h-6 text-bps-primary" />
              </div>
              <h3 className="font-semibold mb-2">Website</h3>
              <p className="text-blue-100">
                www.bps.go.id<br />
                microdata.bps.go.id
              </p>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default TentangKami;