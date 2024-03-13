exports.index = (req, res, next) => {
  res.render("index", { layout: false });
};

exports.calc = (req, res, next) => {
  res.render("calc");
};

exports.calc2 = (req, res, next) => {
  res.render("calc2");
};

exports.calendario = (req, res, next) => {
  res.render("calendario");
};

exports.educacion = (req, res, next) => {
  res.render("edu");
};

exports.objetivos = (req, res, next) => {
  res.render("objetivos");
};

exports.perfil = (req, res, next) => {
  res.render("perfil");
};

exports.plato = (req, res, next) => {
  res.render("plato");
};

exports.premium = (req, res, next) => {
  res.render("premium");
};

exports.seg = (req, res, next) => {
  res.render("seg");
};
