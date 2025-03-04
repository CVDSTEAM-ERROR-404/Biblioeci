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
insert into usuario(correo,contraseña,rol,u_area,nombre) values ('b@gmail.com','aaa',1,'aaa','Juanito');
insert into Semestre(fecha_inicio,fecha_fin) values('2019-08-05 07:00:00','2020-12-07 13:00:00');
insert into recurso(nombre,ubicacion,tipo,capacidad,estado,inicio_disponibilidad,fin_disponibilidad) values ('pprueba','BloqueA','SALA_DE_ESTUDIO',5,'Disponible','07:00','19:00');
insert into recurso(nombre,ubicacion,tipo,capacidad,estado,inicio_disponibilidad,fin_disponibilidad) values ('escuela','BloqueA','SALA_DE_ESTUDIO',5,'Disponible','00:00','23:59');
insert into reserva(tipo,estado,fecha_solicitud,usuario,recurso) values ('Simple','Activa','2019-11-07 13:00:00','a@gmail.com',1);
insert into reserva(tipo,estado,fecha_solicitud,usuario,recurso) values ('Simple','Activa','2019-11-27 13:00:00','a@gmail.com',2);
insert into evento(reserva,hora_inicio,hora_fin,estado) values (1,'2019-11-07 07:00:00','2019-11-07 08:00:00','Activa');
insert into evento(reserva,hora_inicio,hora_fin,estado) values (2,'2019-11-27 07:00:00','2020-11-27 08:00:00','Activa');
