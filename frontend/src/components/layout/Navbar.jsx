import React, { useState } from 'react';
import '../../styles/Navbar.css';

export default function Navbar() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [credentials, setCredentials] = useState({ email: '', password: '' });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCredentials((prev) => ({ ...prev, [name]: value }));
  };

  const handleLoginSubmit = (e) => {
    e.preventDefault();
    setIsLoggedIn(true);
    setIsModalOpen(false);
    setCredentials({ email: '', password: '' });
  };

  return (
    <>
      <nav className="navbar">
        <a href="#inicio" className="nav-brand">
          Pedidos<span>360</span>
        </a>

        <ul className="nav-links">
          <li><a href="#inicio">Inicio</a></li>
          <li><a href="#productos">Productos</a></li>
          <li><a href="#nosotros">Nosotros</a></li>
          <li><a href="#contactos">Contactos</a></li>
        </ul>

        <div className="nav-actions">
          <button
            type="button"
            className="btn-login"
            onClick={() => setIsModalOpen(true)}
          >
            {isLoggedIn ? (
              <>
                <svg className="icon" viewBox="0 0 24 24">
                  <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14" />
                  <polyline points="22 4 12 14.01 9 11.01" />
                </svg>
                <span>Mi Cuenta</span>
              </>
            ) : (
              <>
                <svg className="icon" viewBox="0 0 24 24">
                  <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2" />
                  <circle cx="12" cy="7" r="4" />
                </svg>
                <span>Iniciar sesión</span>
              </>
            )}
          </button>
        </div>
      </nav>

      {isModalOpen && (
        <div
          className="modal-overlay"
          onClick={(e) => e.target === e.currentTarget && setIsModalOpen(false)}
        >
          <div className="modal-box">
            <button
              type="button"
              className="modal-close"
              onClick={() => setIsModalOpen(false)}
              aria-label="Cerrar modal"
            >
              <svg className="icon-close" viewBox="0 0 24 24">
                <line x1="18" y1="6" x2="6" y2="18" />
                <line x1="6" y1="6" x2="18" y2="18" />
              </svg>
            </button>

            <h2 className="modal-title">Iniciar Sesión</h2>
            <p className="modal-subtitle">Accede al panel de Pedidos360</p>

            <form onSubmit={handleLoginSubmit}>
              <div className="form-group">
                <label htmlFor="email">Correo electrónico</label>
                <input
                  type="email"
                  id="email"
                  name="email"
                  placeholder="tu@correo.com"
                  value={credentials.email}
                  onChange={handleInputChange}
                  required
                />
              </div>

              <div className="form-group">
                <label htmlFor="password">Contraseña</label>
                <input
                  type="password"
                  id="password"
                  name="password"
                  placeholder="••••••••"
                  value={credentials.password}
                  onChange={handleInputChange}
                  required
                />
              </div>

              <button type="submit" className="btn-submit">
                Entrar
              </button>
            </form>
          </div>
        </div>
      )}
    </>
  );
}