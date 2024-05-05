import express from "express";
import { educacion, specops, verreunion } from "./../controllers/mainController.js";

const router = express.Router();

router.get("/premium/educacion", educacion); // DEBERIA SER SOLO PARA PREMIUM // COMPLETAR

router.get("/premium/especialistas", specops); // DEBERIA SER SOLO PARA PREMIUM // COMPLETAR

router.get("/premium/reunion/:dia(\\d+)", verreunion);

export default router;
