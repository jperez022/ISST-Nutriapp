import express from "express";
import {
  inicio,
  calc,
  calc2,
  calendario,
  dia,
  objetivos,
  perfil,
  plato,
  platogen,
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
  segsaveini
} from "./../controllers/mainController.js";

const router = express.Router();

router.get("/inicio", inicio);

router.get("/calculadora", calc);

router.get("/calculadoratotal", calc2);

router.get("/calendario", calendario);

router.get("/calendario/:dia(\\d+)", dia);

router.get("/objetivos", objetivos);

router.get("/perfil", perfil);

// router.post("/perfil/foto", upload.single("perfil"), Controller.chanfoto); COMPLETAR NO VA

router.get("/plato", plato);

router.get("/plato/gen", platogen);

router.post("/plato/gen/fil", platogenfil);

router.get("/premium", premium);

router.get("/premium/comprar", premiumcom);

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
