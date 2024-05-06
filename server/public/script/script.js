import axios from "axios";

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
  window.location.href = "http://localhost:3000/premium/educacion";
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

function platogen() {
  window.location.href = "http://localhost:3000/premium/plato/gen";
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

function specops() {
  window.location.href = "http://localhost:3000/premium/especialistas";
}

function cuenta() {
  window.location.href =
    " http://localhost:8080/realms/ISST/account/#/personal-info";
}

function segchan() {
  window.location.href = "http://localhost:3000/seg/mod";
}

function gradea(prog) {
  let gradientColor = 0;
  if (prog < 33) {
    gradientColor = `
    linear-gradient(to right,
      red 0%, 
      red 100%
    )
  `;
  } else {
    if (prog < 66) {
      gradientColor = `
        linear-gradient(to right,
          red 0%, 
          orange 100%
        )
      `;
    } else {
      gradientColor = `
          linear-gradient(to right,
            red 0%, 
            orange 50%, 
            lime 100% 
          )
        `;
    }
  }
  return gradientColor;
}

function ponlobonito(pini, pact, pobj) {
  let a = pact - pini;
  let b = pobj - pini;
  let c = (a * 100) / b;
  if (c > 100) {
    c = 100;
  }
  const progressBar = document.getElementById("prog_bar_peso");
  let gradiente = gradea(c);
  progressBar.style.width = `${c}%`;
  progressBar.style.background = gradiente;
}

function ponlobonito2(eact, eobj) {
  let c = (eact * 100) / eobj;
  if (c > 100) {
    c = 100;
  }
  const progressBar = document.getElementById("prog_bar_ej");
  let gradiente = gradea(c);
  progressBar.style.width = `${c}%`;
  progressBar.style.background = gradiente;
}

function ir_al_dia(dia, mes, año) {
  let url = "http://localhost:3000/calendario/0";
  if (año == 2024) {
    mes = mes + 1;
    url = "http://localhost:3000/calendario/" + dia + "1001" + mes;
  }
  window.location.href = url;
}

function changefot() {
  var brdDiv = document.querySelector(".brd");
  brdDiv.innerHTML = `
  <form class="todo" action="http://localhost:3000/perfil/foto" method="POST" enctype="multipart/form-data">
    <br>
    &nbsp;<input type="file" name="perfil" accept="image/*"> &nbsp;<br> <br>
    <div class="centraooo">
    <button class="but_perf" type="submit">Añadir</button>
    </div>
  </form> 
  `;
}

function comprarprem() {
  window.location.href = "http://localhost:3000/comprar/premium/";
}

function reunete() {
  window.location.href = "http://localhost:3000/specs/reunion/";
}

function verreun(dia, mes) {
  let url = "http://localhost:3000/premium/reunion/"+ dia + 1001 + mes;
  window.location.href = url ;
}

function realizar_calendario(usuario) {
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
  const manipulate = async () => {
    let dayone = new Date(year, month, 1).getDay();
    let lastdate = new Date(year, month + 1, 0).getDate();
    let dayend = new Date(year, month, lastdate).getDay();
    let monthlastdate = new Date(year, month, 0).getDate();
    let lit = "";
    for (let i = dayone; i > 0; i--) {
      lit += `<li class="inactive">${monthlastdate - i + 1}</li>`;
    }
    var fechas = [];
    var myJson;
    for (let i = 1; i <= lastdate; i++) {
      var http = "http://localhost:5000/calendario/dia/platos/" + usuario;
      await axios.get(http).then((response) => {
        myJson = response.data;
        for (let i = 0; i < myJson.length; i++) {
          fechas[i] = new Array(4);
          fechas[i][0] = "fecha";
          fechas[i][1] = myJson[i]["dia"];
          fechas[i][2] = myJson[i]["mes"];
          fechas[i][3] = 2024;
        }
      });
      var http = "http://localhost:5000/reunion/obtener";
      await axios.get(http).then((response) => {
        myJson = response.data;
        for (let i = 0; i < myJson.length; i++) {
          fechas[i] = new Array(4);
          fechas[i][0] = "reunion";
          fechas[i][1] = myJson[i]["dia"];
          fechas[i][2] = myJson[i]["mes"];
          fechas[i][3] = 2024;
        }
      });
      let isToday =
        i === date.getDate() &&
        month === new Date().getMonth() &&
        year === new Date().getFullYear()
          ? "active"
          : "";
      if (isToday != "active") {
        for (let fecha of fechas) {
          if (i === fecha[0] && month === fecha[1] - 1 && year === fecha[2]) {
            isToday = "hayplato";
            break;
          }
        }
      }
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

function realizar_calendarionoprem(usuario) {
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
  const manipulate = async () => {
    let dayone = new Date(year, month, 1).getDay();
    let lastdate = new Date(year, month + 1, 0).getDate();
    let dayend = new Date(year, month, lastdate).getDay();
    let monthlastdate = new Date(year, month, 0).getDate();
    let lit = "";
    for (let i = dayone; i > 0; i--) {
      lit += `<li class="inactive">${monthlastdate - i + 1}</li>`;
    }
    var fechas;
    var myJson;
    for (let i = 1; i <= lastdate; i++) {
      var http = "http://localhost:5000/calendario/dia/platos/" + usuario;
      await axios.get(http).then((response) => {
        myJson = response.data;
        fechas = new Array(myJson.length);
        for (let i = 0; i < myJson.length; i++) {
          fechas[i] = new Array(3);
          fechas[i][0] = myJson[i]["dia"];
          fechas[i][1] = myJson[i]["mes"];
          fechas[i][2] = 2024;
        }
      });
      let isToday =
        i === date.getDate() &&
        month === new Date().getMonth() &&
        year === new Date().getFullYear()
          ? "active"
          : "";
      if (isToday != "active") {
        for (let fecha of fechas) {
          if (i === fecha[0] && month === fecha[1] - 1 && year === fecha[2]) {
            isToday = "hayplato";
            break;
          }
        }
      }
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
