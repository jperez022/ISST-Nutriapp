const keycloakConfig = require("./../keycloak.json");
const axios = require("axios");

async function acc(req) {
  if (!req.session.user) {
    try {
      var keycloakToken = JSON.parse(req.session["keycloak-token"]);
      var accessToken = keycloakToken.access_token;
      const usersResponse = await axios.get(
        keycloakConfig["auth-server-url"]+"/realms/" +
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
    } catch (error) {
      console.error("Hubo un problema al obtener el token de acceso:", error);
    }
  }
}

exports.inicio = async (req, res, next) => {
  await acc(req);
  var http = "http://localhost:5000/api/isst/nuevo_usuario/" + req.session.user;
  axios.get(http);
  res.render("inicio", { layout: false});
}

exports.index = (req, res, next) => {
  res.render("index", { layout: false });
};

exports.calc = async (req, res, next) => {
  await acc(req);
  res.render("calc");
};

exports.calc2 = async (req, res, next) => {
  await acc(req);
  res.render("calc2");
};

exports.calendario = async (req, res, next) => {
  await acc(req);
  var myJson = "error";
  var http = "http://34.175.4.111:5000/api/isst/calendario/crear/" + req.session.user;
  axios.get(http).then((response) => myJson = response);
  res.render("calendario", {myJson: myJson});
};

exports.educacion = async (req, res, next) => {
  await acc(req);
  res.render("edu");
};

exports.objetivos = async (req, res, next) => {
  await acc(req);
  res.render("objetivos");
};

exports.perfil = async (req, res, next) => {
  await acc(req);
  res.render("perfil");
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
  res.render("seg");
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
