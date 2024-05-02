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

// NO TOCAR ESTA CUTREZ
let k = 0;

function getfot() {
  return k;
}

const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, "/public/images/profilePics/");
  },
  filename: function (req, file, cb) {
    const uniqueSuffix = getfot();
    k = k + 1;
    cb(null, file.fieldname + "-" + uniqueSuffix);
  },
});

const upload = multer({ storage: storage });

const router = express.Router();

router.get("/inicio", inicio);

router.get("/calculadora", calc);

router.get("/calculadoratotal", calc2);

router.get("/calendario", calendario);

router.get("/calendario/:dia(\\d+)", dia);

router.get("/objetivos", objetivos);

router.get("/perfil", perfil);

router.post("/perfil/foto", upload.single("perfil"), Controller.chanfoto); //TESTEAR COMPLETAR

router.get("/plato", plato);

router.get("/plato/gen", platogen);

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
