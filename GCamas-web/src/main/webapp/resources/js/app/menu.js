$(document).ready(function() {
    $("#Datos").keydown(function(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
        }
    });
});
function expandFrame(panelID, frameID) {
    var panel = jQuery("#" + panelID);
    var frame = jQuery("#" + frameID);
    var fpos = frame.position();
    frame.height(panel.height() - fpos.top - 20);
    frame.width(panel.width() - 30);
}
function soloLetras(e) {
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = " áéíóúabcdefghijklmnñopqrstuvwxyz";
    especiales = [8, 37, 39, 46];
    tecla_especial = false;
    for (var i in especiales) {
        if (key === especiales[i]) {
            tecla_especial = true;
            break;
        }
    }
    if (letras.indexOf(tecla) === -1 && !tecla_especial)
        return false;
}
function handleComplete(xhr, status, args) {
    if (args.validationFailed) {
        alert("Error de Validacion");
    }
    else {
        cargarMantenedor(args.url2);
    }
}
function redirect(xhr, status, args) {
    if (args.validationFailed) {
        alert("Error de Validacion");
    }
    else {
        window.location=args.url2;
    }
}

function cargarMantenedor(url)
{
    $.ajax({
        url: url,
        async: true,
        success: function(data) {
            $('#content').html(data);
        }
    });
}

function volver(url) {
  console.log("hace algo");
        window.location.href = url;
      }


   