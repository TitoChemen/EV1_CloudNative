import React, { useState, useEffect } from 'react';
import { formatCLP } from '../context/CartContext';
import '../styles/Seguimiento.css';

const ETAPAS = [
  { key: 'confirmado', label: 'Pago Confirmado', desc: 'Tu orden fue recibida y validada.' },
  { key: 'preparando', label: 'Preparando Pedido', desc: 'Empacando en bodega central.' },
  { key: 'en_camino', label: 'En Camino', desc: 'Repartidor asignado con ruta activa.' },
  { key: 'entregado', label: 'Entregado', desc: 'Recibido en la dirección de entrega.' }
];

export default function Seguimiento({ onNavigateToProducts }) {
  const [orden, setOrden] = useState(() => {
    try {
      const saved = localStorage.getItem('pedidos360_active_order');
      return saved ? JSON.parse(saved) : null;
    } catch {
      return null;
    }
  });

  const getStepIndex = (estado) => {
    return ETAPAS.findIndex((e) => e.key === estado);
  };

  const currentStep = orden ? getStepIndex(orden.estado) : 0;

  // Permite simular el avance del repartidor para probar la vista
  const handleAvanzarEstado = () => {
    if (!orden) return;
    const nextIdx = (currentStep + 1) % ETAPAS.length;
    const nuevoEstado = ETAPAS[nextIdx].key;
    const ordenActualizada = { ...orden, estado: nuevoEstado };
    setOrden(ordenActualizada);
    localStorage.setItem('pedidos360_active_order', JSON.stringify(ordenActualizada));
  };

  if (!orden) {
    return (
      <section className="tracking-wrapper">
        <div className="tracking-empty">
          <div className="tracking-empty-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8">
              <rect x="1" y="3" width="15" height="13" rx="2" />
              <polygon points="16 8 20 8 23 11 23 16 16 16 16 8" />
              <circle cx="5.5" cy="18.5" r="2.5" />
              <circle cx="18.5" cy="18.5" r="2.5" />
            </svg>
          </div>
          <h2>No tienes envíos activos</h2>
          <p>Realiza una compra en nuestro catálogo para visualizar el seguimiento en tiempo real.</p>
          <button type="button" className="btn-tracking-home" onClick={onNavigateToProducts}>
            Ir al Catálogo
          </button>
        </div>
      </section>
    );
  }

  return (
    <section className="tracking-wrapper">
      <header className="tracking-header">
        <div className="tracking-header-info">
          <span className="tracking-badge-live">Envío en Tiempo Real</span>
          <h2>Seguimiento de Despacho</h2>
          <p className="tracking-code">
            Número de Guía: <strong>{orden.trackingId}</strong>
          </p>
        </div>
        <button type="button" className="btn-simular-avance" onClick={handleAvanzarEstado}>
          Simular Avance Repartidor
        </button>
      </header>

      {/* Barra de progreso / Stepper */}
      <div className="tracking-stepper-card">
        <div className="stepper-progress-bar">
          <div
            className="stepper-progress-fill"
            style={{ width: `${(currentStep / (ETAPAS.length - 1)) * 100}%` }}
          />
        </div>

        <div className="stepper-steps">
          {ETAPAS.map((etapa, idx) => {
            const isCompleted = idx <= currentStep;
            const isCurrent = idx === currentStep;
            return (
              <div
                key={etapa.key}
                className={`step-item ${isCompleted ? 'completed' : ''} ${isCurrent ? 'current' : ''}`}
              >
                <div className="step-circle">
                  {idx < currentStep ? (
                    <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="3">
                      <polyline points="20 6 9 17 4 12" />
                    </svg>
                  ) : (
                    <span>{idx + 1}</span>
                  )}
                </div>
                <span className="step-label">{etapa.label}</span>
                <span className="step-desc">{etapa.desc}</span>
              </div>
            );
          })}
        </div>
      </div>

      <div className="tracking-grid">
        {/* Datos de Entrega */}
        <div className="tracking-card">
          <div className="tracking-card-title">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z" />
              <circle cx="12" cy="10" r="3" />
            </svg>
            <h3>Destinatario y Entrega</h3>
          </div>

          <div className="tracking-data-list">
            <div className="data-item">
              <span className="data-label">Cliente</span>
              <strong className="data-value">{orden.cliente.nombre} {orden.cliente.apellido}</strong>
            </div>
            <div className="data-item">
              <span className="data-label">RUT</span>
              <strong className="data-value">{orden.cliente.rut}</strong>
            </div>
            <div className="data-item">
              <span className="data-label">Dirección</span>
              <strong className="data-value">{orden.cliente.direccion}</strong>
            </div>
            <div className="data-item">
              <span className="data-label">Comuna</span>
              <strong className="data-value">{orden.cliente.comuna}</strong>
            </div>
            <div className="data-item">
              <span className="data-label">Método</span>
              <strong className="data-value">
                {orden.metodoEnvio === 'express' ? 'Express 24h' : orden.metodoEnvio === 'estandar' ? 'Estándar RM' : 'Retiro en Hub'}
              </strong>
            </div>
          </div>
        </div>

        {/* Resumen de Productos de la Orden */}
        <div className="tracking-card">
          <div className="tracking-card-title">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <rect x="2" y="4" width="20" height="16" rx="2" />
              <line x1="6" y1="8" x2="18" y2="8" />
              <line x1="6" y1="12" x2="18" y2="12" />
              <line x1="6" y1="16" x2="12" y2="16" />
            </svg>
            <h3>Detalle del Pedido</h3>
          </div>

          <div className="tracking-items-mini">
            {orden.items.map((item) => (
              <div key={item.id} className="mini-item">
                <img src={item.img} alt={item.title} />
                <div className="mini-info">
                  <strong>{item.title}</strong>
                  <span>Cant: {item.quantity} × {item.currentPrice}</span>
                </div>
              </div>
            ))}
          </div>

          <div className="tracking-totals">
            <div className="total-row">
              <span>Despacho</span>
              <span>{orden.costoEnvio === 0 ? 'Gratis' : formatCLP(orden.costoEnvio)}</span>
            </div>
            <div className="total-row total-main">
              <strong>Total Pagado</strong>
              <strong>{formatCLP(orden.total)}</strong>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}