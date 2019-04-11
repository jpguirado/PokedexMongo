window.onload = function(){
    // Get the modal
    var ventana = document.getElementById('ventana');
    // Get the button that opens the modal
    var boton = document.getElementById("boton");
    // When the user clicks the button, open the modal 
    boton.onclick = function () {
        ventana.style.display = "block";
    }
    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];
    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        ventana.style.display = "none";
    }
};

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
  if (event.target == ventana) {
    ventana.style.display = "none";
  }
}