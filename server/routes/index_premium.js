import express from "express";
import { educacion, artic, specops } from "./../controllers/mainController";

const router = express.Router();

router.get("/premium/educacion", educacion); // DEBERIA SER SOLO PARA PREMIUM // COMPLETAR

router.get("/premium/articulo/:id(\\d+)", artic); // DEBERIA SER SOLO PARA PREMIUM // COMPLETAR

router.get("/premium/especialistas", specops); // DEBERIA SER SOLO PARA PREMIUM // COMPLETAR

export default router;
