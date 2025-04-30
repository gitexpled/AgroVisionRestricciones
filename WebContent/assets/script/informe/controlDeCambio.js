
const data = [
  { fecha: '2025-01-01', estado: 'Sin cambios', detalle: 'No se registraron modificaciones para esta fecha.' },
  { fecha: '2025-01-02', estado: 'Sin cambios', detalle: 'No se registraron modificaciones para esta fecha.' },
  { fecha: '2025-01-03', estado: 'Sin cambios', detalle: 'No se registraron modificaciones para esta fecha.' },
  { fecha: '2025-01-04', estado: 'Sin cambios', detalle: 'No se registraron modificaciones para esta fecha.' },
  { fecha: '2025-01-05', estado: 'Sin cambios', detalle: 'No se registraron modificaciones para esta fecha.' },
  {
    fecha: '2025-01-06',
    estado: 'Cambios',
    detalle:
	`Jerarquía: Producto > País > LMR
	Origen del cambio: Inspección fitosanitaria
	Cambio detectado:
	Campo: LMR - Chile
	Valor anterior: 1.2 mg/kg
	Valor nuevo: 0.8 mg/kg

	Motivo: Actualización de normativa SAG`
  },
  { fecha: '2025-01-07', estado: 'Sin cambios', detalle: 'No se registraron modificaciones para esta fecha.' },
  { fecha: '2025-01-08', estado: 'Sin cambios', detalle: 'No se registraron modificaciones para esta fecha.' },
  { fecha: '2025-01-09', estado: 'Sin cambios', detalle: 'No se registraron modificaciones para esta fecha.' },
  { fecha: '2025-01-10', estado: 'Sin cambios', detalle: 'No se registraron modificaciones para esta fecha.' }
];

function renderTable() {
  const onlyChanges = $('#onlyChanges').is(':checked');
  const start = $('#startDate').val();
  const end = $('#endDate').val();
  const tbody = $('#versionTable');
  tbody.empty();

  const filtered = data.filter(item => {
    return (!onlyChanges || item.estado === 'Cambios') &&
      item.fecha >= start && item.fecha <= end;
  });

  filtered.forEach(item => {
    const row = `
      <tr class="border-b hover:bg-gray-50">
        <td class="px-4 py-2">${item.fecha}</td>
        <td class="px-4 py-2">${item.estado}</td>
        <td class="px-4 py-2">
          <button class="text-blue-600 hover:underline openModal" data-detalle="${encodeURIComponent(item.detalle)}">
            Ver detalle
          </button>
        </td>
        <td class="px-4 py-2">
          <a href="#" class="text-green-600 hover:underline">Descargar</a>
        </td>
      </tr>`;
    tbody.append(row);
  });

  if (filtered.length === 0) {
    tbody.append(`<tr><td colspan="4" class="px-4 py-4 text-center text-gray-500">No se encontraron registros</td></tr>`);
  }
}

$(document).ready(function () {
  renderTable();

  $('#btnBuscar, #onlyChanges').on('click', renderTable);

  $(document).on('click', '.openModal', function () {
    const detalle = decodeURIComponent($(this).data('detalle'));
    $('#modalContent').text(detalle);
    $('#modal').removeClass('hidden').addClass('flex');
  });

  $('#closeModal, #modal').on('click', function (e) {
    if (e.target.id === 'closeModal' || e.target.id === 'modal') {
      $('#modal').addClass('hidden');
    }
  });
});