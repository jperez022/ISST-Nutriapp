import { fileURLToPath } from "url";
import express from "express";
import multer from "multer";
import path from "path";
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
  chanfoto,
} from "./../controllers/mainController.js";

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
      path.join(projectDirectory, "..", "public", "images", "profilePics")
    );
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

router.get("/calculadora/:ing(\\d+)", calc3);

router.get("/calendario", calendario);

router.get("/calendario/:dia(\\d+)", dia);

router.get("/objetivos", objetivos);

router.get("/perfil", perfil);

router.post("/perfil/foto", upload.single("perfil"), chanfoto);

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
