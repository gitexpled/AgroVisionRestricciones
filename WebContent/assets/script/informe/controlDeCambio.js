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

        const estado = tieneCambios ? 'CAMBIOS' : 'SIN CAMBIOS';

        const botonDetalle = tieneCambios
          ? `<button class="btn btn-link btn-sm openModal" data-fecha="${fecha}">Ver detalle</button>`
          : '—';

        const botonDescarga = `
          <button class="btn btn-success btn-xs btnDescargarExcel" data-fecha="${fecha}">
            Descargar
          </button>`;

        return `
          <tr>
            <td>${fecha}</td>
            <td>${estado}</td>
            <td>${botonDetalle}</td>
            <td>${botonDescarga}</td>
          </tr>
        `;
      }).filter(row => row !== null);

      if (filas.length > 0) {
        tbody.html(filas.join(''));
      } else {
        tbody.html(`<tr><td colspan="4" class="text-center text-muted" style="padding: 15px;">No se encontraron registros</td></tr>`);
      }

      $('#versionTable').data('cambios', cambiosPorFecha);
    })
    .catch(err => {
      console.error('Error al cargar cambios:', err);
      tbody.html(`<tr><td colspan="4" class="text-center text-danger" style="padding: 15px;">Error al obtener datos del servidor</td></tr>`);
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
      <tr>
        <td>${item.sociedad || '-'}</td>
        <td>${item.etapa || '-'}</td>
        <td>${item.campo || '-'}</td>
        <td>${item.turno || '-'}</td>
        <td>${item.variedad || '-'}</td>
        <td>${item.fundo || '-'}</td>
        <td>${item.origen || '-'}</td>
        <td>${(item.accion === 'UPDATE' ? 'Modificado' : item.accion) || '-'}</td>
      </tr>
    `;
    tbody.append(row);
  });

  $('#modal').css('display', 'flex');
});

$('#closeModal, #modal').on('click', function (e) {
  if (e.target.id === 'closeModal' || e.target.id === 'modal') {
    $('#modal').css('display', 'none');
  }
});

$('#btnBuscar, #onlyChanges').on('click', renderTable);

$(document).on('click', '.btnDescargarExcel', function () {
  const fecha = $(this).data('fecha');
  const url = `/AgroVisionRestricciones/descargas/informeEstadoProductor_${fecha}.xlsx`;

  $.ajax({
    url: url,
    type: 'HEAD',
    success: function () {
      window.location.href = url;
    },
    error: function () {
      alert(`El archivo Excel para ${fecha} aún no está disponible.`);
    }
  });
});
