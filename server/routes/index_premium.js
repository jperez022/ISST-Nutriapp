import express from "express";
import { educacion, specops, verreunion, platogen } from "./../controllers/mainController.js";

const router = express.Router();

router.get("/premium/educacion", educacion); 

router.get("/premium/especialistas", specops); 

router.get("/premium/reunion/:dia(\\d+)", verreunion);

router.get("/premium/plato/gen", platogen);

export default router;
