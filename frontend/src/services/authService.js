import { msalInstance, loginRequest } from "../config/auth-config";

export const loginWithAzurePopup = async () => {
  try {
    await msalInstance.loginRedirect(loginRequest);
  } catch (error) {
    console.error("Error al redirigir a Azure:", error);
    throw error;
  }
};

export const loginWithCredentials = async (email, password) => {
  const accounts = JSON.parse(localStorage.getItem("pedidos360_accounts") || "[]");
  const found = accounts.find(
      (acc) => acc.email?.toLowerCase() === email?.toLowerCase() && acc.password === password
  );
  if (!found) throw new Error("Credenciales inválidas. Verifica tu correo y contraseña.");
  return found;
};

export const logoutUser = async (user) => {
  try {
    if (user?.provider === "azure") {
      const account = msalInstance.getActiveAccount();
      // Se limpia también en memoria activa
      msalInstance.setActiveAccount(null);
      await msalInstance.logoutRedirect({
        account: account,
        postLogoutRedirectUri: window.location.origin,
      });
    }
  } catch (error) {
    console.error("Error al cerrar sesión:", error);
  }
};
export const isUserAdmin = (email) => {
  if (!email) return false;

  const account = msalInstance.getActiveAccount();
  if (account) {
    // Valida si el rol viene configurado desde el manifiesto de Azure
    const roles = account.idTokenClaims?.roles || [];
    if (roles.includes("Admin")) return true;
  }

  const emailLower = email.toLowerCase();

  // Validaciones de respaldo por dominio o nombre
  return (
      emailLower.includes("admin") ||
      emailLower.endsWith("@duoc.cl") ||
      emailLower.endsWith("@duocuc.cl") ||
      emailLower.endsWith(".onmicrosoft.com")
  );
};