<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="portlet light portlet-fit portlet-datatable bordered">

		<div class="portlet-body">
			<div class="table-container">

				<div class="table-actions-wrapper">
					<div class="btn-group"></div>

				</div>
				<table
					class="table table-striped table-bordered table-hover table-checkable"
					id="datatable_ajax">
					<thead>
						<tr role="row" class="heading">
							<th width="10%">Cod. Productor</th>
							<th width="10%">Etapa</th>
							<th width="10%">campo</th>
							<th width="10%">turno</th>
							<th width="20%">variedad</th>
							<th width="10%">N° errores</th>


							<th width="15%">Actions</th>
						</tr>
						<tr role="row" class="filter">

							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codProductor">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codParcela">
							</td>
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codCampo">
							</td>
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codTurno">
							</td>
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_idVariedad">
							</td>


							<td></td>
							<td>
								<div class="margin-bottom-5">
									<div class="col-md-4">
										<button
											class="btn btn-sm green btn-outline filter-submit margin-bottom">
											<i class="fa fa-search"></i>
										</button>
									</div>
									<div class="col-md-6">
										<button class="col-6 btn btn-sm red btn-outline filter-cancel">
											<i class="fa fa-times"></i> Borrar filtros
										</button>
									</div>
								</div>

							</td>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<div id="modal-view" class="modal fade" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog" style="width: 900px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true"></button>
				<h4 class="modal-title">Resultados</h4>
			</div>
			<div class="modal-body">



				<table
					class="table table-bordered table-scrollable table-hovertable-striped  table-condensed nowrap"
					id="tbl_det"></table>


				<div class="modal-footer">
					<button type="button" data-dismiss="modal"
						class="btn dark btn-outline">Cancelar</button>

				</div>
			</div>
		</div>
	</div>
</div>
<div id="modal-update" class="modal fade" tabindex="-1"
	aria-hidden="true">
	<input type="hidden" name="keyId" id="keyId" value="">
	<div class="modal-dialog" style="width: 900px;">
		<form id="form-update" class="horizontal-form" role="form">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title">Correción</h4>
				</div>
				<div class="modal-body">
					<div class="scroller" style="height: 400px;"
						data-always-visible="1" data-rail-visible1="1">
						<div class="form-body">


							<div class="row">
								<div class="col-md-2">
									<div class="form-group">
										<label class="control-label">Cod. Productor</label> <input
											type="text" name="codProductor" disabled="disabled"
											id="codProductor" class="form-control"
											placeholder="codProductor">

									</div>
								</div>
								<!--/span-->
								<div class="col-md-2">
									<div class="form-group">
										<label class="control-label">Etapa</label> <input type="text"
											name="codParcela" disabled="disabled" id="codParcela"
											class="form-control" placeholder="cod Etapa">

									</div>
								</div>
								<!--/span-->
								<div class="col-md-2">
									<div class="form-group">
										<label class="control-label">Campo</label> <input type="text"
											name="codCampo" disabled="disabled" id="codCampo"
											class="form-control" placeholder="cod Campo">

									</div>
								</div>

								<!--/span-->
								<div class="col-md-2">
									<div class="form-group">
										<label class="control-label">Turno</label> <input type="text"
											name="codTurno" disabled="disabled" id="codTurno"
											class="form-control" placeholder="cod Turno">

									</div>
								</div>
								<!--/span-->
								<div class="col-md-2">
									<div class="form-group">
										<label class="control-label">variedad</label> <input
											type="text" name="idVariedad" disabled="disabled"
											id="idVariedad" class="form-control" placeholder="variedad">

									</div>
								</div>

							</div>
							<div>
								<!--/span-->
								<div class="col-md-2">
									<div class="form-group">
										<label class="control-label">Cod. Productor</label> <select
											name="codProductorNew" class="codProductor form-control"
											id="codProductorNew">
											<option value="">Seleccionar</option>
										</select>

									</div>
								</div>
								<!--/span-->
								<div>
									<!--/span-->
									<div class="col-md-2">
										<div class="form-group">
											<label class="control-label">Etapa</label> <select
												name="codParcelaNew" class="codParcelaNew form-control"
												id="codParcelaNew">
												<option value="">Seleccionar</option>
											</select>

										</div>
									</div>

									<!--/span-->
									<div class="col-md-2">
										<div class="form-group">
											<label class="control-label">Campo</label> <select
												name="codCampoNew" class="codCampoNew form-control"
												id="codCampoNew">
												<option value="">Seleccionar</option>
											</select>

										</div>
									</div>

									<!--/span-->
									<div>
										<!--/span-->
										<div class="col-md-2">
											<div class="form-group">
												<label class="control-label">Turno</label> <select
													name="codTurnoNew" class="codTurnoNew form-control"
													id="codTurnoNew">
													<option value="">Seleccionar</option>
												</select>

											</div>
										</div>
										<!--/span-->
										<div>
											<!--/span-->
											<div class="col-md-2">
												<div class="form-group">
													<label class="control-label">Variedad</label> <select
														name="idVariedadNew" class="idVariedadNew form-control"
														id="idVariedadNew">
														<option value="">Seleccionar</option>
													</select>

												</div>
											</div>
											<!--/span-->

										</div>





									</div>
								</div>
								<div class="modal-footer">
									<button type="button" data-dismiss="modal"
										class="btn dark btn-outline">Cancelar</button>
									<button type="submit" class="btn green">Guardar</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
		</form>

	</div>
</div>

