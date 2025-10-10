import React from 'react';
import { Link } from 'react-router-dom';
import { 
  FiFacebook, 
  FiTwitter, 
  FiInstagram, 
  FiYoutube,
  FiMail,
  FiPhone,
  FiMapPin
} from 'react-icons/fi';

const Footer = () => {
  const quickLinks = [
    { path: '/', label: 'Beranda' },
    { path: '/visualisasi', label: 'Visualisasi Data' },
    { path: '/metadata', label: 'Metadata' },
    { path: '/feedback', label: 'Feedback' },
    { path: '/tentang', label: 'Tentang Kami' }
  ];

  const socialLinks = [
    { 
      icon: FiFacebook, 
      url: 'https://facebook.com/bps.ri', 
      label: 'Facebook BPS'
    },
    { 
      icon: FiTwitter, 
      url: 'https://twitter.com/bps_statistics', 
      label: 'Twitter BPS'
    },
    { 
      icon: FiInstagram, 
      url: 'https://instagram.com/bps_statistics', 
      label: 'Instagram BPS'
    },
    { 
      icon: FiYoutube, 
      url: 'https://youtube.com/bpsstatistics', 
      label: 'YouTube BPS'
    }
  ];

  return (
    <footer className="bg-bps-dark text-gray-300">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          {/* Logo dan Deskripsi */}
          <div className="col-span-1 md:col-span-2">
            <div className="flex items-center space-x-3 mb-4">
              <img 
                src="/logo-bps-white.png" 
                alt="Logo BPS" 
                className="h-10 w-auto"
                onError={(e) => {
                  e.target.src = "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNDAiIGhlaWdodD0iNDAiIHZpZXdCb3g9IjAgMCA0MCA0MCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHJlY3Qgd2lkdGg9IjQwIiBoZWlnaHQ9IjQwIiByeD0iOCIgZmlsbD0id2hpdGUiLz4KPHRleHQgeD0iMjAiIHk9IjI2IiBmb250LWZhbWlseT0iQXJpYWwsIHNhbnMtc2VyaWYiIGZvbnQtc2l6ZT0iMTYiIGZvbnQtd2VpZ2h0PSJib2xkIiBmaWxsPSIjMWU0MGFmIiB0ZXh0LWFuY2hvcj0ibWlkZGxlIj5CUFM8L3RleHQ+Cjwvc3ZnPgo=";
                }}
              />
              <div>
                <h3 className="text-xl font-bold text-white">Badan Pusat Statistik</h3>
                <p className="text-sm text-gray-400">Republik Indonesia</p>
              </div>
            </div>
            <p className="text-gray-300 text-sm leading-relaxed mb-6">
              Badan Pusat Statistik (BPS) adalah lembaga pemerintah non-departemen yang bertanggung jawab menyelenggarakan kegiatan statistik di Indonesia. Dashboard ini menyediakan akses mudah ke data strategis pembangunan nasional dan daerah.
            </p>
            
            {/* Kontak */}
            <div className="space-y-2">
              <div className="flex items-center space-x-3">
                <FiMapPin className="w-4 h-4 text-bps-accent" />
                <span className="text-sm">Jl. Dr. Sutomo No. 6-8, Jakarta 10710</span>
              </div>
              <div className="flex items-center space-x-3">
                <FiPhone className="w-4 h-4 text-bps-accent" />
                <span className="text-sm">(021) 3841195</span>
              </div>
              <div className="flex items-center space-x-3">
                <FiMail className="w-4 h-4 text-bps-accent" />
                <span className="text-sm">bpshq@bps.go.id</span>
              </div>
            </div>
          </div>

          {/* Tautan Cepat */}
          <div>
            <h4 className="text-lg font-semibold text-white mb-4">Tautan Cepat</h4>
            <ul className="space-y-2">
              {quickLinks.map((link, index) => (
                <li key={index}>
                  <Link 
                    to={link.path}
                    className="text-gray-300 hover:text-bps-accent text-sm transition-colors duration-200"
                  >
                    {link.label}
                  </Link>
                </li>
              ))}
              <li>
                <a 
                  href="https://www.bps.go.id"
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-gray-300 hover:text-bps-accent text-sm transition-colors duration-200"
                >
                  Website Utama BPS
                </a>
              </li>
            </ul>
          </div>

          {/* Media Sosial */}
          <div>
            <h4 className="text-lg font-semibold text-white mb-4">Media Sosial</h4>
            <div className="flex space-x-4 mb-6">
              {socialLinks.map((social, index) => {
                const Icon = social.icon;
                return (
                  <a
                    key={index}
                    href={social.url}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="p-2 bg-gray-700 rounded-full hover:bg-bps-primary transition-colors duration-200"
                    title={social.label}
                  >
                    <Icon className="w-5 h-5" />
                  </a>
                );
              })}
            </div>
            
            <div className="text-sm text-gray-400">
              <p className="mb-2">Ikuti update terbaru dari BPS di media sosial resmi kami</p>
              <p>© 2024 Badan Pusat Statistik</p>
            </div>
          </div>
        </div>

        {/* Bottom Bar */}
        <div className="border-t border-gray-700 mt-8 pt-6">
          <div className="flex flex-col md:flex-row justify-between items-center text-sm text-gray-400">
            <div className="mb-4 md:mb-0">
              <p>© 2024 Badan Pusat Statistik Republik Indonesia. Hak Cipta Dilindungi.</p>
            </div>
            <div className="flex space-x-6">
              <a href="#" className="hover:text-bps-accent transition-colors duration-200">
                Kebijakan Privasi
              </a>
              <a href="#" className="hover:text-bps-accent transition-colors duration-200">
                Syarat Penggunaan
              </a>
              <a href="#" className="hover:text-bps-accent transition-colors duration-200">
                Sitemap
              </a>
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;