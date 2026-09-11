import { LogLevel } from '@azure/msal-browser';

export const msalConfig = {
  auth: {
    // Id. de aplicación (cliente)
    clientId: 'c64efc77-580d-403d-bd8e-f89813aa0265',
    // Authority con tu Id. de directorio (inquilino)
    authority: 'https://login.microsoftonline.com/8680c901-cdee-41fb-b489-95b35c8ffaf0',
    // URI registrada en Azure como Single-Page Application (SPA)
    redirectUri: 'http://localhost:4200',
    postLogoutRedirectUri: 'http://localhost:4200',
    navigateToLoginRequestUrl: true // <-- Cambiado a true para limpiar el hash #code=... de la URL
  },
  cache: {
    cacheLocation: 'localStorage', // Mantiene la sesión iniciada al recargar
    storeAuthStateInCookie: false
  },
  system: {
    loggerOptions: {
      loggerCallback: (level, message, containsPii) => {
        if (containsPii) return;
        switch (level) {
          case LogLevel.Error:
            console.error(message);
            return;
          case LogLevel.Warning:
            console.warn(message);
            return;
          default:
            return;
        }
      }
    }
  }
};

export const loginRequest = {
  scopes: ['User.Read', 'openid', 'profile', 'email']
};