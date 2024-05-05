import KcAdminClient from "@keycloak/keycloak-admin-client";
import axios from "axios";

import { createRequire } from "module";
const require = createRequire(import.meta.url);
const keycloakConfig = require("./../keycloak.json");

const configOptions = {
  baseUrl: keycloakConfig["auth-server-url"],
  realmName: keycloakConfig.realm,
};

const kcAdminClient = new KcAdminClient(configOptions);

async function givprem(req) {
  try {
    await kcAdminClient.auth({
      grantType: "client_credentials",
      clientId: keycloakConfig.resource,
      clientSecret: keycloakConfig.credentials.secret,
    });

    let usuario = await kcAdminClient.users.findOne({
      username: req.session.user,
    });
    let client = await kcAdminClient.clients.findOne({
      clientId: keycloakConfig.resource,
    });
    let role = await kcAdminClient.clients.findRole({
      id: client[0].id,
      roleName: "premium",
    });

    await kcAdminClient.users.addClientRoleMappings({
      id: usuario[0].id,
      clientUniqueId: client[0].id,
      roles: [
        {
          id: role.id,
          name: role.name,
        },
      ],
    });
    var http =
      "http://localhost:5000/usuario/premium/nuevo/" + req.session.user;
    axios.get(http);
    console.log(`Rol "premium" asignado al usuario ${req.session.user}`);
  } catch (error) {
    console.error('Error al asignar el rol "premium" al usuario:', error);
  }
}

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
  if (!req.session.isprem || req.session.isprem == false) {
    let isprem = null;
    var http = "http://localhost:5000/usuario/premium/" + req.session.user;
    await axios.get(http).then((response) => {
      isprem = response.data["resp"];
    });
    req.session.isprem = isprem;
  }
  if (!req.session.isspec || req.session.isspec == false) {
    let isspec = null;
    var http = "http://localhost:5000/usuario/especialista/" + req.session.user;
    await axios.get(http).then((response) => {
      isspec = response.data["resp"];
    });
    req.session.isspec = isspec;
  }
}

export const inicio = async (req, res, next) => {
  await acc(req);
  if (!req.session.inicio_iniciado) {
    var http = "http://localhost:5000/usuario/nuevo/" + req.session.user;
    axios.get(http);
    req.session.inicio_iniciado = true;
  }
  res.render("inicio", { layout: false, user: req.session.user });
};

export const index = (req, res, next) => {
  res.render("index", { layout: false });
};

export const calc = async (req, res, next) => {
  await acc(req);
  let lista = req.session.plato ? req.session.plato : false;
  let suma = req.session.total ? req.session.total : 0;
  res.render("calc", { layout: false, lista: lista, suma: suma });
};

export const calc2 = async (req, res, next) => {
  await acc(req);
  let lista = req.session.plato ? req.session.plato : false;
  let suma = req.session.total ? req.session.total : 0;
  res.render("calc2", { layout: false, lista: lista, suma: suma });
};

export const calendario = async (req, res, next) => {
  await acc(req);
  var myJson = "error";
  if (!req.session.calendario_created) {
    var http = "http://localhost:5000/calendario/crear/" + req.session.user;
    await axios.get(http).then((response) => {
      myJson = response;
    });
    req.session.calendario_created = true;
  } else {
    myJson = "meni";
  }
  res.render("calendario", { myJson: myJson, isprem: req.session.isprem });
};

export const dia = async (req, res, next) => {
  var myJson;
  await acc(req);
  var dia = req.params.dia;
  var dia_mes = dia.split("1001");
  if (dia == 0) {
    dia_mes = "error";
  }
  var http =
    "http://localhost:5000/calendario/dia/" +
    req.session.user +
    "/" +
    dia_mes[1] +
    "/" +
    dia_mes[0];
  await axios.get(http).then((response) => {
    myJson = response.data;
  });
  // COMPLETAR
  // HACER LLAMADA A LA API PARA VER SI HAY REUNION ESE DIA
  // PASAR hayreunion como true o como false
  if (!myJson[0]) {
    res.render("dia_vac", {
      layout: false,
      dia_mes: dia_mes,
      isprem: req.session.isprem,
      isspec: req.session.isspec,
      hayreunion: null,
    });
  } else {
    res.render("dia", {
      layout: false,
      dia_mes: dia_mes,
      platos: myJson,
      isprem: req.session.isprem,
      isspec: req.session.isspec,
      hayreunion: null,
    });
  }
};

