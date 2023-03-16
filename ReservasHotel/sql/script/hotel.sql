DROP DATABASE IF EXISTS hotel;
CREATE DATABASE hotel CHARSET utf8mb4;
USE hotel;
CREATE TABLE alojamiento(
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM ('Bungalow', 'Apartamento', 'Habitación', 'VIP'),
    precio INT UNSIGNED NOT NULL,
    num_noches TINYINT UNSIGNED NOT NULL,
    precio_total INT UNSIGNED NOT NULL
);

CREATE TABLE reservas(
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido1 VARCHAR(50) NOT NULL,
    apellido2 VARCHAR(50) NOT NULL,
    id_alojamiento INT UNSIGNED NOT NULL,
    FOREIGN KEY (id_alojamiento) REFERENCES alojamiento(id)
    );
    
    -- Query inicial
    
SELECT reservas.id, reservas.nombre, CONCAT_WS(' ', reservas.apellido1, reservas.apellido2) AS apellidos,
alojamiento.tipo, alojamiento.num_noches, alojamiento.precio_total 
FROM reservas INNER JOIN alojamiento
ON reservas.id_alojamiento = alojamiento.id;
