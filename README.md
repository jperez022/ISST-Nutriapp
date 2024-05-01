# ISST-NutriApp
Este es el github para el proyecto de la aplicación NutriApp del grupo 18 de la asignatura ISST del grado en Ingeniería de Tecnologías y Servicios de Telecomunicación.


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

- Crear un nuevo cliente activando “Client authentication” y "Service accounts roles" y establecer estos parametros:
    - Valid redirect URIs -> "http://localhost:3000/*" (así habilitamos poder acceder a las demas rutas).<br>
    - Web origins -> "http://localhost:3000" (asi solo se puede acceder a la pagina de logueo desde nuestra web).<br>

> [!NOTE]
> Si el despliegue se realiza en el servidor externo sustituir **localhost** por **LA IP**.

- Una vez realizado esto seleccionamos nuestro cliente y le damos al boton que pone action. Luego le damos a donde pone “donwload adapter config” y copiamos todo el texto y lo pegamos en nuestra fichero keycloak.json (en caso de que no exista lo creamos dentro de la carpeta server) de forma que solo aparezca lo que hemos pegado. Poner "verify-token-audience": false,
- Crear un usuario y ponerle credenciales.
- Parar docker-compose e incluir en docker-compose.yml la imagen del servidor node.
- Levantar nuevamente docker-compose y ya solo se podrá acceder a la página logged mediante el usuario creado.

> [!NOTE]
> Si el despliegue se realiza en el servidor externo utilizar el fichero **script.py** para modificar **script.js**.
> Para más información ejecutar el fichero seguido de la palabra help (python3 script.py help).

### INICIALIZAR BASES DE DATOS

Previamente a la inicialización habrá que instalar mysql en caso de que no este instalado previamente donde alojemos el servicio. En caso de desplegarlo en un sistema que utilice Debian 10 los pasos a seguir son los siguientes:

- wget https://dev.mysql.com/get/mysql-apt-config_0.8.29-1_all.deb
- sudo dpkg -i ./mysql-apt-config_0.8.29-1_all.deb
- Seleccionar la opción OK en el menú que se despliegue.
- sudo apt update
- sudo apt install mysql-server
- Introducir la contraseña que se quiera y seleccionar posteriormente la opción "Use Strong Password Encryption".

Para poder inicializar las bases de datos hay que ejecutar los siguientes comandos si el entorno en el que se corre es gestionado externamente (por ejemplo si usamos los servicios de Google Cloud para desplegar nuestro servicio):

- apt install python3.11-venv
- python3 -m venv .venv
- source .venv/bin/activate
- python3 -m pip install -r requirements.txt

En el resto de casos sirve con correr el comando:

- pip install -r requirements.txt

### INICIALIZAR SPRING BOOT

Para poder desplegar el servicio de nuestra API REST utilizando Spring Boot deberemos instalar tanto Java como Maven.

Para instalar la version 17 de Java (mínima requerida) seguiremos los siguientes pasos situados en el directorio /home/(usuario):

- wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.deb
- sudo dpkg -i jdk-17_linux-x64_bin.deb
- nano .bashrc (escribir dentro la linea export PATH=/usr/lib/jvm/jdk-17/bin:$PATH)
- source .bashrc
- java --version (para comprobar su correcta instalación)

Para instalar maven usaremos los siguientes comandos:

- wget https://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
- tar -xvf apache-maven-3.6.3-bin.tar.gz
- mv apache-maven-3.6.3 /opt/
- nano .profile

Dentro del documento .profile escribiremos las siguientes líneas:

M2_HOME='/opt/apache-maven-3.6.3'
PATH="$M2_HOME/bin:$PATH"
export PATH

- source .profile
- mvn --version (para comprobar su correcta instalación)

Hay que recordar que para poder usar Spring Boot debemos tener creada la BBDD a la que nos referimos en application.properties.
En nuestro caso la BBDD se llama isst2.

Por último para lanzar el servidor usaremos: 

- mvn spring-boot:run

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

### FICHEROS DONDE CAMBIAR IP

- server/controller/mainController.js
- server/public/script/scrip.js
- server/views/calc.ejs
- server/views/calc2.ejs
- server/views/dia.ejs
- server/views/seg.ejs
- server/views/segmodif.ejs
- server/views/segini.ejs
- server/views/platogen.ejs

### INFO ROLES

Asignar en service account roles los siguientes roles
- premium
- query-clients
- query-users
- manage-users
- manage-clients
- view-users
- view-clients

> [!NOTE]
> Modificar keycloak.json asi "verify-token-audience": false,
