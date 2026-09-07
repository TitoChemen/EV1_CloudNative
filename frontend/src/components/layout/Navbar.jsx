import React from 'react';
import '../../styles/Navbar.css';

export default function Navbar({ onNavigate, user, onLogout, theme, onToggleTheme }) {
  const handleNavClick = (view) => {
    if (onNavigate) {
      onNavigate(view);
    }
  };

  return (
    <header className="navbar-wrapper">
      <nav className="navbar">
        {/* Brand / Logo */}
        <a
          href="#inicio"
          className="nav-brand"
          onClick={(e) => {
            e.preventDefault();
            handleNavClick('home');
          }}
        >
          Pedidos<span>360</span>
        </a>

        {/* Links principales */}
        <ul className="nav-links">
          <li>
            <a
              href="#inicio"
              onClick={(e) => {
                e.preventDefault();
                handleNavClick('home');
              }}
            >
              Inicio
            </a>
          </li>
          <li>
            <a
              href="#productos"
              onClick={(e) => {
                e.preventDefault();
                handleNavClick('home');
              }}
            >
              Productos
            </a>
          </li>
          <li>
            <a
              href="#nosotros"
              onClick={(e) => {
                e.preventDefault();
                handleNavClick('home');
              }}
            >
              Nosotros
            </a>
          </li>
          <li>
            <a
              href="#contactos"
              onClick={(e) => {
                e.preventDefault();
                handleNavClick('home');
              }}
            >
              Contactos
            </a>
          </li>
        </ul>

        {/* Acciones */}
        <div className="nav-actions">
          {/* Alternador Modo Claro / Oscuro */}
          <button
            type="button"
            className="btn-theme-toggle"
            onClick={onToggleTheme}
            aria-label={theme === 'dark' ? 'Activar modo claro' : 'Activar modo oscuro'}
            title={theme === 'dark' ? 'Modo claro' : 'Modo oscuro'}
          >
            {theme === 'dark' ? (
              <svg className="icon-theme" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <circle cx="12" cy="12" r="5" />
                <line x1="12" y1="1" x2="12" y2="3" />
                <line x1="12" y1="21" x2="12" y2="23" />
                <line x1="4.22" y1="4.22" x2="5.64" y2="5.64" />
                <line x1="18.36" y1="18.36" x2="19.78" y2="19.78" />
                <line x1="1" y1="12" x2="3" y2="12" />
                <line x1="21" y1="12" x2="23" y2="12" />
                <line x1="4.22" y1="19.78" x2="5.64" y2="18.36" />
                <line x1="18.36" y1="5.64" x2="19.78" y2="4.22" />
              </svg>
            ) : (
              <svg className="icon-theme" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z" />
              </svg>
            )}
          </button>

          {/* Sesión de usuario */}
          {user ? (
            <button
              type="button"
              className="btn-login"
              onClick={onLogout}
              title="Cerrar sesión"
            >
              <svg className="icon" viewBox="0 0 24 24">
                <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14" />
                <polyline points="22 4 12 14.01 9 11.01" />
              </svg>
              <span>Salir</span>
            </button>
          ) : (
            <button
              type="button"
              className="btn-login"
              onClick={() => handleNavClick('login')}
              aria-label="Iniciar sesión"
            >
              <svg className="icon" viewBox="0 0 24 24">
                <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2" />
                <circle cx="12" cy="7" r="4" />
              </svg>
              <span>Iniciar sesión</span>
            </button>
          )}
        </div>
      </nav>
    </header>
  );
}