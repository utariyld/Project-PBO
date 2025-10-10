import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { 
  FiUser, 
  FiLock, 
  FiEye, 
  FiEyeOff,
  FiShield,
  FiUserCheck,
  FiArrowRight
} from 'react-icons/fi';

const Login = ({ onLogin }) => {
  const [formData, setFormData] = useState({
    username: '',
    password: ''
  });
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    if (error) setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setError('');

    try {
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 1500));
      
      // Demo credentials
      const validUsers = [
        { username: 'admin', password: 'admin123', role: 'admin', name: 'Administrator BPS' },
        { username: 'petugas', password: 'petugas123', role: 'staff', name: 'Petugas Statistik' }
      ];

      const user = validUsers.find(
        u => u.username === formData.username && u.password === formData.password
      );

      if (user) {
        const userData = {
          id: Date.now(),
          username: user.username,
          name: user.name,
          role: user.role,
          loginTime: new Date().toISOString()
        };
        
        onLogin(userData);
        navigate(user.role === 'admin' ? '/admin' : '/');
      } else {
        setError('Username atau password salah');
      }
    } catch (err) {
      setError('Terjadi kesalahan sistem. Coba lagi nanti.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8">
        {/* Header */}
        <div className="text-center">
          <div className="mx-auto h-16 w-16 bg-bps-primary rounded-full flex items-center justify-center">
            <FiShield className="h-8 w-8 text-white" />
          </div>
          <h2 className="mt-6 text-3xl font-bold text-gray-900">
            Login Dashboard BPS
          </h2>
          <p className="mt-2 text-sm text-gray-600">
            Masuk ke sistem manajemen data statistik
          </p>
        </div>

        {/* Demo Credentials */}
        <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
          <h3 className="text-sm font-medium text-blue-800 mb-2">Demo Credentials:</h3>
          <div className="text-xs text-blue-700 space-y-1">
            <p><strong>Admin:</strong> admin / admin123</p>
            <p><strong>Petugas:</strong> petugas / petugas123</p>
          </div>
        </div>

        {/* Login Form */}
        <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
          <div className="space-y-4">
            <div>
              <label htmlFor="username" className="form-label">
                Username
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <FiUser className="h-5 w-5 text-gray-400" />
                </div>
                <input
                  id="username"
                  name="username"
                  type="text"
                  required
                  value={formData.username}
                  onChange={handleInputChange}
                  className="form-input pl-10"
                  placeholder="Masukkan username"
                />
              </div>
            </div>

            <div>
              <label htmlFor="password" className="form-label">
                Password
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <FiLock className="h-5 w-5 text-gray-400" />
                </div>
                <input
                  id="password"
                  name="password"
                  type={showPassword ? 'text' : 'password'}
                  required
                  value={formData.password}
                  onChange={handleInputChange}
                  className="form-input pl-10 pr-10"
                  placeholder="Masukkan password"
                />
                <button
                  type="button"
                  className="absolute inset-y-0 right-0 pr-3 flex items-center"
                  onClick={() => setShowPassword(!showPassword)}
                >
                  {showPassword ? (
                    <FiEyeOff className="h-5 w-5 text-gray-400 hover:text-gray-600" />
                  ) : (
                    <FiEye className="h-5 w-5 text-gray-400 hover:text-gray-600" />
                  )}
                </button>
              </div>
            </div>
          </div>

          {error && (
            <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm">
              {error}
            </div>
          )}

          <div className="flex items-center justify-between">
            <div className="flex items-center">
              <input
                id="remember-me"
                name="remember-me"
                type="checkbox"
                className="h-4 w-4 text-bps-primary focus:ring-bps-primary border-gray-300 rounded"
              />
              <label htmlFor="remember-me" className="ml-2 block text-sm text-gray-700">
                Ingat saya
              </label>
            </div>
            <div className="text-sm">
              <a href="#" className="font-medium text-bps-primary hover:text-blue-700">
                Lupa password?
              </a>
            </div>
          </div>

          <button
            type="submit"
            disabled={isLoading}
            className="group relative w-full flex justify-center py-3 px-4 border border-transparent text-sm font-medium rounded-lg text-white bg-bps-primary hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-bps-primary disabled:opacity-50 disabled:cursor-not-allowed transition-colors duration-200"
          >
            {isLoading ? (
              <div className="flex items-center space-x-2">
                <div className="loading-spinner"></div>
                <span>Memproses...</span>
              </div>
            ) : (
              <div className="flex items-center space-x-2">
                <FiUserCheck className="w-5 h-5" />
                <span>Login</span>
                <FiArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform duration-200" />
              </div>
            )}
          </button>

          <div className="text-center">
            <p className="text-sm text-gray-600">
              Belum punya akses?{' '}
              <a href="#" className="font-medium text-bps-primary hover:text-blue-700">
                Hubungi Administrator
              </a>
            </p>
          </div>
        </form>

        {/* Security Notice */}
        <div className="bg-yellow-50 border border-yellow-200 rounded-lg p-4">
          <div className="flex items-start space-x-2">
            <FiShield className="w-5 h-5 text-yellow-600 mt-0.5 flex-shrink-0" />
            <div>
              <h3 className="text-sm font-medium text-yellow-800">Keamanan Data</h3>
              <p className="text-xs text-yellow-700 mt-1">
                Sistem ini hanya untuk petugas resmi BPS. Semua aktivitas akan dicatat dan dimonitor.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;