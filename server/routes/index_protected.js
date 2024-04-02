var express = require("express");
var router = express.Router();
const Controller = require("../controllers/mainController")

router.get("/inicio", Controller.inicio);

router.get("/calculadora", Controller.calc);

router.get("/calculadoratotal", Controller.calc2);

router.get("/calendario", Controller.calendario);

router.get("/calendario/:dia(\\d+)", Controller.dia);

router.get("/educacion", Controller.educacion);

router.get("/objetivos", Controller.objetivos);

router.get("/perfil", Controller.perfil);

router.get("/plato", Controller.plato);

router.get("/premium", Controller.premium);

router.get("/seguimiento", Controller.seg);

router.get("/logo", Controller.logout);

module.exports = router