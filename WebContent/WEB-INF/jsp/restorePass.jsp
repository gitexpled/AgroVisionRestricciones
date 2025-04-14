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

        if (nuevaClave !== confirmarClave) {
          mensajeDiv
            .text("Las contraseñas no coinciden.")
            .removeClass()
            .addClass("text-sm font-semibold text-red-600 text-center");
          return;
        }

        $.ajax({
          url: "/AgroVisionRestricciones/json/api/restorePassMail",
          method: "GET",
          data: {
            email,
            claveProvisoria,
            nuevaClave,
            confirmarClave,
          },
          success: function (data) {
            mensajeDiv
              .text(data.message)
              .removeClass()
              .addClass(
                "text-sm font-semibold text-center mt-2 " +
                  (data.status === "success"
                    ? "text-green-600"
                    : "text-red-600")
              );
          },
          error: function () {
            mensajeDiv
              .text("Error al conectar con el servidor.")
              .removeClass()
              .addClass("text-sm font-semibold text-red-600 text-center");
          },
        });
      });
    });
  </script>
</body>
</html>
