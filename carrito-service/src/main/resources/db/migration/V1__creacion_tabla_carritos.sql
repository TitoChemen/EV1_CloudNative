CREATE TABLE carrito
(
    id bigint auto_increment primary key,
    codigo_producto varchar(50) not null,
    cantidad int not null,
    precio_unitario int not null,
    usuario_id bigint not null
);