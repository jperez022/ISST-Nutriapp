var express = require("express");
var router = express.Router();
const Controller = require("../controllers/mainController");

router.get("/inicio", Controller.inicio);

router.get("/calculadora", Controller.calc);

router.get("/calculadoratotal", Controller.calc2);

router.get("/calendario", Controller.calendario);

router.get("/calendario/:dia(\\d+)", Controller.dia);

router.get("/educacion", Controller.educacion);

router.get("/objetivos", Controller.objetivos);

router.get("/perfil", Controller.perfil);

router.get("/plato", Controller.plato);

router.get("/plato/gen", Controller.platogen);

router.post("/plato/gen/fil", Controller.platogenfil);

router.get("/premium", Controller.premium);

router.get("/seguimiento", Controller.seg);

router.get("/seg/mod", Controller.segmod);

router.get("/logo", Controller.logout);

router.post("/calculadora/save", Controller.save);

router.post("/calculadora/save/plato", Controller.saveplat);

router.post("/calendario/preparacion", Controller.preparacion);

router.post("/obj/change", Controller.objchan);

router.post("/seg/save", Controller.segsav);

router.post("/seg/inisave", Controller.segsaveini);

router.post("/especialistas", Controller.specops)

module.exports = router;
