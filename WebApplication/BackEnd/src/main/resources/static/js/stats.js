let scrollBtn;

window.onload = function() {
  scrollBtn = document.querySelector("#scrollBtn");

  window.onscroll = function() {scrollCheck()};
}
function scrollCheck() {
  if (window.scrollY > 400) {
    scrollBtn.style.display = "flex";
  } else {
    scrollBtn.style.display = "none";
  }
}

// When the user clicks on the button, scroll to the top of the document
function scrollToTop() {
  window.scrollTo({top: 0, behavior: 'smooth'});
}

function stats(id){
  location.href = `http://localhost:4200/event/${id}`;
}

// TODO: Rimuovere il codice sottostante se non necessario
function checkButton(){
  const user=localStorage.getItem("token")

  //const user = "utente"; // la variabile user potrebbe essere impostata dinamicamente in base all'autenticazione dell'utente

  const rightContent = document.querySelector(".right-section");
  if (user === "user") {

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    rightContent.appendChild(checkbox);

  } else if(user=="organizer")  {
    document.write(
        "<button class=\"right-button\"><span>Crea evento</span></button> "
    )
    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    rightContent.appendChild(checkbox);
  }else if(user=="admin"){

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    rightContent.appendChild(checkbox);

  }else{
    const button2 = document.createElement("button");
    button2.innerHTML = user+"-------";
    rightContent.appendChild(button2);
    document.write(
        "<button class=\"right-button\"><span>Accedi</span></button> " +
        "<button class=\"right-button\"><span>Registrati</span></button>"
    )
  }
}
