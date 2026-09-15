# Etapa 1: Build de Vite
FROM node:20-slim AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Etapa 2: Nginx con SSL y Proxy Inverso al API Gateway
FROM nginx:alpine

# Generar certificado SSL autofirmado
RUN apk add --no-cache openssl && \
    mkdir -p /etc/nginx/ssl && \
    openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
    -keyout /etc/nginx/ssl/nginx.key \
    -out /etc/nginx/ssl/nginx.crt \
    -subj "/CN=107.23.150.144"

# Nginx configurado para servir SPA y redirigir /api/ al backend interno
RUN echo 'server { \
    listen 443 ssl; \
    ssl_certificate /etc/nginx/ssl/nginx.crt; \
    ssl_certificate_key /etc/nginx/ssl/nginx.key; \
    \
    location /api/ { \
        proxy_pass http://api-gateway:9000/api/; \
        proxy_set_header Host $host; \
        proxy_set_header X-Real-IP $remote_addr; \
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for; \
        proxy_set_header X-Forwarded-Proto https; \
    } \
    \
    location / { \
        root /usr/share/nginx/html; \
        index index.html; \
        try_files $uri $uri/ /index.html; \
    } \
}' > /etc/nginx/conf.d/default.conf

COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 443
CMD ["nginx", "-g", "daemon off;"]