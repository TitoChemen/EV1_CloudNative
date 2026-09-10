import React, { useState } from 'react';
import { useCart, formatCLP } from '../context/CartContext';
import '../styles/Carrito.css';

export default function Carrito({ onNavigateToProducts }) {
  const { cartItems, addToCart, decreaseQuantity, removeFromCart, clearCart, subtotal } = useCart();
  const [shippingMethod, setShippingMethod] = useState('express'); // 'express' | 'retiro'

  const shippingCost = shippingMethod === 'express' ? (subtotal > 0 ? 4990 : 0) : 0;
  const total = subtotal + shippingCost;

  const handleCheckout = () => {
    alert(`¡Compra procesada con éxito por ${formatCLP(total)}! Se envió el comprobante a tu correo.`);
    clearCart();
    if (onNavigateToProducts) onNavigateToProducts();
  };

  return (
    <section className="cart-wrapper">
      <header className="cart-header">
        <h2>Mi Carrito de Compras</h2>
        <p>Revisa y gestiona los productos gamer seleccionados antes de finalizar tu orden.</p>
      </header>

      {cartItems.length === 0 ? (
        <div className="cart-empty-box">
          <div className="cart-empty-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8">
              <circle cx="9" cy="21" r="1" />
              <circle cx="20" cy="21" r="1" />
              <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6" />
            </svg>
          </div>
          <h3>Tu carrito está vacío</h3>
          <p>Aún no has añadido ningún producto. Revisa nuestro catálogo con stock garantizado.</p>
          <button
            type="button"
            className="cart-shop-btn"
            onClick={onNavigateToProducts}
          >
            Explorar Catálogo
          </button>
        </div>
      ) : (
        <div className="cart-layout">
          {/* Listado de Productos */}
          <div className="cart-items-container">
            <div className="cart-items-header">
              <span>Productos ({cartItems.length})</span>
              <button type="button" className="btn-clear-cart" onClick={clearCart}>
                Vaciar Carro
              </button>
            </div>

            <div className="cart-items-list">
              {cartItems.map((item) => (
                <div key={item.id} className="cart-item-card">
                  <div className="cart-item-img-box">
                    <img src={item.img} alt={item.title} />
                  </div>

                  <div className="cart-item-details">
                    <span className="cart-item-badge">{item.badge || 'Disponible'}</span>
                    <h4 className="cart-item-title">{item.title}</h4>
                    <p className="cart-item-specs">{item.specs}</p>
                    <strong className="cart-item-unit-price">{item.currentPrice} c/u</strong>
                  </div>

                  <div className="cart-item-actions">
                    <div className="quantity-counter">
                      <button
                        type="button"
                        onClick={() => decreaseQuantity(item.id)}
                        aria-label="Disminuir unidad"
                      >
                        -
                      </button>
                      <span className="quantity-display">{item.quantity}</span>
                      <button
                        type="button"
                        onClick={() => addToCart(item)}
                        aria-label="Aumentar unidad"
                      >
                        +
                      </button>
                    </div>

                    <button
                      type="button"
                      className="btn-remove-item"
                      onClick={() => removeFromCart(item.id)}
                      title="Eliminar producto"
                    >
                      <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <polyline points="3 6 5 6 21 6" />
                        <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" />
                      </svg>
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Resumen de Pago */}
          <aside className="cart-summary-card">
            <h3>Resumen de la Orden</h3>

            <div className="summary-shipping-choice">
              <label className="shipping-option">
                <input
                  type="radio"
                  name="shipping"
                  value="express"
                  checked={shippingMethod === 'express'}
                  onChange={() => setShippingMethod('express')}
                />
                <div className="option-label">
                  <strong>Despacho Express RM</strong>
                  <span>Entrega en 24 hrs ($4.990)</span>
                </div>
              </label>

              <label className="shipping-option">
                <input
                  type="radio"
                  name="shipping"
                  value="retiro"
                  checked={shippingMethod === 'retiro'}
                  onChange={() => setShippingMethod('retiro')}
                />
                <div className="option-label">
                  <strong>Retiro Inmediato</strong>
                  <span>Hub Providencia / Las Condes ($0)</span>
                </div>
              </label>
            </div>

            <div className="summary-breakdown">
              <div className="summary-row">
                <span>Subtotal</span>
                <span>{formatCLP(subtotal)}</span>
              </div>
              <div className="summary-row">
                <span>Despacho</span>
                <span>{shippingCost === 0 ? 'Gratis' : formatCLP(shippingCost)}</span>
              </div>
              <div className="summary-divider"></div>
              <div className="summary-row summary-total">
                <strong>Total Final</strong>
                <strong>{formatCLP(total)}</strong>
              </div>
            </div>

            <button
              type="button"
              className="btn-checkout"
              onClick={handleCheckout}
            >
              Continuar al Pago
            </button>

            <button
              type="button"
              className="btn-continue-shopping"
              onClick={onNavigateToProducts}
            >
              Seguir Comprando
            </button>
          </aside>
        </div>
      )}
    </section>
  );
}