CREATE TABLE facturacion (
    id bigint auto_increment primary key,
    id_pago bigint not null,
    id_usuario bigint not null,
    monto double not null,
    nro_boleta varchar(255) not null,
    fecha varchar(50) not null
);