import express from "express";
import {index} from "./../controllers/mainController.js";

const router = express.Router();

router.get("/", index);

export default router;
