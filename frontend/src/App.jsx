import React, { useState, useEffect } from 'react';
import Layout from './components/layout/Layout';
import Home from './pages/Home';
import Login from './pages/login';
import Register from './pages/register';
import './styles/App.css';

export default function App() {
  const [currentPage, setCurrentPage] = useState('home');
  const [user, setUser] = useState(null);

  const [theme, setTheme] = useState(() => {
    return localStorage.getItem('pedidos360_theme') || 'light';
  });

  useEffect(() => {
    document.documentElement.setAttribute('data-theme', theme);
    localStorage.setItem('pedidos360_theme', theme);
  }, [theme]);

  const toggleTheme = () => {
    setTheme((prev) => (prev === 'light' ? 'dark' : 'light'));
  };

  const handleLoginSuccess = (email) => {
    setUser(email);
    setCurrentPage('home');
  };

  const handleLogout = () => {
    setUser(null);
  };

  return (
    <Layout
      onNavigate={setCurrentPage}
      user={user}
      onLogout={handleLogout}
      theme={theme}
      onToggleTheme={toggleTheme}
    >
      {currentPage === 'home' && <Home />}

      {currentPage === 'login' && (
        <Login
          onLoginSuccess={handleLoginSuccess}
          onNavigateToRegister={() => setCurrentPage('register')}
          onBackToHome={() => setCurrentPage('home')}
        />
      )}

      {currentPage === 'register' && (
        <Register
          onRegisterSuccess={handleLoginSuccess}
          onNavigateToLogin={() => setCurrentPage('login')}
          onBackToHome={() => setCurrentPage('home')}
        />
      )}
    </Layout>
  );
}