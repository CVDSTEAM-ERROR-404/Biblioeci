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
  inicio_disponibilidad varchar(20) not null,
  fin_disponibilidad varchar(20) not null,
  PRIMARY KEY (id));



CREATE TABLE IF NOT EXISTS Reserva (
  id SERIAL,
  tipo varchar(45) not null,
  estado varchar(45) not null,
  fecha_solicitud timestamp not null,
  usuario varchar(45) not null REFERENCES Usuario(correo),
  recurso INTEGER not null REFERENCES Recurso(id),
  PRIMARY KEY (id));


CREATE TABLE IF NOT EXISTS Evento (
  id SERIAL,
  reserva INTEGER NOT NULL REFERENCES reserva(id),
  hora_inicio timestamp NOT NULL,
  hora_fin timestamp NOT NULL,
  estado varchar(45) not null,
  PRIMARY KEY (id));


CREATE TABLE IF NOT EXISTS Semestre (
  fecha_inicio TIMESTAMP NOT NULL,
  fecha_fin TIMESTAMP not null
);

insert into rol(nombre) values ('administrador');
insert into usuario(correo,contraseña,rol,u_area,nombre) values ('a@gmail.com','aaa',1,'aaa','Pepito');
