import { LogLevel } from '@azure/msal-browser';

export const msalConfig = {
  auth: {
    // Nuevo Client ID de Pedidos360-SPA:
    clientId: '0a895358-1500-448a-982f-afc20822336c',
    // Id. de directorio (inquilino)
    authority: 'https://login.microsoftonline.com/8680c901-cdee-41fb-b489-95b35c8ffaf0',
    redirectUri: 'http://localhost:4200',
    postLogoutRedirectUri: 'http://localhost:4200',
    navigateToLoginRequestUrl: true
  },
  cache: {
    cacheLocation: 'localStorage',
    storeAuthStateInCookie: false
  },
  system: {
    loggerOptions: {
      loggerCallback: (level, message, containsPii) => {
        if (containsPii) return;
        if (level === LogLevel.Error) {
          console.error(message);
        }
      }
    }
  }
};

export const loginRequest = {
  scopes: ['User.Read', 'openid', 'profile', 'email']
};