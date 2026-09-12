CREATE TABLE inventario (
                            id bigint auto_increment primary key,
                            codigo_producto varchar(20) not null unique,
                            stock_disponible int not null,
                            pasillo_bodega varchar(50),
                            estado_stock varchar(50)
);