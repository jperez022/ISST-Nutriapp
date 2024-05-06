import express from "express";


import {
  inicio,
  calc,
  calc2,
  calc3,
  calendario,
  dia,
  objetivos,
  perfil,
  plato,
  platogenfil,
  premium,
  premiumcom,
  seg,
  segmod,
  logout,
  save,
  saveplat,
  preparacion,
  objchan,
  segsav,
  segsaveini,
} from "./../controllers/mainController.js";

const router = express.Router();

router.get("/inicio", inicio);

router.get("/calculadora", calc);

router.get("/calculadoratotal", calc2);

router.get("/calculadora/:ing(\\d+)", calc3);

router.get("/calendario", calendario);

router.get("/calendario/:dia(\\d+)", dia);

router.get("/objetivos", objetivos);

router.get("/perfil", perfil);

router.get("/plato", plato);

router.post("/plato/gen/fil", platogenfil);

router.get("/premium", premium);

router.get("/comprar/premium", premiumcom);

router.get("/seguimiento", seg);

router.get("/seg/mod", segmod);

router.get("/logo", logout);

router.post("/calculadora/save", save);

router.post("/calculadora/save/plato", saveplat);

router.post("/calendario/preparacion", preparacion);

router.post("/obj/change", objchan);

router.post("/seg/save", segsav);

router.post("/seg/inisave", segsaveini);

export default router;
