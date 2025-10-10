import React, { useState } from 'react';
import { 
  FiMessageSquare, 
  FiSend,
  FiUser,
  FiMail,
  FiPhone,
  FiMapPin,
  FiAlertCircle,
  FiBug,
  FiThumbsUp,
  FiStar
} from 'react-icons/fi';

const FeedbackTypeCard = ({ type, icon: Icon, title, description, isSelected, onClick }) => {
  return (
    <div 
      className={`p-4 rounded-lg border-2 cursor-pointer transition-all duration-200 ${
        isSelected 
          ? 'border-bps-primary bg-blue-50' 
          : 'border-gray-200 hover:border-gray-300'
      }`}
      onClick={onClick}
    >
      <div className="flex items-center space-x-3">
        <div className={`p-2 rounded-lg ${
          isSelected ? 'bg-bps-primary text-white' : 'bg-gray-100 text-gray-600'
        }`}>
          <Icon className="w-5 h-5" />
        </div>
        <div>
          <h3 className={`font-medium ${
            isSelected ? 'text-bps-primary' : 'text-gray-900'
          }`}>{title}</h3>
          <p className="text-sm text-gray-600">{description}</p>
        </div>
      </div>
    </div>
  );
};

const ContactCard = ({ icon: Icon, title, content, link }) => {
  return (
    <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
      <div className="flex items-center space-x-3 mb-4">
        <div className="p-2 bg-bps-primary rounded-lg">
          <Icon className="w-5 h-5 text-white" />
        </div>
        <h3 className="font-semibold text-gray-900">{title}</h3>
      </div>
      <p className="text-gray-600 mb-4">{content}</p>
      {link && (
        <a 
          href={link}
          className="text-bps-primary hover:text-blue-700 font-medium transition-colors duration-200"
        >
          Hubungi Sekarang
        </a>
      )}
    </div>
  );
};

