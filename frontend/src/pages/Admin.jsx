import React, { useState } from 'react';
import '../styles/Admin.css';

export default function Admin({ products, onUpdateProducts }) {
  const [productList, setProductList] = useState(products);
  const [searchTerm, setSearchTerm] = useState('');
  const [editingProduct, setEditingProduct] = useState(null);
  const [isNewModalOpen, setIsNewModalOpen] = useState(false);

  // Formulario para crear o editar
  const [formData, setFormData] = useState({
    title: '',
    category: 'notebooks',
    specs: '',
    currentPrice: '',
    refPrice: '',
    discount: '',
    stock: 10,
    badge: 'Disponible'
  });

  // Filtro de productos con stock crítico (inferior a 4)
  const criticalStockProducts = productList.filter((p) => (p.stock ?? 10) < 4);

  const filteredProducts = productList.filter((p) =>
    p.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
    p.category.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleOpenCreate = () => {
    setEditingProduct(null);
    setFormData({
      title: '',
      category: 'notebooks',
      specs: '',
      currentPrice: '$',
      refPrice: '$',
      discount: '',
      stock: 10,
      badge: 'Disponible'
    });
    setIsNewModalOpen(true);
  };

  const handleOpenEdit = (product) => {
    setEditingProduct(product);
    setFormData({
      title: product.title,
      category: product.category,
      specs: product.specs,
      currentPrice: product.currentPrice,
      refPrice: product.refPrice,
      discount: product.discount || '',
      stock: product.stock ?? 10,
      badge: product.badge || 'Disponible'
    });
    setIsNewModalOpen(true);
  };

  // Acción rápida para reponer stock inmediato (+10 unidades)
  const handleRestock = (id) => {
    const updated = productList.map((p) =>
      p.id === id ? { ...p, stock: (p.stock ?? 0) + 10 } : p
    );
    setProductList(updated);
    if (onUpdateProducts) onUpdateProducts(updated);
  };

  const handleDelete = (id) => {
    if (!window.confirm('¿Estás seguro de eliminar este producto del inventario?')) return;
    const updated = productList.filter((p) => p.id !== id);
    setProductList(updated);
    if (onUpdateProducts) onUpdateProducts(updated);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    let updated;

    if (editingProduct) {
      updated = productList.map((p) =>
        p.id === editingProduct.id
          ? { ...p, ...formData, stock: parseInt(formData.stock, 10) }
          : p
      );
    } else {
      const newProduct = {
        id: Date.now(),
        ...formData,
        stock: parseInt(formData.stock, 10),
        img: productList[0]?.img || ''
      };
      updated = [newProduct, ...productList];
    }

    setProductList(updated);
    if (onUpdateProducts) onUpdateProducts(updated);
    setIsNewModalOpen(false);
  };

  return (
    <section className="admin-wrapper">
      <header className="admin-header">
        <div>
          <h2>Panel de Administración - Inventario</h2>
          <p>Control de existencias, precios y catálogo general de Pedidos360.</p>
        </div>
        <button type="button" className="btn-admin-add" onClick={handleOpenCreate}>
          + Agregar Producto
        </button>
      </header>

      {/* Banner de alerta de stock crítico */}
      {criticalStockProducts.length > 0 && (
        <div className="admin-alert-banner">
          <div className="alert-banner-header">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.2">
              <path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z" />
              <line x1="12" y1="9" x2="12" y2="13" />
              <line x1="12" y1="17" x2="12.01" y2="17" />
            </svg>
            <div>
              <strong>¡Alerta de Reposición Urgente!</strong>
              <p>Hay {criticalStockProducts.length} producto(s) con stock en peligro (inferior a 4 unidades). Solicite reposición lo antes posible.</p>
            </div>
          </div>

          <div className="alert-critical-list">
            {criticalStockProducts.map((item) => (
              <div key={item.id} className="alert-critical-item">
                <span>
                  <strong>{item.title}</strong> — Quedan solo <b>{item.stock ?? 0} unid.</b>
                </span>
                <button
                  type="button"
                  className="btn-quick-restock"
                  onClick={() => handleRestock(item.id)}
                >
                  + Reponer (+10)
                </button>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Métricas rápidas */}
      <div className="admin-metrics-grid">
        <div className="metric-card">
          <span>Total Ítems</span>
          <strong>{productList.length}</strong>
        </div>
        <div className="metric-card">
          <span>Stock Total</span>
          <strong>{productList.reduce((acc, p) => acc + (p.stock ?? 0), 0)} u.</strong>
        </div>
        <div className={`metric-card ${criticalStockProducts.length > 0 ? 'metric-card-danger' : ''}`}>
          <span>En Peligro (&lt; 4 unid.)</span>
          <strong>{criticalStockProducts.length}</strong>
        </div>
      </div>

      {/* Buscador de inventario */}
      <div className="admin-search-bar">
        <input
          type="text"
          placeholder="Filtrar por nombre de producto o categoría..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      {/* Tabla de Productos */}
      <div className="admin-table-container">
        <table className="admin-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Producto</th>
              <th>Categoría</th>
              <th>Precio Venta</th>
              <th>Stock</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {filteredProducts.map((product) => {
              const currentStock = product.stock ?? 0;
              const isCritical = currentStock < 4;

              return (
                <tr key={product.id} className={isCritical ? 'tr-stock-critical' : ''}>
                  <td>#{product.id}</td>
                  <td>
                    <div className="admin-product-cell">
                      {product.img && <img src={product.img} alt="" />}
                      <div>
                        <strong>{product.title}</strong>
                        <span>{product.specs}</span>
                      </div>
                    </div>
                  </td>
                  <td><span className="badge-category">{product.category}</span></td>
                  <td><strong>{product.currentPrice}</strong></td>
                  <td>
                    <span className={`stock-indicator ${isCritical ? 'stock-danger' : ''}`}>
                      {currentStock} unid.
                    </span>
                    {isCritical && <span className="tag-reponer-urgente">¡Reponer!</span>}
                  </td>
                  <td>
                    <span className={`badge-badge ${isCritical ? 'badge-danger' : ''}`}>
                      {currentStock <= 0 ? 'Agotado' : isCritical ? 'Peligro' : (product.badge || 'Disponible')}
                    </span>
                  </td>
                  <td>
                    <div className="admin-actions-cell">
                      {isCritical && (
                        <button
                          type="button"
                          className="btn-action-restock"
                          onClick={() => handleRestock(product.id)}
                          title="Reponer 10 unidades automáticamente"
                        >
                          +10
                        </button>
                      )}
                      <button
                        type="button"
                        className="btn-action-edit"
                        onClick={() => handleOpenEdit(product)}
                      >
                        Editar
                      </button>
                      <button
                        type="button"
                        className="btn-action-delete"
                        onClick={() => handleDelete(product.id)}
                      >
                        Eliminar
                      </button>
                    </div>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>

      {/* Modal Crear / Editar Producto */}
      {isNewModalOpen && (
        <div className="admin-modal-overlay" onClick={() => setIsNewModalOpen(false)}>
          <div className="admin-modal-card" onClick={(e) => e.stopPropagation()}>
            <h3>{editingProduct ? 'Editar Producto' : 'Nuevo Producto en Inventario'}</h3>

            <form onSubmit={handleSubmit} className="admin-form">
              <div className="form-group">
                <label>Nombre del Producto *</label>
                <input
                  type="text"
                  required
                  value={formData.title}
                  onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                />
              </div>

              <div className="form-row">
                <div className="form-group">
                  <label>Categoría *</label>
                  <select
                    value={formData.category}
                    onChange={(e) => setFormData({ ...formData, category: e.target.value })}
                  >
                    <option value="notebooks">Notebooks</option>
                    <option value="componentes">Componentes</option>
                    <option value="perifericos">Periféricos</option>
                  </select>
                </div>

                <div className="form-group">
                  <label>Stock Disponible *</label>
                  <input
                    type="number"
                    min="0"
                    required
                    value={formData.stock}
                    onChange={(e) => setFormData({ ...formData, stock: e.target.value })}
                  />
                </div>
              </div>

              <div className="form-row">
                <div className="form-group">
                  <label>Precio Actual *</label>
                  <input
                    type="text"
                    required
                    placeholder="$1.299.990"
                    value={formData.currentPrice}
                    onChange={(e) => setFormData({ ...formData, currentPrice: e.target.value })}
                  />
                </div>

                <div className="form-group">
                  <label>Precio Referencial</label>
                  <input
                    type="text"
                    placeholder="$1.599.990"
                    value={formData.refPrice}
                    onChange={(e) => setFormData({ ...formData, refPrice: e.target.value })}
                  />
                </div>
              </div>

              <div className="form-group">
                <label>Especificaciones Breves *</label>
                <input
                  type="text"
                  required
                  placeholder="Ej: Core i7 RTX 4060 16GB SSD 1TB"
                  value={formData.specs}
                  onChange={(e) => setFormData({ ...formData, specs: e.target.value })}
                />
              </div>

              <div className="admin-modal-buttons">
                <button
                  type="button"
                  className="btn-modal-cancel"
                  onClick={() => setIsNewModalOpen(false)}
                >
                  Cancelar
                </button>
                <button type="submit" className="btn-modal-save">
                  {editingProduct ? 'Guardar Cambios' : 'Crear Producto'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </section>
  );
}