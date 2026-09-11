import React, { createContext, useContext, useState, useEffect } from 'react';
import { msalInstance } from '../main';
import { loginRequest } from '../config/auth-config';
import { EventType } from '@azure/msal-browser';
import {
  loginWithAzurePopup,
  loginWithCredentials,
  logoutUser,
  isUserAdmin
} from '../services/authService';

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    try {
      const saved = localStorage.getItem('pedidos360_user_session');
      return saved ? JSON.parse(saved) : null;
    } catch {
      return null;
    }
  });

  const [authError, setAuthError] = useState('');
  const [isAuthenticating, setIsAuthenticating] = useState(false);

  // Normaliza el usuario proveniente de Microsoft Entra ID
  const extractAzureUser = (account) => {
    if (!account) return null;
    const fullName = account.name || 'Usuario';
    const nameParts = fullName.split(' ');
    const email = (
      account.username ||
      account.idTokenClaims?.email ||
      account.idTokenClaims?.preferred_username ||
      ''
    ).toLowerCase();

    return {
      nombre: nameParts[0] || 'Administrador',
      apellido: nameParts.slice(1).join(' ') || '',
      rut: '11.111.111-1',
      email: email,
      direccion: 'Av. Concha y Toro',
      provider: 'azure'
    };
  };

  // 1. Sincronización inicial, listeners de MSAL y comunicación del popup
  useEffect(() => {
    const syncAccount = () => {
      const active = msalInstance.getActiveAccount() || msalInstance.getAllAccounts()[0];
      if (active) {
        msalInstance.setActiveAccount(active);
        const mapped = extractAzureUser(active);
        setUser((prev) => prev || mapped);
        localStorage.setItem('pedidos360_user_session', JSON.stringify(mapped));
      }
    };

    syncAccount();

    // Eventos directos de MSAL
    const callbackId = msalInstance.addEventCallback((event) => {
      if (
        (event.eventType === EventType.LOGIN_SUCCESS || event.eventType === EventType.ACQUIRE_TOKEN_SUCCESS) &&
        event.payload?.account
      ) {
        msalInstance.setActiveAccount(event.payload.account);
        const mapped = extractAzureUser(event.payload.account);
        setUser(mapped);
        localStorage.setItem('pedidos360_user_session', JSON.stringify(mapped));
      }
    });

    // Mensaje recibido cuando el popup se autentica y se cierra
    const handlePopupMessage = (event) => {
      if (event.origin === window.location.origin && event.data?.type === 'MSAL_LOGIN_SUCCESS') {
        const active = msalInstance.getActiveAccount() || msalInstance.getAllAccounts()[0];
        if (active) {
          msalInstance.setActiveAccount(active);
          const mapped = extractAzureUser(active);
          setUser(mapped);
          localStorage.setItem('pedidos360_user_session', JSON.stringify(mapped));
          setIsAuthenticating(false);
        }
      }
    };

    window.addEventListener('message', handlePopupMessage);

    return () => {
      if (callbackId) {
        msalInstance.removeEventCallback(callbackId);
      }
      window.removeEventListener('message', handlePopupMessage);
    };
  }, []);

  // 2. Persistir sesión en localStorage
  useEffect(() => {
    if (user) {
      localStorage.setItem('pedidos360_user_session', JSON.stringify(user));
    } else {
      localStorage.removeItem('pedidos360_user_session');
    }
  }, [user]);

  const loginWithMicrosoft = async () => {
    try {
      setIsAuthenticating(true);
      setAuthError('');
      const azureUser = await loginWithAzurePopup();
      setUser(azureUser);
      localStorage.setItem('pedidos360_user_session', JSON.stringify(azureUser));
      return azureUser;
    } catch (err) {
      setAuthError(err.message || 'Error al autenticar con Microsoft');
      throw err;
    } finally {
      setIsAuthenticating(false);
    }
  };

  const loginLocal = async (email, password) => {
    try {
      setIsAuthenticating(true);
      setAuthError('');
      const localUser = await loginWithCredentials(email, password);
      setUser(localUser);
      localStorage.setItem('pedidos360_user_session', JSON.stringify(localUser));
      return localUser;
    } catch (err) {
      setAuthError(err.message || 'Error al iniciar sesión');
      throw err;
    } finally {
      setIsAuthenticating(false);
    }
  };

  const logout = async () => {
    await logoutUser(user);
    setUser(null);
    localStorage.removeItem('pedidos360_user_session');
  };

  const updateUser = (updatedData) => {
    const merged = { ...user, ...updatedData };
    setUser(merged);
    localStorage.setItem('pedidos360_user_session', JSON.stringify(merged));

    const currentAccounts = JSON.parse(localStorage.getItem('pedidos360_accounts') || '[]');
    const updatedAccounts = currentAccounts.map((acc) =>
      acc.email.toLowerCase() === merged.email.toLowerCase() ? merged : acc
    );
    localStorage.setItem('pedidos360_accounts', JSON.stringify(updatedAccounts));
  };

  const isAdmin = isUserAdmin(user?.email);

  return (
    <AuthContext.Provider
      value={{
        user,
        isAdmin,
        isAuthenticating,
        authError,
        setAuthError,
        loginWithMicrosoft,
        loginLocal,
        logout,
        updateUser
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth debe usarse dentro de un AuthProvider');
  }
  return context;
}