const Feedback = () => {
  const [feedbackType, setFeedbackType] = useState('');
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    organization: '',
    phone: '',
    subject: '',
    message: '',
    rating: 0
  });
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [showThankYou, setShowThankYou] = useState(false);

  const feedbackTypes = [
    {
      type: 'suggestion',
      icon: FiThumbsUp,
      title: 'Saran Perbaikan',
      description: 'Berikan saran untuk meningkatkan layanan kami'
    },
    {
      type: 'bug',
      icon: FiBug,
      title: 'Laporkan Masalah',
      description: 'Laporkan bug atau error pada sistem'
    },
    {
      type: 'data',
      icon: FiAlertCircle,
      title: 'Koreksi Data',
      description: 'Laporkan kesalahan atau inkonsistensi data'
    },
    {
      type: 'general',
      icon: FiMessageSquare,
      title: 'Pertanyaan Umum',
      description: 'Tanyakan tentang layanan atau data BPS'
    }
  ];

  const contactMethods = [
    {
      icon: FiMail,
      title: 'Email',
      content: 'bpshq@bps.go.id',
      link: 'mailto:bpshq@bps.go.id'
    },
    {
      icon: FiPhone,
      title: 'Telepon',
      content: '(021) 3841195, 3842508',
      link: 'tel:+62213841195'
    },
    {
      icon: FiMapPin,
      title: 'Alamat',
      content: 'Jl. Dr. Sutomo No. 6-8, Jakarta Pusat 10710',
      link: 'https://maps.google.com/?q=BPS+Jakarta'
    }
  ];

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleRatingClick = (rating) => {
    setFormData(prev => ({
      ...prev,
      rating
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 2000));
    
    setIsSubmitting(false);
    setShowThankYou(true);
    
    // Reset form after 3 seconds
    setTimeout(() => {
      setShowThankYou(false);
      setFormData({
        name: '',
        email: '',
        organization: '',
        phone: '',
        subject: '',
        message: '',
        rating: 0
      });
      setFeedbackType('');
    }, 3000);
  };

  if (showThankYou) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center py-8">
        <div className="max-w-md mx-auto text-center">
          <div className="bg-white p-8 rounded-xl shadow-lg border border-gray-100">
            <div className="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <FiThumbsUp className="w-8 h-8 text-green-600" />
            </div>
            <h2 className="text-2xl font-bold text-gray-900 mb-2">Terima Kasih!</h2>
            <p className="text-gray-600 mb-4">
              Feedback Anda telah berhasil dikirim. Tim kami akan meninjau dan merespons dalam 1-2 hari kerja.
            </p>
            <div className="w-full bg-gray-200 rounded-full h-2">
              <div className="bg-green-600 h-2 rounded-full animate-pulse" style={{ width: '100%' }}></div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="text-center mb-12">
          <h1 className="text-3xl font-bold text-gray-900 mb-4">Feedback & Saran</h1>
          <p className="text-xl text-gray-600 max-w-3xl mx-auto">
            Masukan Anda sangat berharga untuk meningkatkan kualitas layanan dan data yang kami sediakan. 
            Sampaikan saran, laporan masalah, atau pertanyaan Anda kepada kami.
          </p>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Feedback Form */}
          <div className="lg:col-span-2">
            <div className="bg-white p-8 rounded-xl shadow-lg border border-gray-100">
              <h2 className="text-2xl font-bold text-gray-900 mb-6">Kirim Feedback</h2>
              
              {/* Feedback Type Selection */}
              <div className="mb-8">
                <h3 className="text-lg font-semibold text-gray-900 mb-4">Pilih Jenis Feedback</h3>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  {feedbackTypes.map((type) => (
                    <FeedbackTypeCard
                      key={type.type}
                      {...type}
                      isSelected={feedbackType === type.type}
                      onClick={() => setFeedbackType(type.type)}
                    />
                  ))}
                </div>
              </div>

              {feedbackType && (
                <form onSubmit={handleSubmit} className="space-y-6">
                  {/* Personal Information */}
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <label className="form-label">Nama Lengkap *</label>
                      <input
                        type="text"
                        name="name"
                        value={formData.name}
                        onChange={handleInputChange}
                        className="form-input"
                        required
                        placeholder="Masukkan nama lengkap"
                      />
                    </div>
                    
                    <div>
                      <label className="form-label">Email *</label>
                      <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleInputChange}
                        className="form-input"
                        required
                        placeholder="nama@email.com"
                      />
                    </div>
                  </div>

                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <label className="form-label">Instansi/Organisasi</label>
                      <input
                        type="text"
                        name="organization"
                        value={formData.organization}
                        onChange={handleInputChange}
                        className="form-input"
                        placeholder="Nama instansi (opsional)"
                      />
                    </div>
                    
                    <div>
                      <label className="form-label">Nomor Telepon</label>
                      <input
                        type="tel"
                        name="phone"
                        value={formData.phone}
                        onChange={handleInputChange}
                        className="form-input"
                        placeholder="08xxxxxxxxxx"
                      />
                    </div>
                  </div>

                  {/* Subject */}
                  <div>
                    <label className="form-label">Subjek *</label>
                    <input
                      type="text"
                      name="subject"
                      value={formData.subject}
                      onChange={handleInputChange}
                      className="form-input"
                      required
                      placeholder="Ringkasan singkat feedback Anda"
                    />
                  </div>

                  {/* Message */}
                  <div>
                    <label className="form-label">Pesan *</label>
                    <textarea
                      name="message"
                      value={formData.message}
                      onChange={handleInputChange}
                      rows="6"
                      className="form-input"
                      required
                      placeholder="Jelaskan feedback Anda secara detail..."
                    />
                  </div>

                  {/* Rating */}
                  <div>
                    <label className="form-label">Rating Layanan (Opsional)</label>
                    <div className="flex items-center space-x-2">
                      {[1, 2, 3, 4, 5].map((star) => (
                        <button
                          key={star}
                          type="button"
                          onClick={() => handleRatingClick(star)}
                          className={`p-1 transition-colors duration-200 ${
                            star <= formData.rating ? 'text-yellow-500' : 'text-gray-300'
                          }`}
                        >
                          <FiStar className="w-6 h-6 fill-current" />
                        </button>
                      ))}
                      <span className="text-sm text-gray-600 ml-2">
                        {formData.rating > 0 && `${formData.rating} dari 5 bintang`}
                      </span>
                    </div>
                  </div>

                  {/* Submit Button */}
                  <div className="flex justify-end">
                    <button
                      type="submit"
                      disabled={isSubmitting}
                      className="btn-primary flex items-center space-x-2 disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                      {isSubmitting ? (
                        <div className="loading-spinner"></div>
                      ) : (
                        <FiSend className="w-4 h-4" />
                      )}
                      <span>{isSubmitting ? 'Mengirim...' : 'Kirim Feedback'}</span>
                    </button>
                  </div>
                </form>
              )}
            </div>
          </div>

          {/* Contact Information */}
          <div className="space-y-6">
            <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
              <h3 className="text-lg font-semibold text-gray-900 mb-4">Informasi Kontak</h3>
              <p className="text-sm text-gray-600 mb-6">
                Anda juga dapat menghubungi kami langsung melalui:
              </p>
              
              <div className="space-y-4">
                {contactMethods.map((contact, index) => (
                  <ContactCard key={index} {...contact} />
                ))}
              </div>
            </div>

            {/* FAQ */}
            <div className="bg-white p-6 rounded-xl shadow-lg border border-gray-100">
              <h3 className="text-lg font-semibold text-gray-900 mb-4">Pertanyaan Umum</h3>
              <div className="space-y-4">
                <div>
                  <h4 className="font-medium text-gray-900 mb-1">Berapa lama respons feedback?</h4>
                  <p className="text-sm text-gray-600">Kami berusaha merespons dalam 1-2 hari kerja.</p>
                </div>
                <div>
                  <h4 className="font-medium text-gray-900 mb-1">Bagaimana cara melaporkan data yang salah?</h4>
                  <p className="text-sm text-gray-600">Pilih "Koreksi Data" dan sertakan detail lokasi serta sumber data.</p>
                </div>
                <div>
                  <h4 className="font-medium text-gray-900 mb-1">Apakah feedback saya akan ditindaklanjuti?</h4>
                  <p className="text-sm text-gray-600">Ya, semua feedback akan ditinjau oleh tim terkait.</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Feedback;