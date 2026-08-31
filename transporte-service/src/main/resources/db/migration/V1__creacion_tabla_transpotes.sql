CREATE TABLE IF NOT EXISTS transporte (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          nro_boleta VARCHAR(100),
    empresa_transporte VARCHAR(100),
    rut_destinatario VARCHAR(15),
    direc_destino VARCHAR(255),
    fecha_entrega_aprox VARCHAR(50) -- O DATE, pero asegúrate que coincida con el modelo
    );