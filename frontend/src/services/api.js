import { msalInstance } from '../main';
import { loginRequest } from '../config/auth-config';

const BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

/**
 * Obtiene el token JWT vigente:
 * Si la sesión proviene de Azure, pide el token a MSAL (renovándolo automáticamente si expiró).
 * Si proviene del login local, lo lee de localStorage.
 */
async function getBearerToken() {
  // 1. Intentar obtener cuenta activa de Azure
  const activeAccount = msalInstance.getActiveAccount();
  if (activeAccount) {
    try {
      const response = await msalInstance.acquireTokenSilent({
        ...loginRequest,
        account: activeAccount
      });
      return response.accessToken;
    } catch {
      // Fallback si la sesión silenciosa falló
      return null;
    }
  }

  // 2. Intentar obtener token de sesión local
  try {
    const sessionData = localStorage.getItem('pedidos360_user_session');
    if (sessionData) {
      const parsed = JSON.parse(sessionData);
      return parsed.accessToken || parsed.token || null;
    }
  } catch {
    return null;
  }

  return null;
}

/**
 * Cliente HTTP base conectado a Spring Security
 */
async function request(endpoint, options = {}) {
  const url = `${BASE_URL}${endpoint}`;
  const token = await getBearerToken();

  const defaultHeaders = {
    'Content-Type': 'application/json',
    Accept: 'application/json'
  };

  if (token) {
    defaultHeaders['Authorization'] = `Bearer ${token}`;
  }

  const config = {
    ...options,
    headers: {
      ...defaultHeaders,
      ...options.headers
    }
  };

  try {
    const response = await fetch(url, config);

    // Spring Security: 401 Unauthorized (Token no enviado, inválido o expirado)
    if (response.status === 401) {
      localStorage.removeItem('pedidos360_user_session');
      window.dispatchEvent(new CustomEvent('auth:unauthorized'));
      throw new Error('Sesión no autorizada o expirada. Por favor, inicia sesión nuevamente.');
    }

    // Spring Security: 403 Forbidden (Autenticado, pero sin permisos para este recurso)
    if (response.status === 403) {
      throw new Error('Acceso denegado: no cuentas con los permisos necesarios para realizar esta acción.');
    }

    if (!response.ok) {
      const errorBody = await response.json().catch(() => ({}));
      throw new Error(errorBody.message || `Error en la petición: ${response.status}`);
    }

    if (response.status === 204) {
      return null;
    }

    return await response.json();
  } catch (error) {
    console.error(`[Spring Security API Error] [${options.method || 'GET'}] ${url}:`, error.message);
    throw error;
  }
}

export const api = {
  get: (endpoint, headers = {}) => request(endpoint, { method: 'GET', headers }),
  post: (endpoint, body, headers = {}) => request(endpoint, { method: 'POST', body: JSON.stringify(body), headers }),
  put: (endpoint, body, headers = {}) => request(endpoint, { method: 'PUT', body: JSON.stringify(body), headers }),
  delete: (endpoint, headers = {}) => request(endpoint, { method: 'DELETE', headers })
};

export default api;