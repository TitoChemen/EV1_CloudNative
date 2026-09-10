import React, { useState, useEffect } from 'react';
import { CartProvider } from './context/CartContext';
import Layout from './components/layout/Layout';
import Home from './pages/Home';
import Products from './pages/products';
import About from './pages/about';
import Contact from './pages/contact';
import Carrito from './pages/Carrito';
import Login from './pages/login';
import Register from './pages/register';
import './styles/App.css';

export default function App() {
  const [currentPage, setCurrentPage] = useState('home');
  const [user, setUser] = useState(() => {
    try {
      const saved = localStorage.getItem('pedidos360_user_session');
      return saved ? JSON.parse(saved) : null;
    } catch {
      return null;
    }
  });

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

  const handleLoginSuccess = (userData) => {
    let fullUser;
    if (typeof userData === 'object' && userData !== null) {
      fullUser = userData;
    } else {
      const registeredAccounts = JSON.parse(localStorage.getItem('pedidos360_accounts') || '[]');
      const found = registeredAccounts.find((acc) => acc.email === userData);
      fullUser = found || {
        nombre: userData ? userData.split('@')[0] : '',
        apellido: '',
        rut: '',
        email: userData || '',
        direccion: ''
      };
    }

    setUser(fullUser);
    localStorage.setItem('pedidos360_user_session', JSON.stringify(fullUser));
    setCurrentPage('home');
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('pedidos360_user_session');
  };

  const handleUpdateUser = (updatedData) => {
    setUser(updatedData);
    localStorage.setItem('pedidos360_user_session', JSON.stringify(updatedData));

    // Actualiza también en la lista de cuentas para futuros logins
    const currentAccounts = JSON.parse(localStorage.getItem('pedidos360_accounts') || '[]');
    const updatedAccounts = currentAccounts.map((acc) =>
      acc.email === updatedData.email ? updatedData : acc
    );
    localStorage.setItem('pedidos360_accounts', JSON.stringify(updatedAccounts));
  };

  return (
    <CartProvider>
      <Layout
        onNavigate={setCurrentPage}
        user={user}
        onLogout={handleLogout}
        theme={theme}
        onToggleTheme={toggleTheme}
        onUpdateUser={handleUpdateUser}
      >
        {currentPage === 'home' && <Home />}
        {currentPage === 'products' && (
          <Products
            user={user}
            onNavigateToLogin={() => setCurrentPage('login')}
          />
        )}
        {currentPage === 'about' && <About />}
        {currentPage === 'contact' && <Contact />}
        {currentPage === 'cart' && (
          <Carrito onNavigateToProducts={() => setCurrentPage('products')} />
        )}

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
    </CartProvider>
  );
}