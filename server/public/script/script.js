const axios = require("axios");
const keycloakConfig = require("./../../keycloak.json");

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
  window.location.href = "http://localhost:3000/calendario";
}

function cuenta() {

  window.location.href = " http://localhost:8080/realms/ISST/account/#/personal-info";
}

function ir_al_dia(dia, mes, año) {
  let url = "http://localhost:3000/calendario/0";
  if (año == 2024) {
    mes = mes + 1;
    url = "http://localhost:3000/calendario/" + dia + "1001" + mes;
  }
  window.location.href = url;
}

function realizar_calendario() {
  let date = new Date();
  let year = date.getFullYear();
  let month = date.getMonth();
  const day = document.querySelector(".calendar-dates");
  const currdate = document.querySelector(".calendar-current-date");
  const prenexIcons = document.querySelectorAll(".calendar-navigation span");
  const months = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
  ];
  const manipulate = () => {
    let dayone = new Date(year, month, 1).getDay();
    let lastdate = new Date(year, month + 1, 0).getDate();
    let dayend = new Date(year, month, lastdate).getDay();
    let monthlastdate = new Date(year, month, 0).getDate();
    let lit = "";
    for (let i = dayone; i > 0; i--) {
      lit += `<li class="inactive">${monthlastdate - i + 1}</li>`;
    }
    for (let i = 1; i <= lastdate; i++) {
      let isToday =
        i === date.getDate() &&
        month === new Date().getMonth() &&
        year === new Date().getFullYear()
          ? "active"
          : "";
      lit += `<li class="${isToday}"><button class="botones-calendario" onclick="ir_al_dia(${i},${month},${year})">${i}</button></li>`;
    }
    for (let i = dayend; i < 6; i++) {
      lit += `<li class="inactive">${i - dayend + 1}</li>`;
    }
    currdate.innerText = `${months[month]} ${year}`;
    day.innerHTML = lit;
  };
  manipulate();
  prenexIcons.forEach((icon) => {
    icon.addEventListener("click", () => {
      month = icon.id === "calendar-prev" ? month - 1 : month + 1;
      if (month < 0 || month > 11) {
        date = new Date(year, month, new Date().getDate());
        year = date.getFullYear();
        month = date.getMonth();
      } else {
        date = new Date();
      }
      manipulate();
    });
  });
}
