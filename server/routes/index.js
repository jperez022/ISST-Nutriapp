import express from "express";
import {index} from "./../controllers/mainController"

const router = express.Router();

router.get("/", index);

export default router;
