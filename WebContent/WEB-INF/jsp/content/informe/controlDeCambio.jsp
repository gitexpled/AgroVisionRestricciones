<style>
  input[type="date"]::-webkit-calendar-picker-indicator {
    filter: invert(0.5);
  }

  input[type="date"]:focus {
    outline: none;
    box-shadow: none;
    border-color: transparent;
  }
</style>
<script src="https://cdn.tailwindcss.com"></script>
<div class="bg-white shadow rounded-lg p-4 sticky top-0 z-10">
  <h2 class="text-lg font-semibold mb-4">Hist&oacute;rico de Versiones de LMR</h2>
  <div class="flex flex-wrap items-center gap-4 mb-2">
    <label class="font-medium">Rango de Fecha:</label>
    <input type="date" id="startDate" class="px-3 py-1 focus:ring-0 focus:outline-none">
    <span class="font-bold">a</span>
    <input type="date" id="endDate" class="px-3 py-1 focus:ring-0 focus:outline-none">
    <label class="flex items-center">
      <input type="checkbox" id="onlyChanges" class="mr-2">
      Solo con cambios
    </label>
    <button id="btnBuscar" class="bg-blue-600 text-white px-4 py-2 hover:bg-blue-700">
      Buscar
    </button>
  </div>
</div>

<div class="bg-white shadow rounded-lg overflow-auto max-h-[65vh]">
  <table class="min-w-full text-left">
    <thead class="bg-gray-50 border-b text-gray-700 font-medium sticky top-0 z-5">
      <tr>
        <th class="px-4 py-2">Fecha</th>
        <th class="px-4 py-2">Estado</th>
        <th class="px-4 py-2">Detalle</th>
        <th class="px-4 py-2">Opci&oacute;n</th>
      </tr>
    </thead>
    <tbody id="versionTable" class="overflow-y-auto">
    </tbody>
  </table>
</div>

<div id="modal" class="fixed inset-0 bg-black bg-opacity-30 hidden items-center justify-center z-50">
  <div class="bg-white rounded-lg w-[90%] max-w-5xl p-6 shadow-lg relative">
    <button id="closeModal" class="absolute top-2 right-3 text-gray-500 hover:text-black text-xl">X</button>
    <h3 class="text-2xl font-semibold mb-4">Detalle de cambios</h3>

    <div class="overflow-x-auto">
      <table class="min-w-full text-sm">
        <thead class="bg-gray-100 text-left border-b text-xl">
          <tr>
            <th class="px-4 py-2 border-r">Sociedad</th>
            <th class="px-4 py-2 border-r">Etapa</th>
            <th class="px-4 py-2 border-r">Campo</th>
            <th class="px-4 py-2 border-r">Turno</th>
            <th class="px-4 py-2 border-r">Variedad</th>
            <th class="px-4 py-2 border-r">Fundo</th>
            <th class="px-4 py-2 border-r">Origen</th>
            <th class="px-4 py-2">Acci&oacute;n</th>
          </tr>
        </thead>
        <tbody id="modalTableBody" class="text-xl">
        </tbody>
      </table>
    </div>
  </div>
</div>
