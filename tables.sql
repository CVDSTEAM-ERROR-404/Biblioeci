create table if not exists Rol(
id SERIAL primary key ,
nombre varchar(50));


CREATE TABLE IF NOT EXISTS Usuario(
  correo varchar(45)NOT NULL,
  contraseña varchar(45)not null,
  rol int references Rol(id),
  u_area varchar(45) not null,
  nombre VARCHAR(45) NOT NULL,
  PRIMARY KEY (correo))
;


CREATE TABLE IF NOT EXISTS Recurso (
  id SERIAL,
  nombre varchar(45) not null,
  ubicacion varchar(45) not null,
  tipo varchar(45) not null,
  capacidad int not null,
  estado varchar(45) not null,
  PRIMARY KEY (id));



CREATE TABLE IF NOT EXISTS Reserva (
  id SERIAL,
  tipo varchar(45) not null,
  estado varchar(45) not null,
  usuario varchar(45) NOT NULL REFERENCES Usuario(correo),
  recurso INTEGER NOT NULL REFERENCES Recurso(id),
  PRIMARY KEY (id));


 create table if not exists Horario(
 fecha_hora timestamp primary key
 );

CREATE TABLE IF NOT EXISTS Evento (
  id SERIAL,
  reserva INTEGER NOT NULL REFERENCES reserva(id),
  hora_inicio timestamp NOT NULL REFERENCES horario(fecha_hora),
  hora_fin timestamp NOT NULL REFERENCES horario(fecha_hora),
  estado varchar(45),
  PRIMARY KEY (id));
