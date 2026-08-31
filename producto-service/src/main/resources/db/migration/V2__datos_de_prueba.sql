-- src/main/resources/db/migration/V2__datos_de_prueba.sql

-- Insertar categorías
INSERT INTO categoria (id, nombre) VALUES
                                       (1, 'Gamer'),
                                       (2, 'Periféricos')
ON DUPLICATE KEY UPDATE nombre=nombre;

-- Insertar productos
INSERT INTO productos (codigo, nombre, precio, stock, categoria_id) VALUES
                                                                        ('8465S', 'Monitor Gamer 144Hz', 150000.0, 50, 1),
                                                                        ('3255S', 'Teclado Mecánico RGB', 45000.0, 100, 2),
                                                                        ('4235S', 'Mouse Inalámbrico Pro', 25000.0, 75, 2)
ON DUPLICATE KEY UPDATE nombre=nombre;