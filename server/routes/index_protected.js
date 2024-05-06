import express from "express";
import { fileURLToPath } from "url";
import multer from "multer";
import path from "path";
import fs from "fs";

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

const fileName = "auxiliar.txt";

async function getk() {
  return new Promise((resolve, reject) => {
    fs.readFile(fileName, "utf8", (err, data) => {
      if (err) {
        console.error("Error al leer el archivo", err);
        reject(err);
        return;
      }
      const k = parseInt(data) || 1; // Si el archivo está vacío o no contiene un número válido, se usa 1 como valor predeterminado
      resolve(k);
    });
  });
}

async function incrementK() {
  try {
    let k = await getk();
    k++;
    await fs.promises.writeFile(fileName, k.toString());
    console.log(`Valor de k incrementado a ${k} y guardado en el archivo`);
  } catch (error) {
    console.error("Error al incrementar k y guardar en el archivo", error);
  }
}

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const projectDirectory = path.resolve(__dirname);

const storage = multer.diskStorage({
  destination: async function (req, file, cb) {
    cb(null, path.join(projectDirectory, "public", "images", "profilePics"));
  },
  filename: async function (req, file, cb) {
    const uniqueSuffix = await getk();
    await incrementK()
    cb(null, file.fieldname + "" + uniqueSuffix);
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

router.get("/plato", plato);

app.post("/perfil/foto", upload.single("perfil"), chanfoto);

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
