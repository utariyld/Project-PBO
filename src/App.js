import React, { useState } from 'react';
import { Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import Home from './pages/Home';
import VisualisasiData from './pages/VisualisasiData';
import Metadata from './pages/Metadata';
import Feedback from './pages/Feedback';
import TentangKami from './pages/TentangKami';
import Login from './pages/Login';
import Admin from './pages/Admin';

function App() {
  const [user, setUser] = useState(null);

  const handleLogin = (userData) => {
    setUser(userData);
  };

  const handleLogout = () => {
    setUser(null);
  };

  return (
    <div className="App">
      <Header user={user} onLogout={handleLogout} />
      
      <main className="min-h-screen">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/visualisasi" element={<VisualisasiData />} />
          <Route path="/metadata" element={<Metadata />} />
          <Route path="/feedback" element={<Feedback />} />
          <Route path="/tentang" element={<TentangKami />} />
          <Route path="/login" element={<Login onLogin={handleLogin} />} />
          <Route path="/admin" element={user?.role === 'admin' ? <Admin user={user} /> : <Login onLogin={handleLogin} />} />
        </Routes>
      </main>

      <Footer />
    </div>
  );
}

export default App;