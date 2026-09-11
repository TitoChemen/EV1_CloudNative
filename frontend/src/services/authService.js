import { msalInstance } from '../main';
import { loginRequest } from '../config/auth-config';

export const ADMIN_EMAIL = 'pedido360@kevinsosag.onmicrosoft.com';

export function isUserAdmin(email) {
  if (!email) return false;
  return email.trim().toLowerCase() === ADMIN_EMAIL.toLowerCase();
}

export async function loginWithAzurePopup() {
  // Redirige en la misma ventana, sin ventanas emergentes ni bloqueos de navegador
  await msalInstance.loginRedirect(loginRequest);
}

export async function loginWithCredentials(emailOrUser) {
  if (typeof emailOrUser === 'object' && emailOrUser !== null) {
    return { ...emailOrUser, provider: 'local' };
  }
  const email = String(emailOrUser || '').trim();
  return {
    nombre: email.split('@')[0] || 'Usuario',
    apellido: '',
    rut: '',
    email: email.toLowerCase(),
    direccion: '',
    provider: 'local'
  };
}

export async function logoutUser(user) {
  localStorage.removeItem('pedidos360_user_session');
  if (user?.provider === 'azure') {
    await msalInstance.logoutRedirect({ postLogoutRedirectUri: window.location.origin });
  }
}