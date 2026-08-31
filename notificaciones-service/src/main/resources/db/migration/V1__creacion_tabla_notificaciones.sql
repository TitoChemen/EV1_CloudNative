CREATE TABLE notificacion (
    id bigint auto_increment primary key,
    cod_seguimiento varchar(100),
    estado_env varchar(50),
    rastreo varchar(255),
    email_notificacion varchar(100) not null
);