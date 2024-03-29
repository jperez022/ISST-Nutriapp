# ISST-NutriApp
Este es el github para el proyecto d ela aplicación NutriApp del grupo 18 de la asignatura ISST del grado en ingenieria de tecnologas y servicios de telecomunicación.


## PASOS A SEGUIR

- Al ejecutar el docker-compose por primera vez, **SOLO** poner la imagen de keycloak (ya que la del servicor habrá que modificarla posteriormente).
- Si se lanza en servidor externo **DESABILITAR SSL PETICIONES EXTERNAS** (explicación más abajo).
- Si se lanza en localhost (SOLO VA UBUNTU) modificar el fichero docker-compose.yml y en vez de esto, (ports:- "3000:3000") escribir (network_mode: "host").
- Una vez cargado entrar a http://localhost:8080.

> [!NOTE]
> Si el despliegue se realiza en el servidor externo entrar a http://IP:8080

- Iniciar sesión con las credenciales dadas en el fichero de docker-compose.
- Crear un realm nuevo y dentro de ese realm realizar las siguientes modificaciones.
- En Realm Settings poner "Require SSL" con el valor None.

- Crear un nuevo cliente activando “Client authentication” y establecer estos parametros:
    - Valid redirect URIs -> "http://localhost:3000/*" (así habilitamos poder acceder a las demas rutas).<br>
    - Web origins -> "http://localhost:3000" (asi solo se puede acceder a la pagina de logueo desde nuestra web).<br>

> [!NOTE]
> Si el despliegue se realiza en el servidor externo sustituir **localhost** por **LA IP**

- Una vez realizado esto seleccionamos nuestro cliente y le damos al boton que pone action. Luego le damos a donde pone “donwload adapter config” y copiamos todo el texto y lo pegamos en nuestra fichero keycloak.json (en caso de que no exista lo creamos dentro de la carpeta server) de forma que solo aparezca lo que hemos pegado.
- Crear un usuario y ponerle credenciales.
- Parar docker-compose e incluir en docker-compose.yml la imagen del servidor node.
- Levantar nuevamente docker-compose y ya solo se podrá acceder a la página logged mediante el usuario creado.

### DESHABILITAR SSL PETICIONES EXTERNAS
Ejecutar:

```bash
docker exec -it keycloak bash
cd opt/keycloak/bin
./kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin
./kcadm.sh update realms/master -s sslRequired=NONE
```
> [!NOTE]
> Si el contenedor de keycloak no tiene como nombre **keycloak** poner el nombre que tenga.<br>
> Da igual que se realice en el servidor externo o no. Poner **localhost**.

