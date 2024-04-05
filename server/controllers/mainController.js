const keycloakConfig = require("./../keycloak.json");
const axios = require("axios");

async function acc(req) {
  if (!req.session.user) {
    try {
      var keycloakToken = JSON.parse(req.session["keycloak-token"]);
      var accessToken = keycloakToken.access_token;
      const usersResponse = await axios.get(
        keycloakConfig["auth-server-url"] +
          "/realms/" +
          keycloakConfig.realm +
          "/protocol/openid-connect/userinfo",
        {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      req.session.user = usersResponse.data.preferred_username;
      req.session.inicio_iniciado = false;
      req.session.calendario_created = false;
    } catch (error) {
      console.error("Hubo un problema al obtener el token de acceso:", error);
    }
  }
}

exports.inicio = async (req, res, next) => {
  await acc(req);
  if (!req.session.inicio_iniciado) {
    var http =
      "http://localhost:5000/api/isst/nuevo_usuario/" + req.session.user;
    axios.get(http);
    req.session.inicio_iniciado = true;
  }
  res.render("inicio", { layout: false });
};

exports.index = (req, res, next) => {
  res.render("index", { layout: false });
};

exports.calc = async (req, res, next) => {
  await acc(req);
  let lista = req.session.plato ? req.session.plato : false;
  let suma = req.session.total ? req.session.total : 0;
  res.render("calc", { layout: false, lista: lista, suma: suma });
};

exports.calc2 = async (req, res, next) => {
  await acc(req);
  let lista = req.session.plato ? req.session.plato : false;
  let suma = req.session.total ? req.session.total : 0;
  res.render("calc2", { layout: false, lista: lista, suma: suma });
};

exports.calendario = async (req, res, next) => {
  await acc(req);
  var myJson = "error";
  if (!req.session.calendario_created) {
    var http =
      "http://localhost:5000/api/isst/calendario/crear/" + req.session.user;
    await axios.get(http).then((response) => {
      myJson = response;
    });
    req.session.calendario_created = true;
  } else {
    myJson = "meni";
  }
  res.render("calendario", { myJson: myJson });
};

exports.dia = async (req, res, next) => {
  await acc(req);
  var dia = req.params.dia;
  var dia_mes = dia.split("1001");
  if (dia == 0) {
    dia_mes = "error";
  }
  var http =
    "http://localhost:5000/api/isst/calendario/dia/" +
    req.session.user +
    "/" +
    dia_mes[1] +
    "/" +
    dia_mes[0];
  await axios.get(http).then((response) => {
    myJson = response.data;
  });

  if (myJson["resp"] == "vacio") {
    res.render("dia_vac", { layout: false, dia_mes: dia_mes });
  } else {
    var aux = 0;
    var elems = [];
    for (let i = 0; i < Object.keys(myJson).length; i++) {
      elems[aux.toString()] = myJson[aux.toString()].toString();
      aux++;
    }
    res.render("dia", {
      layout: false,
      dia_mes: dia_mes,
      platos: elems,
    });
  }
};

exports.educacion = async (req, res, next) => {
  await acc(req);
  res.render("edu");
};

exports.objetivos = async (req, res, next) => {
  await acc(req);
  res.render("objetivos", { layout: false });
};

exports.perfil = async (req, res, next) => {
  await acc(req);
  var http =
    "http://34.175.19.24:5000/api/isst/obtener_objetivos/" + req.session.user;
  await axios
    .get(http)
    .then((response) => {
      myJson = response.data;
      peso_ini = myJson["peso_ini"].toString();
      peso_obj = myJson["peso_obj"].toString();
      peso_act = myJson["peso_act"].toString();
      ejer_act = myJson["ejercicio_act"].toString();
      ejer_obj = myJson["ejercicio_obj"].toString();
      peso = [peso_ini, peso_act, peso_obj];
      ejer = [ejer_act, ejer_obj];
    })
    .catch((error) => {
      peso = "error";
      ejer = "error";
    });
  res.render("perfil", {
    layout: false,
    user: req.session.user,
    peso: peso,
    ejer: ejer,
  });
};

exports.plato = async (req, res, next) => {
  await acc(req);
  res.render("plato");
};

exports.premium = async (req, res, next) => {
  await acc(req);
  res.render("premium");
};

exports.seg = async (req, res, next) => {
  await acc(req);
  res.render("seg", { layout: false });
};

exports.logout = (req, res, next) => {
  req.session.destroy();
  res.redirect(
    keycloakConfig["auth-server-url"] +
      "/realms/" +
      keycloakConfig.realm +
      "/protocol/openid-connect/logout"
  );
  // res.redirect("http://34.175.236.187:8080/realms/" + keycloakConfig.realm + "/protocol/openid-connect/logout");
  // PONER IP SI SE HACE EN SERVIDOR EXTERNO
};

exports.save = async (req, res, next) => {
  const ingrediente = req.body.ing;
  const descripcion = req.body.desc;
  const calorias = req.body.calo;

  if (ingrediente && calorias && !isNaN(calorias)) {
    let ing_overall = [ingrediente, descripcion, calorias];
    let plato = req.session.plato ? req.session.plato : [];
    plato.push(ing_overall);
    req.session.plato = plato;
    let tot = req.session.total
      ? parseInt(req.session.total) + parseInt(calorias)
      : calorias;
    req.session.total = tot;
  }

  res.redirect("/calculadora");
};

exports.saveplat = async (req, res, next) => {
  const nombre = req.body.nombre;
  const prep = req.body.prep;
  var fecha = req.body.fecha;
  const inges = req.session.plato;
  const calo = req.session.total;
  if (!fecha) {
    fecha = "no";
  }
  if (nombre && inges && calo) {
    let plato_global = [nombre, prep, fecha, inges, calo];
    delete req.session.plato;
    delete req.session.total;
    if (plato_global[1] == "") {
      plato_global[1] = "_";
    }
    if (plato_global[2] != "no") {
      var new_fecha = plato_global[2].split("-");
      var mi_dia = parseInt(new_fecha[2]);
      var mi_mes = parseInt(new_fecha[1]);
      plato_global[2] = mi_dia.toString() + "_" + mi_mes.toString();
    }
    var mis_ingredientes = "";
    var mis_descripciones = "";
    var mis_calorias = "";
    var mi_aux = "";
    for (let num = 0; num < plato_global[3].length; num++) {
      if (num == plato_global[3].length - 1) {
        mis_ingredientes =
          mis_ingredientes + plato_global[3][num][0].replace(" ", "_");
        mis_descripciones =
          mis_descripciones + plato_global[3][num][1].replace(" ", "_");
        mis_calorias =
          mis_calorias + plato_global[3][num][2].replace(" ", "_");
        mi_aux = mi_aux + "-";
      } else {
        mis_ingredientes =
          mis_ingredientes + plato_global[3][num][0].replace(" ", "_") + "-";
        mis_descripciones =
          mis_descripciones + plato_global[3][num][1].replace(" ", "_") + "-";
        mis_calorias =
          mis_calorias + plato_global[3][num][2].replace(" ", "_") + "-";
        mi_aux = mi_aux + "-";
      }
    }
    if (mis_descripciones == mi_aux) {
      mis_descripciones = "_";
    }
    req.session.plato_global = plato_global;
    var http =
      "http://localhost:5000/api/isst/agregar_plato/" +
      req.session.user +
      "/" +
      req.session.plato_global[0].replace(" ", "_") +
      "/" +
      req.session.plato_global[1] +
      "/" +
      mis_ingredientes +
      "/" +
      mis_descripciones +
      "/" +
      mis_calorias +
      "/" +
      req.session.plato_global[4] +
      "/" +
      req.session.plato_global[2];
    await axios.get(http);
  }
  res.redirect("/calendario");
};

exports.preparacion = async (req, res, next) => {
  let fecha = req.body.dia_mes;
  let plat = req.body.plat;
  plat = plat.split(',');
  if (!req.body.prep) {
    plat[1] = "_";
  } else {
    plat[1] = req.body.prep;
  }
  var http = 
    "http://34.175.19.24:5000/api/isst/modificar_plato/" + 
    req.session.user +
    "/" +
    plat[0] + 
    "/" + 
    plat[1] + 
    "/" + 
    plat[2].replace(' ','_').replace('/','-') + 
    "/" +
    plat[4].replace('/','-') + 
    "/" +
    plat[5] + 
    "/" + 
    fecha.split(',')[0] + "_" + fecha.split(',')[1];
  await axios.get(http);
  res.redirect("/calendario/" + fecha.split(',')[0] + "1001" + fecha.split(',')[1]);
}