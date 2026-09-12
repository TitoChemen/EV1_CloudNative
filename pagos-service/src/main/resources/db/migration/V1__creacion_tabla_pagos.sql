CREATE TABLE pago (
    id bigint auto_increment primary key,
    id_carrito bigint not null,
    monto_pago int not null,
    metodo_pago varchar(50),
    transaccion_token varchar(255),
    estado_pago varchar(50)
);