<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
  input[type="date"]::-webkit-calendar-picker-indicator {
    filter: invert(0.5);
  }

  input[type="date"]:focus {
    outline: none;
    box-shadow: none;
    border-color: #ccc;
  }

  .modal-custom {
    position: fixed;
    top: 0; left: 0;
    width: 100%; height: 100%;
    background: rgba(0,0,0,0.3);
    display: none;
    align-items: center;
    justify-content: center;
    z-index: 1050;
  }
</style>
<div class="panel panel-default" style="position: sticky; top: 0; z-index: 1000;">
  <div class="panel-heading"><strong>Histórico de Versiones de LMR</strong></div>
  <div class="panel-body">
    <div class="form-inline">
      <label>Rango de Fecha:</label>
      <input type="date" id="startDate" class="form-control input-sm">
      <strong>a</strong>
      <input type="date" id="endDate" class="form-control input-sm">

      <label class="checkbox-inline">
        <input type="checkbox" id="onlyChanges"> Solo con cambios
      </label>

      <button id="btnBuscar" class="btn btn-primary btn-sm">
        Buscar
      </button>
    </div>
  </div>
</div>
<div class="panel panel-default" style="max-height: 65vh; overflow-y: auto;">
  <div class="table-responsive">
    <table class="table table-bordered table-hover">
      <thead>
        <tr class="bg-info">
          <th>Fecha</th>
          <th>Estado</th>
          <th>Detalle</th>
          <th>Opción</th>
        </tr>
      </thead>
      <tbody id="versionTable">
      </tbody>
    </table>
  </div>
</div>
<div id="modal" class="modal-custom">
  <div class="panel panel-default" style="width: 95%; max-width: 1200px; padding: 20px; background: white; position: relative;">
    <button id="closeModal" type="button" class="close" style="position: absolute; top: 10px; right: 15px;">&times;</button>
    <h3><strong>Detalle de cambios</strong></h3>

    <div class="table-responsive">
      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Sociedad</th>
            <th>Etapa</th>
            <th>Campo</th>
            <th>Turno</th>
            <th>Variedad</th>
            <th>Fundo</th>
            <th>Origen</th>
            <th>Acción</th>
          </tr>
        </thead>
        <tbody id="modalTableBody">
        </tbody>
      </table>
    </div>
  </div>
</div>