export const educacion = async (req, res, next) => {
  await acc(req);
  var myJson;
  var articulos;
  var http = "http://localhost:5000/articulo/obtener";
  await axios.get(http).then((response) => {
    myJson = response.data;
    articulos = new Array(myJson.length);
    for (let i = 0; i < myJson.length; i++) {
      articulos[i] = new Array(3);
      articulos[i][0] = myJson[i]["titulo"];
      articulos[i][1] = myJson[i]["descripcion"];
      articulos[i][2] = myJson[i]["url"];
    }
  });
  res.render("edu", { layout: false, articulos: articulos });
};

export const objetivos = async (req, res, next) => {
  await acc(req);
  res.render("objetivos", { layout: false });
};

export const perfil = async (req, res, next) => {
  await acc(req);
  var peso;
  var ejer;
  var fotico;
  var myJson;
  var peso_ini;
  var peso_obj;
  var peso_act;
  var ejer_act;
  var ejer_obj;
  var http =
    "http://localhost:5000/usuario/obtener_objetivos/" + req.session.user;
  await axios
    .get(http)
    .then((response) => {
      myJson = response.data;
      peso_ini = myJson["peso_ini"].toString();
      peso_obj = myJson["peso_obj"].toString();
      peso_act = myJson["peso_act"].toString();
      ejer_act = myJson["ejercicio_act"].toString();
      ejer_obj = myJson["ejercicio_obj"].toString();
      if ((ejer_obj % 60).toString().length == 1) {
        ejer_obj = Math.round(ejer_obj / 60) + ":0" + (ejer_obj % 60);
      } else {
        ejer_obj = Math.round(ejer_obj / 60) + ":" + (ejer_obj % 60);
      }
      if (ejer_obj.length == 4) {
        ejer_obj = "0" + ejer_obj;
      }
      if ((ejer_act % 60).toString().length == 1) {
        ejer_act = Math.round(ejer_act / 60) + ":0" + (ejer_act % 60);
      } else {
        ejer_act = Math.round(ejer_act / 60) + ":" + (ejer_act % 60);
      }
      if (ejer_act.length == 4) {
        ejer_act = "0" + ejer_act;
      }
      peso = [peso_ini, peso_act, peso_obj];
      ejer = [ejer_act, ejer_obj];
    })
    .catch((error) => {
      peso = "error";
      ejer = "error";
    });
  // API FALTA PASAR FOTO EL ID DE LA FOTO A LA VISTA
  // COMPLETAR
  res.render("perfil", {
    layout: false,
    foto: fotico,
    user: req.session.user,
    peso: peso,
    ejer: ejer,
  });
};

export const chanfoto = async (req, res, next) => {
  if (!req.file) {
    return res.status(400).send("No se ha seleccionado ninguna imagen.");
  } else {
    res.redirect("/perfil");
    // TESTEAR FUNCIONAMIENTO
  }
};

export const plato = async (req, res, next) => {
  await acc(req);
  var myJson;
  var http = "http://localhost:5000/plato/obtener/" + req.session.user;
  await axios.get(http).then((response) => {
    myJson = response.data;
  });
  if (!myJson[0]) {
    res.render("plato", { layout: false, platos: "no" });
  } else {
    res.render("plato", {
      layout: false,
      platos: myJson,
    });
  }
};

export const platogen = async (req, res, next) => {
  await acc(req);
  var myJson;
  var http = "http://localhost:5000/plato/obtenerSugerido/no";
  await axios.get(http).then((response) => {
    myJson = response.data;
  });
  res.render("platogen", { layout: false, platos: myJson, calorias: null });
};

