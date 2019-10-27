CREATE TABLE IF NOT EXISTS Usuario(
  correo varchar(45)NOT NULL,
  contraseña varchar(45)not null,
  tipo_de_usuario varchar(45) not null,
  programa varchar(45),
  nombre VARCHAR(45) NOT NULL,
  apellido varchar(45) not null,
  PRIMARY KEY (correo))
;

CREATE SEQUENCE Recurso_seq
INCREMENT 1
START 1;


CREATE TABLE IF NOT EXISTS Recurso (
  id INT NOT NULL DEFAULT NEXTVAL ('Recurso_seq'),
  nombre varchar(45) not null,
  ubicacion varchar(45) not null,
  tipo varchar(45) not null,
  capacidad int not null,
  estado varchar(45) not null,
  PRIMARY KEY (id));
 