function formatDate(date) {
  return date.toISOString().split("T")[0];
}

function getDateRange(start, end) {
  const range = [];
  let current = new Date(start + 'T00:00:00');
  const last = new Date(end + 'T00:00:00');
  while (current <= last) {
    const year = current.getFullYear();
    const month = String(current.getMonth() + 1).padStart(2, '0');
    const day = String(current.getDate()).padStart(2, '0');
    range.push(`${year}-${month}-${day}`);
    current.setDate(current.getDate() + 1);
  }

  return range;
}

function renderTable() {
  const start = $('#startDate').val();
  const end = $('#endDate').val();
  const onlyChanges = $('#onlyChanges').is(':checked');
  const tbody = $('#versionTable');
  tbody.empty();

  if (!start || !end) {
    alert('Debe seleccionar el rango de fechas.');
    return;
  }

  const url = `${PROYECT}/json/Jerarquia/getCambioJerarquia/${start}/${end}`;

  fetch(url)
    .then(res => res.json())
    .then(data => {
      const cambiosPorFecha = {};
      data.forEach(cambio => {
        if (!cambiosPorFecha[cambio.fecha]) cambiosPorFecha[cambio.fecha] = [];
        cambiosPorFecha[cambio.fecha].push(cambio);
      });

      const todasLasFechas = getDateRange(start, end);

      const filas = todasLasFechas.map(fecha => {
        const tieneCambios = cambiosPorFecha[fecha] !== undefined;
        if (onlyChanges && !tieneCambios) return null;

        let estado = tieneCambios ? 'CAMBIOS' : 'SIN CAMBIOS';
        let botonDetalle = tieneCambios
          ? `<button class="text-blue-600 hover:underline openModal" data-fecha="${fecha}">Ver detalle</button>`
          : '—';

        return `
          <tr class="border-b hover:bg-gray-50">
            <td class="px-4 py-2">${fecha}</td>
            <td class="px-4 py-2">${estado}</td>
            <td class="px-4 py-2">${botonDetalle}</td>
            <td class="px-4 py-2"><a href="#" class="text-green-600 hover:underline">DESCARGAR</a></td>
          </tr>
        `;
      }).filter(row => row !== null);

      if (filas.length > 0) {
        tbody.html(filas.join(''));
      } else {
        tbody.html(`<tr><td colspan="4" class="px-4 py-4 text-center text-gray-500">No se encontraron registros</td></tr>`);
      }
      $('#versionTable').data('cambios', cambiosPorFecha);
    })
    .catch(err => {
      console.error('Error al cargar cambios:', err);
      tbody.html(`<tr><td colspan="4" class="text-center text-red-600 p-4">Error al obtener datos del servidor</td></tr>`);
    });
}

$(document).on('click', '.openModal', function () {
  const fecha = $(this).data('fecha');
  const cambios = $('#versionTable').data('cambios')[fecha];
  if (!cambios || cambios.length === 0) return;
  const tbody = $('#modalTableBody');
  tbody.empty();
  cambios.forEach(item => {
    const row = `
      <tr class="border-t">
        <td class="px-4 py-2 border-r">${item.sociedad || '-'}</td>
        <td class="px-4 py-2 border-r">${item.etapa || '-'}</td>
        <td class="px-4 py-2 border-r">${item.campo || '-'}</td>
        <td class="px-4 py-2 border-r">${item.turno || '-'}</td>
        <td class="px-4 py-2 border-r">${item.variedad || '-'}</td>
        <td class="px-4 py-2 border-r">${item.fundo || '-'}</td>
        <td class="px-4 py-2 border-r">${item.origen || '-'}</td>
        <td class="px-4 py-2">${(item.accion === 'UPDATE'? 'Modificado': item.accion) || '-'}</td>
      </tr>
    `;
    tbody.append(row);
  });

  $('#modal').removeClass('hidden').addClass('flex');
});

$('#closeModal, #modal').on('click', function (e) {
  if (e.target.id === 'closeModal' || e.target.id === 'modal') {
    $('#modal').addClass('hidden');
  }
});

$('#btnBuscar, #onlyChanges').on('click', renderTable);
