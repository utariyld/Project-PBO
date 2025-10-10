/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'bps': {
          'primary': '#1e40af',
          'secondary': '#3b82f6',
          'accent': '#f59e0b',
          'success': '#10b981',
          'warning': '#f59e0b',
          'error': '#ef4444',
          'dark': '#1f2937',
          'light': '#f8fafc'
        }
      },
      fontFamily: {
        'sans': ['Inter', 'system-ui', 'sans-serif'],
      },
      animation: {
        'fade-in': 'fadeIn 0.5s ease-in-out',
        'slide-up': 'slideUp 0.5s ease-out',
        'pulse-slow': 'pulse 3s infinite'
      }
    },
  },
  plugins: [],
}