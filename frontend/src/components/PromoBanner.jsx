import React, { useState } from 'react';
import '../styles/PromoBanner.css';
import lenovoImg from '../assets/lenovo-loq.png';

export default function PromoBanner() {
  const [isPlaying, setIsPlaying] = useState(true);

  return (
    <div className="banner-wrapper">
      {/* Barra superior verde */}
      <div className="banner-top-bar">
        <span>Únicos</span> con compra online y retiro <strong>inmediato</strong> en tienda Pedidos360 en todo Chile.
      </div>

      {/* Contenedor principal del banner */}
      <div className="banner-content">
        {/* Flecha izquierda */}
        <button className="banner-nav-btn prev-btn" aria-label="Anterior">
          &#10094;
        </button>

        {/* Producto Izquierdo */}
        <div className="product-card">
          <div className="product-img-box">
            <img 
              src= {lenovoImg} 
              alt="Notebook Gamer Lenovo" 
              className="product-img"
            />
          </div>
          <div className="product-info">
            <h4 className="product-title">Notebook Gamer Lenovo®</h4>
            <p className="product-specs">Core i7 RTX 4060 16" WUXGA 16GB 1TB</p>
            <div className="price-tag-group">
              <div className="price-main">
                <span className="current-price">$1.459.990</span>
                <span className="ref-price">Referencial: $1.899.990</span>
              </div>
              <span className="discount-badge">-23%</span>
            </div>
          </div>
        </div>

        {/* Centro Promocional */}
        <div className="promo-center">
          <span className="promo-pill">Cumplimos</span>
          <h2 className="promo-title">
            10 <span className="promo-unit">años</span>
          </h2>
          <p className="promo-subtitle">
            y seguimos <strong>actualizándonos_</strong>
          </p>
          <div className="promo-chip">
            <span>v1.0</span>
          </div>
        </div>

        {/* Producto Derecho */}
        <div className="product-card">
          <div className="product-img-box">
            <img 
              src="https://via.placeholder.com/260x180/111/fff?text=Notebook+Gigabyte" 
              alt="Notebook Gamer Gigabyte" 
              className="product-img"
            />
          </div>
          <div className="product-info">
            <h4 className="product-title">Notebook Gamer Gigabyte®</h4>
            <p className="product-specs">Ryzen 9 RTX 4070 16" 32GB 1TB SSD</p>
            <div className="price-tag-group">
              <div className="price-main">
                <span className="current-price">$1.819.990</span>
                <span className="ref-price">Referencial: $1.969.000</span>
              </div>
              <span className="discount-badge">-8%</span>
            </div>
          </div>
        </div>

        {/* Flecha derecha */}
        <button className="banner-nav-btn next-btn" aria-label="Siguiente">
          &#10095;
        </button>

        {/* Botón de pausa */}
        <button 
          className="banner-pause-btn" 
          onClick={() => setIsPlaying(!isPlaying)}
        >
          {isPlaying ? '❚❚' : '▶'}
        </button>
      </div>

      {/* Indicadores inferiores */}
      <div className="banner-dots">
        <span className="dot"></span>
        <span className="dot"></span>
        <span className="dot active"></span>
        <span className="dot"></span>
        <span className="dot"></span>
      </div>
    </div>
  );
}