# Biblioeci

Aplicación web para la gestión y reserva de recursos de la biblioteca de la Escuela Colombiana de Ingeniería Julio Garavito.

**Curso:** Ciclos de Vida del Desarrollo de Software

## Integrantes y Roles

|     Nombre    |     Rol         |
|:--------------:|:-------------: |
|Santiago Alzate|Product Owner    |
|Juan Angel |Team Developer       |
|Juan Rojas |Team Developer       |
|Daniel Walteros |Team Developer  |
|David Vasquez |Team Developer    |



## Estado del Proyecto

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/18f5757fdb6e4b41a0e297e42438781e)](https://www.codacy.com/manual/Silenrate/Biblioeci?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=CVDSTEAM-ERROR-404/Biblioeci&amp;utm_campaign=Badge_Grade)
[![CircleCI](https://circleci.com/gh/CVDSTEAM-ERROR-404/Biblioeci.svg?style=svg)](https://circleci.com/gh/CVDSTEAM-ERROR-404/Biblioeci)

## Descripción del Producto

#### Descripción General
Biblioeci es una herramienta donde el personal de la biblioteca pertenecientes a la Escuela Colombiana de Ingeniería Julio Garavito, pueden registrar las salas de estudio, equipos de cómputo y demás recursos con los que cuenta la biblioteca, junto con los horarios de disponibilidad y demás información importante.

#### Manual de Usuario

Para poder utilizar los servicios que ofrece Biblioeci es necesario iniciar sesión.

Las credenciales para ingresar como administrador son las siguientes:

+ **Usuario:** *adm@adm.com*
+ **Contraseña:** *213*

Las credenciales para ingresar como usuario son las siguientes:

+ **Usuario:** *prueba*
+ **Contraseña:** *prueba*

Ver [funcionalidades](resources/md/manual.md)

## Arquitectura y Diseño

#### Modelo E-R
![](resources/modelos/er.jpg)
#### Diagrama de Clases
![](resources/modelos/clases.png)
![](resources/modelos/enti.png)
#### Cobertura de pruebas
![](resources/md/jacoco.png)

![](resources/md/jacoco1.PNG)

![](resources/md/jacoco2.PNG)

En total se realizaron 144 pruebas unitarias y de aceptación

### Descripción de la arquitectura (Capas)

![](resources/md/arq.png)

Se tiene la capa de presentación la cual usa PrimeFaces y los Managed Beans.

En la capa de negocios utilizamos MyBatis, la cual provee los servicios que se comunican con la capa de Persistencia, la cual tiene acceso a la base de datos Postgresql.

#### Stack de Tecnología Utilizado

   * [PrimeFaces (Framework)](https://www.primefaces.org/)
   * [JUnit (Property Based Testing)](https://junit.org/junit5/)
   * [Guice (Inyección de Dependencias)](https://github.com/google/guice)
   * [PostgreSQL (DataBase Management)](https://www.postgresql.org)
   * [Jacoco (cobertura de pruebas)](https://www.jacoco.org/)
   * [Apache Shiro (Autenticación de usuarios)](https://shiro.apache.org/)

#### Enlaces

+ [Despliegue en Heroku](https://biblioeci-cvds2019.herokuapp.com/)
+ [Integración Continua (CircleCI)](https://circleci.com/gh/CVDSTEAM-ERROR-404)

## Descripción del Proceso
#### Integrantes

|     Nombre    |     Rol         |
|:--------------:|:-------------: |
|Juan Angel |Team Developer       |
|Juan Rojas |Team Developer       |
|Daniel Walteros |Team Developer  |
|David Vasquez |Team Developer    |

#### Metodología
Se utilizó la metodoloía Scrum en la cual el equipo se reune y planea lo que se realizará en el Sprint, se discute lo que es necesario para realizar la historia de usuario y se reparten las tareas entre los integrantes.
 
 Al finalizar cada Sprint, el equipo se reune para planear el siguiente y se comentan lo que sucedió con el anterior.
 
 #### Taiga
 
 [Taiga backlog](https://tree.taiga.io/project/jcro15-gestion-de-recursos-biblioteca/backlog)
 #### Información sprints
 
 Por circunstancias de tiempo nos faltaron los filtros de los reportes relacionados con atributos de tipo Date.
 
 Ver [Sprints](resources/md/Sprints.md)
