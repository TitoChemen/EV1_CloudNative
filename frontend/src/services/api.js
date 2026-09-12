import { msalInstance, apiRequest } from "../config/auth-config";

// Cambia "3000" por el puerto donde realmente se levanta tu backend o API Gateway
const API_BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:9000";

async function getAccessToken() {
  let account = msalInstance.getActiveAccount();
  if (!account) {
    const accounts = msalInstance.getAllAccounts();
    if (accounts.length > 0) {
      account = accounts[0];
      msalInstance.setActiveAccount(account);
    } else {
      throw new Error("No hay sesión activa. Por favor, inicia sesión.");
    }
  }

  try {
    const response = await msalInstance.acquireTokenSilent({
      ...apiRequest,
      account: account,
    });
    return response.accessToken;
  } catch (error) {
    const response = await msalInstance.acquireTokenPopup({
      ...apiRequest,
      account: account,
    });
    return response.accessToken;
  }
}

export async function request(endpoint, options = {}) {
  const token = await getAccessToken();

  // 1. IMPRIME EL TOKEN EN LA CONSOLA (F12)
  console.log("Token enviado al backend:", token);

  const headers = {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`,
    ...options.headers,
  };

  const fullUrl = endpoint.startsWith("http") ? endpoint : `${API_BASE_URL}${endpoint}`;

  const response = await fetch(fullUrl, {
    ...options,
    headers,
  });

  if (response.status === 401) {
    throw new Error("401 Unauthorized: El token fue rechazado por el backend.");
  }

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.statusText}`);
  }

  if (response.status === 204) return null;
  return response.json();
}

export const api = {
  get: (url) => request(url, { method: "GET" }),
  post: (url, data) => request(url, { method: "POST", body: JSON.stringify(data) }),
  put: (url, data) => request(url, { method: "PUT", body: JSON.stringify(data) }),
  delete: (url) => request(url, { method: "DELETE" }),
};

export default api;