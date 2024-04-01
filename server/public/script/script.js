const axios = require("axios");

function login() {
  // Redirigir al servidor de autorización de Keycloak     
  window.location.href = "http://localhost:3000/inicio";
}

function calculadora() { 
  window.location.href = "http://localhost:3000/calculadora";
}

function calculadoratot() { 
  window.location.href = "http://localhost:3000/calculadoratotal";
}
  
function calendario() { 
  window.location.href = "http://localhost:3000/calendario";
}

function educacion() { 
  window.location.href = "http://localhost:3000/educacion";
}

function objetivos() { 
  window.location.href = "http://localhost:3000/objetivos";
}

function perfil() { 
  window.location.href = "http://localhost:3000/perfil";
}

function plato() { 
  window.location.href = "http://localhost:3000/plato";
}

function premium() { 
  window.location.href = "http://localhost:3000/premium";
}

function seg() { 
  window.location.href = "http://localhost:3000/seguimiento";
}

function logout() {
  window.location.href = "http://localhost:3000/logo";
}

function inicio() {
  var http = "http://localhost:5000/api/isst/nuevo_usuario/" + req.session.user;
  axios.get(http);
  window.location.href = "http://localhost:3000/calendario";
}