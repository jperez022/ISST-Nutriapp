var express = require("express");
var router = express.Router();
const Controller = require("../controllers/mainController");

router.get("/premium/educacion", Controller.educacion); // DEBERIA SER SOLO PARA PREMIUM // COMPLETAR

router.get("/premium/articulo/:id(\\d+)", Controller.artic); // DEBERIA SER SOLO PARA PREMIUM // COMPLETAR

router.post("/premium/especialistas", Controller.specops); // DEBERIA SER SOLO PARA PREMIUM // COMPLETAR

module.exports = router;