export const platogenfil = async (req, res, next) => {
  await acc(req);
  var myJson;
  let caloriasmin = req.body.calomin;
  let caloriasmax = req.body.calomax;
  let calorias = [caloriasmin, caloriasmax];
  let mis_calorias = caloriasmin + "_" + caloriasmax;
  var http = "http://localhost:5000/plato/obtenerSugerido/" + mis_calorias;
  await axios.get(http).then((response) => {
    myJson = response.data;
  });
  res.render("platogen", { layout: false, platos: myJson, calorias: calorias });
};

export const premium = async (req, res, next) => {
  await acc(req);

  res.render("premium", { layout: false, isprem: req.session.isprem });
};

export const premiumcom = async (req, res, next) => {
  await acc(req);
  await givprem(req);
  res.redirect("/calendario");
};

export const seg = async (req, res, next) => {
  await acc(req);
  var peso;
  var ejer;
  var myJson;
  var mi_error = "nohay";
  var peso_ini;
  var peso_obj;
  var peso_act;
  var ejer_act;
  var ejer_obj;
  var http =
    "http://localhost:5000/usuario/obtener_objetivos/" + req.session.user;
  await axios
    .get(http)
    .then((response) => {
      myJson = response.data;
      peso_ini = myJson["peso_ini"].toString();
      peso_obj = myJson["peso_obj"].toString();
      peso_act = myJson["peso_act"].toString();
      ejer_act = myJson["ejercicio_act"].toString();
      ejer_obj = myJson["ejercicio_obj"].toString();
      if ((ejer_obj % 60).toString().length == 1) {
        ejer_obj = Math.round(ejer_obj / 60) + ":0" + (ejer_obj % 60);
      } else {
        ejer_obj = Math.round(ejer_obj / 60) + ":" + (ejer_obj % 60);
      }
      if (ejer_obj.length == 4) {
        ejer_obj = "0" + ejer_obj;
      }
      if ((ejer_act % 60).toString().length == 1) {
        ejer_act = Math.round(ejer_act / 60) + ":0" + (ejer_act % 60);
      } else {
        ejer_act = Math.round(ejer_act / 60) + ":" + (ejer_act % 60);
      }
      if (ejer_act.length == 4) {
        ejer_act = "0" + ejer_act;
      }
      peso = [peso_ini, peso_act, peso_obj];
      ejer = [ejer_act, ejer_obj];
    })
    .catch((error) => {
      mi_error = error;
      res.render("segini", { layout: false });
    });
  if (mi_error == "nohay") {
    res.render("seg", { layout: false, peso: peso, ejer: ejer });
  }
};

