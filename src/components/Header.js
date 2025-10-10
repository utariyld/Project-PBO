import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { 
  FiHome, 
  FiBarChart3, 
  FiDatabase, 
  FiMessageSquare, 
  FiInfo, 
  FiUser,
  FiMenu,
  FiX
} from 'react-icons/fi';

const Header = ({ user, onLogout }) => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const location = useLocation();

  const navigationItems = [
    { path: '/', label: 'Beranda', icon: FiHome },
    { path: '/visualisasi', label: 'Visualisasi Data', icon: FiBarChart3 },
    { path: '/metadata', label: 'Metadata', icon: FiDatabase },
    { path: '/feedback', label: 'Feedback', icon: FiMessageSquare },
    { path: '/tentang', label: 'Tentang Kami', icon: FiInfo }
  ];

  const isActive = (path) => location.pathname === path;

  return (
    <header className="bg-white shadow-lg sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo BPS */}
          <div className="flex items-center">
            <Link to="/" className="flex items-center space-x-3">
              <img 
                src="/logo-bps.png" 
                alt="Logo BPS" 
                className="h-10 w-auto"
                onError={(e) => {
                  e.target.src = "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNDAiIGhlaWdodD0iNDAiIHZpZXdCb3g9IjAgMCA0MCA0MCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHJlY3Qgd2lkdGg9IjQwIiBoZWlnaHQ9IjQwIiByeD0iOCIgZmlsbD0iIzFlNDBhZiIvPgo8dGV4dCB4PSIyMCIgeT0iMjYiIGZvbnQtZmFtaWx5PSJBcmlhbCwgc2Fucy1zZXJpZiIgZm9udC1zaXplPSIxNiIgZm9udC13ZWlnaHQ9ImJvbGQiIGZpbGw9IndoaXRlIiB0ZXh0LWFuY2hvcj0ibWlkZGxlIj5CUFM8L3RleHQ+Cjwvc3ZnPgo=";
                }}
              />
              <div>
                <h1 className="text-xl font-bold text-bps-primary">BPS</h1>
                <p className="text-xs text-gray-600">Dashboard Data Strategis</p>
              </div>
            </Link>
          </div>

          {/* Desktop Navigation */}
          <nav className="hidden md:flex space-x-8">
            {navigationItems.map((item) => {
              const Icon = item.icon;
              return (
                <Link
                  key={item.path}
                  to={item.path}
                  className={`nav-link flex items-center space-x-2 ${
                    isActive(item.path) ? 'active' : ''
                  }`}
                >
                  <Icon className="w-4 h-4" />
                  <span>{item.label}</span>
                </Link>
              );
            })}
          </nav>

          {/* User Menu */}
          <div className="flex items-center space-x-4">
            {user ? (
              <div className="flex items-center space-x-4">
                <div className="text-sm">
                  <p className="text-gray-700">Selamat datang,</p>
                  <p className="font-semibold text-bps-primary">{user.name}</p>
                </div>
                {user.role === 'admin' && (
                  <Link
                    to="/admin"
                    className="btn-primary text-sm"
                  >
                    Dashboard Admin
                  </Link>
                )}
                <button
                  onClick={onLogout}
                  className="btn-secondary text-sm"
                >
                  Logout
                </button>
              </div>
            ) : (
              <Link
                to="/login"
                className="btn-primary flex items-center space-x-2"
              >
                <FiUser className="w-4 h-4" />
                <span>Login</span>
              </Link>
            )}

            {/* Mobile menu button */}
            <button
              onClick={() => setIsMenuOpen(!isMenuOpen)}
              className="md:hidden p-2 rounded-md text-gray-400 hover:text-gray-500 hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-bps-primary"
            >
              {isMenuOpen ? (
                <FiX className="w-6 h-6" />
              ) : (
                <FiMenu className="w-6 h-6" />
              )}
            </button>
          </div>
        </div>
      </div>

      {/* Mobile Navigation */}
      {isMenuOpen && (
        <div className="md:hidden mobile-menu animate-slide-up">
          <div className="px-2 pt-2 pb-3 space-y-1">
            {navigationItems.map((item) => {
              const Icon = item.icon;
              return (
                <Link
                  key={item.path}
                  to={item.path}
                  className={`block px-3 py-2 rounded-md text-base font-medium ${
                    isActive(item.path)
                      ? 'text-bps-primary bg-blue-50'
                      : 'text-gray-700 hover:text-gray-900 hover:bg-gray-50'
                  }`}
                  onClick={() => setIsMenuOpen(false)}
                >
                  <div className="flex items-center space-x-2">
                    <Icon className="w-5 h-5" />
                    <span>{item.label}</span>
                  </div>
                </Link>
              );
            })}
            
            {/* Mobile User Actions */}
            <div className="border-t border-gray-200 pt-4 mt-4">
              {!user && (
                <Link
                  to="/login"
                  className="block px-3 py-2 rounded-md text-base font-medium text-gray-700 hover:text-gray-900 hover:bg-gray-50"
                  onClick={() => setIsMenuOpen(false)}
                >
                  <div className="flex items-center space-x-2">
                    <FiUser className="w-5 h-5" />
                    <span>Login</span>
                  </div>
                </Link>
              )}
            </div>
          </div>
        </div>
      )}
    </header>
  );
};

export default Header;