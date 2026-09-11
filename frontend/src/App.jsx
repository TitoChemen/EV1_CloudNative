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
import Seguimiento from './pages/Seguimiento';
import Historial from './pages/Historial';
import Admin from './pages/Admin';
import './styles/App.css';

import lenovoImg from './assets/lenovo-loq.png';
import gigabyteImg from './assets/notebook-gamer-gigabyte.png';
import msiImg from './assets/msi-katana.png';

const initialCatalog = [
  {
    id: 1,
    category: 'notebooks',
    title: 'Notebook Gamer Lenovo® LOQ',
    specs: 'Core i7 RTX 4060 16" WUXGA 16GB 1TB SSD',
    currentPrice: '$1.459.990',
    refPrice: '$1.899.990',
    discount: '-23%',
    img: lenovoImg,
    badge: 'Oferta',
    stock: 12
  },
  {
    id: 2,
    category: 'notebooks',
    title: 'Notebook Gamer Gigabyte® AORUS',
    specs: 'Ryzen 9 RTX 4070 16" 32GB 1TB SSD',
    currentPrice: '$1.819.990',
    refPrice: '$1.969.000',
    discount: '-8%',
    img: gigabyteImg,
    badge: 'Destacado',
    stock: 8
  },
  {
    id: 3,
    category: 'notebooks',
    title: 'Notebook Gamer MSI Katana 15 B12V',
    specs: '15.6" FHD, i7 12650H, RTX 4070 8GB, RAM 16GB, SSD 512GB',
    currentPrice: '$1.799.990',
    refPrice: '$1.880.990',
    discount: '-4%',
    img: msiImg,
    badge: 'Nuevo',
    stock: 5
  },
  {
    id: 4,
    category: 'notebooks',
    title: 'Notebook Gamer Lenovo Legion® Pro',
    specs: 'Ryzen 7 RTX 4060 16GB 512GB SSD 165Hz',
    currentPrice: '$1.299.990',
    refPrice: '$1.599.990',
    discount: '-18%',
    img: lenovoImg,
    badge: 'Más Vendido',
    stock: 15
  },
  {
    id: 5,
    category: 'perifericos',
    title: 'Monitor Gamer Gigabyte® 27" QHD',
    specs: 'IPS, 165Hz, 1ms, FreeSync Premium, HDR400',
    currentPrice: '$289.990',
    refPrice: '$349.990',
    discount: '-17%',
    img: gigabyteImg,
    badge: 'Oferta',
    stock: 20
  },
  {
    id: 6,
    category: 'componentes',
    title: 'Tarjeta de Video RTX 4070 Super 12GB',
    specs: 'GDDR6X, Triple Fan, PCIe 4.0, DLSS 3.5',
    currentPrice: '$699.990',
    refPrice: '$789.990',
    discount: '-11%',
    img: msiImg,
    badge: 'Stock Limitado',
    stock: 4
  }
];

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

  const [products, setProducts] = useState(() => {
    try {
      const saved = localStorage.getItem('pedidos360_products');
      return saved ? JSON.parse(saved) : initialCatalog;
    } catch {
      return initialCatalog;
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

  const handleUpdateProducts = (updated) => {
    setProducts(updated);
    localStorage.setItem('pedidos360_products', JSON.stringify(updated));
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
            products={products}
            onNavigateToLogin={() => setCurrentPage('login')}
          />
        )}
        {currentPage === 'about' && <About />}
        {currentPage === 'contact' && <Contact />}
        {currentPage === 'cart' && (
          <Carrito
            user={user}
            products={products}
            onUpdateProducts={handleUpdateProducts}
            onNavigateToProducts={() => setCurrentPage('products')}
            onNavigateToTracking={() => setCurrentPage('tracking')}
          />
        )}
        {currentPage === 'tracking' && (
          <Seguimiento onNavigateToProducts={() => setCurrentPage('products')} />
        )}
        {currentPage === 'history' && (
          <Historial
            user={user}
            onNavigateToProducts={() => setCurrentPage('products')}
            onSelectTrackingOrder={() => setCurrentPage('tracking')}
          />
        )}
        {currentPage === 'admin' && (
          <Admin
            products={products}
            onUpdateProducts={handleUpdateProducts}
          />
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