<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <title>Restablecer Contraseña</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body class="bg-gray-100 flex items-center justify-center min-h-screen">

  <div class="bg-white p-8 rounded shadow-md w-full max-w-md">
    <h2 class="text-2xl font-semibold text-center mb-6">Restablecer Contraseña</h2>
	<div id="loading" class="hidden text-center text-sm text-gray-500 font-medium">
	  Procesando...
	</div>
    <form id="resetForm" class="space-y-4">
      <input type="hidden" id="email" />
      
      <div>
        <label class="block text-sm font-medium text-gray-700">Clave Provisoria</label>
        <input
          type="text"
          id="claveProvisoria"
          required
          class="mt-1 block w-full px-4 py-2 border rounded-md shadow-sm focus:ring focus:ring-indigo-200"
        />
      </div>

      <div>
        <label class="block text-sm font-medium text-gray-700">Nueva Contraseña</label>
        <input
          type="password"
          id="nuevaClave"
          required
          class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring focus:ring-indigo-200"
        />
      </div>

      <div>
        <label class="block text-sm font-medium text-gray-700">Confirmar Contraseña</label>
        <input
          type="password"
          id="confirmarClave"
          required
          class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring focus:ring-indigo-200"
        />
      </div>

      <div id="mensaje" class="text-sm text-center font-medium mt-2"></div>

      <button
        type="submit"
        class="w-full bg-indigo-600 text-white py-2 rounded-md hover:bg-indigo-700 transition"
      >
        Restablecer
      </button>
    </form>
  </div>
	<div id="spinnerOverlay" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 hidden">
	  <div class="w-12 h-12 border-4 border-white border-t-transparent rounded-full animate-spin"></div>
	</div>
  <script>
    function getParam(name) {
      const urlParams = new URLSearchParams(window.location.search);
      return urlParams.get(name) || '';
    }

    $(document).ready(function () {
      // Cargar el email desde los parámetros de la URL
      const email = getParam("email");
      $("#email").val(email);

      $("#resetForm").on("submit", function (e) {
    	  e.preventDefault();

    	  const claveProvisoria = $("#claveProvisoria").val();
    	  const nuevaClave = $("#nuevaClave").val();
    	  const confirmarClave = $("#confirmarClave").val();
    	  const mensajeDiv = $("#mensaje");
    	  const spinnerOverlay = $("#spinnerOverlay");

    	  mensajeDiv.text("").removeClass();

    	  if (nuevaClave !== confirmarClave) {
    	    mensajeDiv
    	      .text("Las contraseñas no coinciden.")
    	      .removeClass()
    	      .addClass("text-sm font-semibold text-red-600 text-center");
    	    return;
    	  }
    	  spinnerOverlay.removeClass("hidden");
    	  $.ajax({
    	    url: "/AgroVisionRestricciones/json/api/restorePassMail",
    	    method: "GET",
    	    data: {
    	      email: $("#email").val(),
    	      claveProvisoria,
    	      nuevaClave,
    	      confirmarClave,
    	    },
    	    success: function (data) {
    	      const colorClass = data.estado === "OK" ? "text-green-600" : "text-red-600";

    	      mensajeDiv
    	        .text(data.mensaje)
    	        .removeClass()
    	        .addClass("text-sm font-semibold text-center mt-2 " + colorClass);

    	      if (data.estado === "OK") {
    	        $("#claveProvisoria, #nuevaClave, #confirmarClave").val("");
    	      }
    	    },
    	    error: function () {
    	      mensajeDiv
    	        .text("Error al conectar con el servidor.")
    	        .removeClass()
    	        .addClass("text-sm font-semibold text-red-600 text-center");
    	    },
    	    complete: function () {
    	      spinnerOverlay.addClass("hidden");
    	    },
    	  });
    	});
    });
  </script>
</body>
</html>
