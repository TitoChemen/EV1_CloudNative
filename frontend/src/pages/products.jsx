import React, { useState, useMemo } from 'react';
import { useCart } from '../context/CartContext';
import '../styles/Products.css';
import lenovoImg from '../assets/lenovo-loq.png';
import gigabyteImg from '../assets/notebook-gamer-gigabyte.png';
import msiImg from '../assets/msi-katana.png';
import lenovoSlime from '../assets/lenovo-slime-5.png';
import nvidia4070 from '../assets/nvidia-4070.png';
import monitorGigabyteGS27 from '../assets/monitor-gigabyte-gs27.png';

const initialProducts = [
  {
    id: 1,
    category: 'notebooks',
    title: 'Notebook Gamer Lenovo® LOQ',
    specs: 'Core i7 RTX 4060 16" WUXGA 16GB 1TB SSD',
    currentPrice: '$1.459.990',
    refPrice: '$1.899.990',
    discount: '-23%',
    img: lenovoImg,
    badge: 'Oferta'
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
    badge: 'Destacado'
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
    badge: 'Nuevo'
  },
  {
    id: 4,
    category: 'notebooks',
    title: 'Notebook Gamer Lenovo Legion Slime 5',
    specs: '16AHP9, R7 8845HS, RTX 4060, 16" 240Hz, 16GB RAM, 1TB SSD, W11',
    currentPrice: '$1.299.990',
    refPrice: '$1.599.990',
    discount: '-18%',
    img: lenovoSlime,
    badge: 'Más Vendido'
  },
  {
    id: 5,
    category: 'perifericos',
    title: 'Monitor Gamer Gigabyte GS27QC GAMING 27"',
    specs: 'VA - QHD - 1ms - 165hz/OC 170hz',
    currentPrice: '$278.890',
    refPrice: '$299.990',
    discount: '-7%',
    img: monitorGigabyteGS27,
    badge: 'Oferta'
  },
  {
    id: 6,
    category: 'componentes',
    title: 'Tarjeta de Video RTX 4070 Super 12GB',
    specs: 'GDDR6X, Triple Fan, PCIe 4.0, DLSS 3.5',
    currentPrice: '$699.990',
    refPrice: '$789.990',
    discount: '-11%',
    img: nvidia4070,
    badge: 'Stock Limitado'
  }
];

export default function Products({ user, onNavigateToLogin }) {
  const { addToCart } = useCart();
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [searchTerm, setSearchTerm] = useState('');
  const [showAuthModal, setShowAuthModal] = useState(false);

  const filteredProducts = useMemo(() => {
    return initialProducts.filter((product) => {
      const matchesCategory =
        selectedCategory === 'all' || product.category === selectedCategory;
      const matchesSearch =
        product.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        product.specs.toLowerCase().includes(searchTerm.toLowerCase());
      return matchesCategory && matchesSearch;
    });
  }, [selectedCategory, searchTerm]);

  const handleAddToCartClick = (product) => {
    if (!user) {
      setShowAuthModal(true);
      return;
    }
    addToCart(product);
  };

  return (
    <section className="catalog-wrapper">
      <div className="catalog-header">
        <h2>Catálogo de Productos</h2>
        <p>Explora todo nuestro equipamiento y tecnología disponible con retiro o despacho inmediato.</p>
      </div>

      <div className="catalog-controls">
        <div className="catalog-search">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
            <circle cx="11" cy="11" r="8" />
            <line x1="21" y1="21" x2="16.65" y2="16.65" />
          </svg>
          <input
            type="text"
            placeholder="Buscar por marca, procesador o modelo..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>

        <div className="catalog-filters">
          <button
            type="button"
            className={`filter-btn ${selectedCategory === 'all' ? 'active' : ''}`}
            onClick={() => setSelectedCategory('all')}
          >
            Todos
          </button>
          <button
            type="button"
            className={`filter-btn ${selectedCategory === 'notebooks' ? 'active' : ''}`}
            onClick={() => setSelectedCategory('notebooks')}
          >
            Notebooks
          </button>
          <button
            type="button"
            className={`filter-btn ${selectedCategory === 'componentes' ? 'active' : ''}`}
            onClick={() => setSelectedCategory('componentes')}
          >
            Componentes
          </button>
          <button
            type="button"
            className={`filter-btn ${selectedCategory === 'perifericos' ? 'active' : ''}`}
            onClick={() => setSelectedCategory('perifericos')}
          >
            Periféricos
          </button>
        </div>
      </div>

      {filteredProducts.length === 0 ? (
        <div className="catalog-empty">
          <p>No se encontraron productos que coincidan con tu búsqueda.</p>
        </div>
      ) : (
        <div className="catalog-grid">
          {filteredProducts.map((product) => (
            <div key={product.id} className="catalog-card">
              {product.badge && <span className="card-badge">{product.badge}</span>}

              <div className="card-img-box">
                <img src={product.img} alt={product.title} className="card-img" />
              </div>

              <div className="card-content">
                <h3 className="card-title">{product.title}</h3>
                <p className="card-specs">{product.specs}</p>

                <div className="card-pricing">
                  <div className="price-box">
                    <span className="current-price">{product.currentPrice}</span>
                    <span className="ref-price">Ref: {product.refPrice}</span>
                  </div>
                  <span className="discount-pill">{product.discount}</span>
                </div>

                <button
                  type="button"
                  className="card-buy-btn"
                  onClick={() => handleAddToCartClick(product)}
                >
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <circle cx="9" cy="21" r="1" />
                    <circle cx="20" cy="21" r="1" />
                    <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6" />
                  </svg>
                  <span>Añadir al Carro</span>
                </button>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Modal aviso inicio de sesión */}
      {showAuthModal && (
        <div className="auth-required-overlay" onClick={() => setShowAuthModal(false)}>
          <div className="auth-required-card" onClick={(e) => e.stopPropagation()}>
            <div className="auth-required-icon">
              <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <rect x="3" y="11" width="18" height="11" rx="2" ry="2" />
                <path d="M7 11V7a5 5 0 0 1 10 0v4" />
              </svg>
            </div>
            <h3>Inicio de Sesión Requerido</h3>
            <p>Para agregar los productos al carro se debe tener un inicio de sesión previo.</p>
            <span className="auth-required-question">¿Quiere iniciar sesión?</span>

            <div className="auth-required-actions">
              <button
                type="button"
                className="btn-auth-cancel"
                onClick={() => setShowAuthModal(false)}
              >
                Cancelar
              </button>
              <button
                type="button"
                className="btn-auth-confirm"
                onClick={() => {
                  setShowAuthModal(false);
                  if (onNavigateToLogin) onNavigateToLogin();
                }}
              >
                Iniciar sesión
              </button>
            </div>
          </div>
        </div>
      )}
    </section>
  );
}