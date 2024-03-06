var express = require("express");
var router = express.Router();
const Controller = require("../controllers/mainController")

router.get("/", Controller.index);

module.exports = router