const express = require("express");
const session = require("express-session");
const expressPartials = require("express-partials");
const Keycloak = require("keycloak-connect");
const keycloakConfig = require("./keycloak.json");
const bodyParser = require('body-parser');
const app = express();
const port = 3000;

// Configuración de EJS como motor de plantillas
app.use(expressPartials());
app.set("views", "./views");
app.set("view engine", "ejs");

// Configura body-parser para analizar solicitudes con cuerpo JSON
app.use(bodyParser.json());

// Configura body-parser para analizar solicitudes con cuerpo de formulario
app.use(bodyParser.urlencoded({ extended: true }));

// Configura a partir del fichero public
app.use(express.static("public"));

// Configuración de la sesión de Express
const memoryStore = new session.MemoryStore();
app.use(
  session({
    secret: "asdawda3dbqlhbdwoabyo8dygy2o8dadiuoas",
    resave: false,
    saveUninitialized: true,
    store: memoryStore,
  })
);

// Configuración del adaptador de Keycloak
const keycloak = new Keycloak({ store: memoryStore });
app.use(keycloak.middleware());

// Importamos las rutas
var indexRouterProtected = require("./routes/index_protected");
var indexRouter = require("./routes/index");
app.get("/", indexRouter);
app.get("/*", keycloak.protect(), indexRouterProtected);
app.post("/*", keycloak.protect(), indexRouterProtected);

app.listen(port, () => {
  console.log(`Servidor escuchando en http://localhost:${port}`);
});
