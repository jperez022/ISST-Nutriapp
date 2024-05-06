import expressPartials from 'express-partials';
import Keycloak from 'keycloak-connect';
import session from "express-session";
import bodyParser from "body-parser";
import express from "express";
import { chanfoto } from './controllers/mainController.js';
import { fileURLToPath } from "url";
import multer from "multer";
import path from "path";

import proutes from "./routes/index_protected.js";
import premtes from "./routes/index_premium.js";
import broutes from "./routes/index.js";
import sroutes from "./routes/index_spec.js"

import { createRequire } from "module";
const require = createRequire(import.meta.url);
const keycloakConfig = require("./keycloak.json");


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
    secret: "misecretosuperseguronadiedebesaberlo", //DA IGUAL LO QUE APAREZCA AQUI
    resave: false,
    saveUninitialized: true,
    store: memoryStore,
  })
);

// Configuración del adaptador de Keycloak
const keycloak = new Keycloak({ store: memoryStore });
app.use(keycloak.middleware());


// NO TOCAR ESTA CUTREZ
let k = 1;

function getfot() {
  return k;
}

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const projectDirectory = path.resolve(__dirname);

const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(
      null,
      path.join(projectDirectory, "public", "images", "profilePics")
    );
  },
  filename: function (req, file, cb) {
    const uniqueSuffix = getfot();
    k = k + 1;
    cb(null, file.fieldname + "-" + uniqueSuffix);
  },
});

const upload = multer({ storage: storage });

app.post("/perfil/foto", upload.single("perfil"), chanfoto);
app.get("/", broutes);
app.get("/premium/*", keycloak.protect("premium"), premtes);
app.post("/premium/*", keycloak.protect("premium"), premtes);
app.get("/specs/*", keycloak.protect("specialist"), sroutes);
app.post("/specs/*", keycloak.protect("specialist"), sroutes);
app.get("/*", keycloak.protect(), proutes);
app.post("/*", keycloak.protect(), proutes);

app.listen(port, () => {
  console.log(`Servidor escuchando en http://localhost:${port}`);
});