export const logout = (req, res, next) => {
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

export const save = async (req, res, next) => {
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

export const saveplat = async (req, res, next) => {
  const nombre = req.body.nombre;
  const prep = req.body.prep;
  var fecha = req.body.fecha;
  const inges = req.session.plato;
  const calo = req.session.total;
  if (!fecha) {
    res.redirect("/calculadoratotal");
  }
  if (nombre && inges && calo && fecha) {
    let plato_global = [nombre, prep, fecha, inges, calo];
    delete req.session.plato;
    delete req.session.total;
    if (plato_global[1] == "") {
      plato_global[1] = "_";
    }
    var new_fecha = plato_global[2].split("-");
    var mi_dia = parseInt(new_fecha[2]);
    var mi_mes = parseInt(new_fecha[1]);
    plato_global[2] = mi_dia.toString() + "_" + mi_mes.toString();
    var mis_ingredientes = "";
    var mis_descripciones = "";
    var mis_calorias = "";
    var mi_aux = "";
    for (let num = 0; num < plato_global[3].length; num++) {
      if (num == plato_global[3].length - 1) {
        mis_ingredientes = mis_ingredientes + plato_global[3][num][0];
        mis_descripciones = mis_descripciones + plato_global[3][num][1];
        mis_calorias = mis_calorias + plato_global[3][num][2];
      } else {
        mis_ingredientes = mis_ingredientes + plato_global[3][num][0] + "-";
        mis_descripciones = mis_descripciones + plato_global[3][num][1] + "-";
        mis_calorias = mis_calorias + plato_global[3][num][2] + "-";
        mi_aux = mi_aux + "-";
      }
    }
    if (mis_descripciones == mi_aux) {
      mis_descripciones = "_";
    }
    req.session.plato_global = plato_global;
    var http = "http://localhost:5000/plato/agregar";
    await axios.post(http, {
      usuario: req.session.user,
      nombre: req.session.plato_global[0],
      preparacion: req.session.plato_global[1],
      ingredientes: mis_ingredientes,
      descripcion: mis_descripciones,
      calorias: mis_calorias,
      calorias_total: req.session.plato_global[4],
      dia_mes: req.session.plato_global[2],
    });
  }
  res.redirect("/calendario");
};

export const preparacion = async (req, res, next) => {
  let fecha = req.body.dia_mes;
  let plat = req.body.plat;
  plat = plat.split(",");
  if (!req.body.prep) {
    plat[1] = "_";
  } else {
    plat[1] = req.body.prep;
  }
  var http = "http://localhost:5000/plato/modificar";
  await axios.post(http, {
    usuario: req.session.user,
    nombre: plat[0],
    preparacion: plat[1],
    ingredientes: plat[2].replaceAll("/", "-"),
    descripcion: plat[3].replaceAll("/", "-"),
    calorias: plat[4].replaceAll("/", "-"),
    calorias_total: plat[5],
    dia_mes: fecha.split(",")[0] + "_" + fecha.split(",")[1],
  });
  res.redirect(
    "/calendario/" + fecha.split(",")[0] + "1001" + fecha.split(",")[1]
  );
};

export const segmod = async (req, res, next) => {
  var peso;
  var ejer;
  var myJson;
  var peso_ini;
  var peso_obj;
  var peso_act;
  var ejer_act;
  var ejer_obj;
  var http =
    "http://localhost:5000/usuario/obtener_objetivos/" + req.session.user;
  await axios
    .get(http)
    .then((response) => {
      myJson = response.data;
      peso_ini = myJson["peso_ini"].toString();
      peso_obj = myJson["peso_obj"].toString();
      peso_act = myJson["peso_act"].toString();
      ejer_act = myJson["ejercicio_act"].toString();
      ejer_obj = myJson["ejercicio_obj"].toString();
      if ((ejer_obj % 60).toString().length == 1) {
        ejer_obj = Math.round(ejer_obj / 60) + ":0" + (ejer_obj % 60);
      } else {
        ejer_obj = Math.round(ejer_obj / 60) + ":" + (ejer_obj % 60);
      }
      if (ejer_obj.length == 4) {
        ejer_obj = "0" + ejer_obj;
      }
      if ((ejer_act % 60).toString().length == 1) {
        ejer_act = Math.round(ejer_act / 60) + ":0" + (ejer_act % 60);
      } else {
        ejer_act = Math.round(ejer_act / 60) + ":" + (ejer_act % 60);
      }
      if (ejer_act.length == 4) {
        ejer_act = "0" + ejer_act;
      }
      peso = [peso_ini, peso_act, peso_obj];
      ejer = [ejer_act, ejer_obj];
    })
    .catch((error) => {
      peso = "error";
      ejer = "error";
    });
  res.render("segmodif", { layout: false, peso: peso, ejer: ejer });
};

export const objchan = async (req, res, next) => {
  let peso_obj = req.body.valor;
  let ejer_obj = req.body.cantidad;
  let peso_ini = req.body.peso_ini;
  let peso_act = req.body.peso_act;
  let ejer_act = req.body.ejer_act;
  ejer_obj = ejer_obj.split(":");
  ejer_obj = +ejer_obj[0] * 60 + parseInt(ejer_obj[1]);
  ejer_act = ejer_act.split(":");
  ejer_act = +ejer_act[0] * 60 + parseInt(ejer_act[1]);
  let peso = [peso_ini, peso_act, peso_obj];
  let ejer = [ejer_act, ejer_obj];
  peso = peso.join("-");
  ejer = ejer.join("-");
  var http =
    "http://localhost:5000/usuario/crear_objetivos/" +
    req.session.user +
    "/" +
    peso +
    "/" +
    ejer;
  await axios.get(http);
  //Realizar llamada a la api para guardarlos
  res.redirect("/seguimiento");
};

export const segsav = async (req, res, next) => {
  let peso_act = req.body.peso;
  let ejer_act = req.body.tiempo;
  let peso_ini = req.body.peso_ini;
  let peso_obj = req.body.peso_obj;
  let ejer_obj = req.body.ejer_obj;
  ejer_obj = ejer_obj.split(":");
  ejer_obj = +ejer_obj[0] * 60 + parseInt(ejer_obj[1]);
  ejer_act = ejer_act.split(":");
  ejer_act = +ejer_act[0] * 60 + parseInt(ejer_act[1]);
  let peso = [peso_ini, peso_act, peso_obj];
  let ejer = [ejer_act, ejer_obj];
  peso = peso.join("-");
  ejer = ejer.join("-");
  var http =
    "http://localhost:5000/usuario/crear_objetivos/" +
    req.session.user +
    "/" +
    peso +
    "/" +
    ejer;
  await axios.get(http);
  // El tiempo lo guardaba en minutos
  //Realizar llamada a la api para guardarlos
  res.redirect("/seguimiento");
};

export const segsaveini = async (req, res, next) => {
  let peso_ini = req.body.pesoini;
  let peso_act = req.body.peso;
  let peso_obj = req.body.pesojb;
  let ejer_act = "0:00";
  let ejer_obj = req.body.cantidad;
  ejer_obj = ejer_obj.split(":");
  ejer_obj = +ejer_obj[0] * 60 + parseInt(ejer_obj[1]);
  ejer_act = ejer_act.split(":");
  ejer_act = +ejer_act[0] * 60 + parseInt(ejer_act[1]);
  let peso = [peso_ini, peso_act, peso_obj];
  let ejer = [ejer_act, ejer_obj];
  peso = peso.join("-");
  ejer = ejer.join("-");
  var http =
    "http://localhost:5000/usuario/crear_objetivos/" +
    req.session.user +
    "/" +
    peso +
    "/" +
    ejer;
  await axios.get(http);
  res.redirect("/seguimiento");
};

export const specops = async (req, res, next) => {
  var myJson;
  var lista_spec;
  var http = "http://localhost:5000/especialista/obtener";
  await axios.get(http).then((response) => {
    myJson = response.data;
    lista_spec = new Array(myJson.length);
    for (let i = 0; i < myJson.length; i++) {
      lista_spec[i] = new Array(5);
      lista_spec[i][0] = myJson[i]["nombre"];
      lista_spec[i][1] = myJson[i]["info"];
      lista_spec[i][2] = myJson[i]["valoracion"];
      lista_spec[i][3] = myJson[i]["movil"];
      lista_spec[i][4] = myJson[i]["precio"];
    }
  });
  // EJEMPLO
  // lista_spec[1] = [Nombre, info, valoracion en numero de 0 a 100, numero, precio, idfoto]
  // AUN FALTA CAMBIAR FOTO EN LA VISTA
  res.render("specopc", { layout: false, lista_spec: lista_spec });
};

export const convreu = async (req, res, next) => {
  res.render("reunion", { layout: false });
};

export const crearreu = async (req, res, next) => {
  let titulo = req.body.nombre;
  let link = req.body.link;
  let fecha = req.body.fecha;
  let hora = req.body.hora;
  let autor = req.session.user;
  // COMPLETAR
  // HACER LLAMADA A LA API PARA GUARDAR REUNION
  res.redirect("/calendario");
};

export const verreunion = async (req, res, next) => {
  // COMPLETAR
  // HACER LLAMADA A LA API PARA TENER LOS DATOS DE LA REUNION DE ESTE DIA
  // QUE REUNION SEA UN ARRAY Y SEA ASI
  // reunion = [titulo, link, fecha, hora]
  // autor = [autor, foto autor, valoracion (pasala como en perfil)]
  let dia = req.params.dia; // AQUI TIENES LA FECHA PARA VER SI HAY REUNION O NO
  let reunion = null;
  let autor = null;
  if (reunion) {
    res.render("reunver", { layout: false, reunion: reunion, autor: autor });
  } else {
    res.render("reunnover", { layout: false });
  }
};
