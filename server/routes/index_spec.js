import express from "express";
import { convreu, crearreu } from "./../controllers/mainController.js";

const router = express.Router();

router.get("/spec/reunion", convreu); 

router.post("/specs/conv", crearreu)

export default router;