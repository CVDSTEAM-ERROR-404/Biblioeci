# Funcionalidades

## Servicios Ofrecidos

La página cuenta con el los siguientes servicios:
+ **Registrar**
   + [Registrar Recurso](#registrar-recurso)
   + [Realizar Reserva](#realizar-reserva)
   
+ **Actualizar**
   + [Actualizar estado del Recurso](#actualizar-estado-del-recurso)
   
+ **Consultar**
   + [Consultar los recursos disponibles](#consultar-los-recursos-disponibles)
   + [Consultar mis reservas](#consultar-mis-reservas)
   + [Consultar detalle reservas](#consultar-detalle-reservas)

## Registrar

### Registrar Recurso

Al escoger esta opción el usuario irá a una página en la cual se muestra el siguiente formulario:

![](/resources/md/registrar.png)

El usuario selecciona la hora de disponibilidad con un deslizador.

![](/resources/md/hora.png)

El usuario rellena estos campos y registra el recurso, si el registro fue exitoso se muestra un mensaje al usuario.

### Realizar Reserva

Desde la página de consultar recursos disponibles si el usuario selecciona un recurso es direccionado a la siguiente página:

![](/resources/md/horario.png)

En dicha página puede observar las reservas de dicho recurso para escoger su reserva.

Al darle click en un día se despliega el siguiente panel para realizar la reserva

![](/resources/md/horario2.png)

## Actualizar

### Actualizar estado del Recurso

Al escoger esta opción el usuario irá a una página en la cual se muestran los recursos.

![](/resources/md/act.png)

El usuario selecciona el recurso a actualizar y se despliega la siguiente ventana:

![](/resources/md/act1.png)

El usuario rellena el campo y si se actualiza el estado del recurso a Daño reparable o Daño total se muestra el formulario correspondiente con la opción de cancelar las reservas futuras asociadas:

![](/resources/md/act3.png)

Al terminar el proceso de actualización un mensaje de error o de exito es mostrado al usuario.

## Consultar

### Consultar los recursos disponibles

Al seleccionar esta opción el usuario irá a un página donde puede consultar los recursos disponibles según un filtro.

![](/resources/md/consultar.png)

### Consultar mis reservas

Al seleccionar esta opción el usuario irá a una página donde puede consultar sus revervas activas, pasadas y canceladas. Además podrá ampliar la información sobre una reserva especifica.

![](/resources/md/misreservas.png)
![](/resources/md/opc.png)
![](/resources/md/info.png)

### Consultar detalle reservas

El usuario al seleccionar una reserva desde el horario puede consultar el detalle de esta misma, la información mostrada varia según el rol del usuario.

![](/resources/md/info2.png)

### Cancelar Reservas 
Desde la página de consultar mis reservas el usuario debe seleccionar el boton (+) para luego escoger la opción cancelar, esto lo redirigirá a otra página dónde deberá confirmar la cancelación si la reserva es simple o completar los datos si es recurrente

![](/resources/md/general.png)
#### Mensaje de confirmación en reserva simple
![](/resources/md/confirmacion.png)
si el usuario presiona la opción "si" se cancelara la reserva simple  si no se cerrara el diálogo
#### Cancelar toda la reserva recurrente
![](/resources/md/cancelarTotal.png)
si el usuario presiona el boton cancelar cancelara la reserva y todos sus eventos
#### Cancelar reserva recurrentes desde una fecha
![](/resources/md/cancelarFechas.png)
el usuario debe seleccionar una fecha y al presionar cancelara todos los eventos despues de esta fecha , si todos los eventos se cancelan tambien se cancelara la reserva
#### Cancelar un evento de la reserva
![](/resources/md/canelarEvento.png)
el usuario debe seleccionar el evento que desea borrar en el calendario, si es el ultimo evento activo de la reserva esta sera cancelada
