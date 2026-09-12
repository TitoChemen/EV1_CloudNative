CREATE TABLE usuario (
    id bigint auto_increment primary key,
    nombre varchar(100) not null,
    apellido varchar(100) not null,
    rut varchar(15) unique not null,
    email varchar(100) unique not null,
    direccion varchar(255)
);