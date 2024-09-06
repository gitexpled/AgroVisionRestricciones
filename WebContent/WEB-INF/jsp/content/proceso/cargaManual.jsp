<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="portlet light portlet-fit portlet-datatable bordered">

		<div class="portlet-body">
			<div class="table-container">

					<div class="table-actions-wrapper">
					<div class="btn-group">
						<button data-toggle="modal" data-target="#modal-insert" id="modal-new" class="btn green  btn-outline">
							Agregar Analisis <i class="fa fa-plus"></i>
						</button>
					</div>
					<!-- <div class="btn-group">
						<a class="btn red btn-outline " href="javascript:;"
							data-toggle="dropdown"> <i class="fa fa-share"></i> <span
							class="hidden-xs"> Herramientas </span> <i
							class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu pull-right">
							<li><a href="javascript:;"> Export a Excel </a></li>

						</ul>
					</div> -->
				</div>
				<table
					class="table table-striped table-bordered table-hover table-checkable"
					id="datatable_ajax">
					<thead>
						<tr role="row" class="heading">
							<th width="1%">id</th>
							<th width="20%">Cod. Productor</th>
							<th width="20%">Especie</th>
							<th width="20%">Cod. Etapa</th>
							<th width="20%">Cod. Turno</th>
							<th width="20%">Cod. Variedad</th>
							<th width="20%">Fecha muestreo</th>
							<th width="30%">Laboratorio</th>
							<th width="30%">N° Informe</th>
							<th width="20%">Actions</th>
						</tr>
						<tr role="row" class="filter">
							<td></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codProductor">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_especie">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codParcela">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_CodTurno">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_cod">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_fecha">
							</td>
								<td>
								<input type="text"
								class="form-control form-filter input-sm" name="vw_laboratorio">
							</td>
								<td><input type="text"
								class="form-control form-filter input-sm" name="vw_identificador">
							</td>
							
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
<div id="modal-insert" class="modal fade" tabindex="-1" aria-hidden="true"  >
    <div class="modal-dialog" style="width: 900px;">
       <form id="form-new" class="horizontal-form" role="form">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">Registro de nuevo Analisis</h4>
            </div>
            <div class="modal-body">
                <div class="scroller" style="height:400px; " data-always-visible="1" data-rail-visible1="1">
                         <div class="form-body">
                         
							
								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Laboratorio</label> <input
												type="text" name="laboratorio" id="laboratorio" class="form-control"
												placeholder="Laboratorio"> 

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">N° Informe</label> <input
												type="text" name="identificador" id="Identificador" class="form-control"
												placeholder="Identificador"> 

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Productor</label> 
												<select name="codProductor" class="codProductor form-control" id="codProductor">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Etapa</label> 
												<select name="codParcela" class="codParcela form-control" id="codParcela">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
											<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Turno</label> 
												<select name="codTurno" class="codTurno form-control" id="codTurno">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
										<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Especie</label> 
												<select name="idEspecie" class="idEspecie form-control" id="idEspecie">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Variedad</label> 
												<select name="idVariedad" class="idVariedad form-control" id="idVariedad">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Fecha</label> 
										<input id="fecha" name="fecha" class="form-control" type="date" value="">
                                         
										</div>
									</div>
									<!--/span-->
								</div>



							<div class="row">
								<div class="col-md-8">
									<h5 class="row-section">Analisis</h5>
								</div>
								<div class="col-md-12">
									<div class="mt-repeater">
										<div data-repeater-list="detalle">
											<div data-repeater-item class="row">
												<div class="col-md-4">
													<label class="control-label">Ingr. Activo</label> 
													<select name=codProducto  class="codProducto form-control m-bootstrap-select m_selectpicker" data-live-search="true" id="codProductoNew">
                                      		<option value="">Seleccionar</option>
                                      		<c:forEach var="r" items="${optionDiccionario}">
												<option value="${r.codProducto}">${r.codProducto}</option>
											</c:forEach>
                                      		
                                     </select>
												</div>
												<div class="col-md-4">
													<label class="control-label">Resultado</label> 
													<input type="number" min="1" max="5" name="limite"  id="limiteInput"placeholder="limite"  class="limite form-control" />
												   
												</div>
												<div class="col-md-4">
													<a href="javascript:;" onclick='javascript: agregarFila();'
														class="btn btn-lg green"> Agregar <i
														class="fa fa-plus"></i>
													</a>
												</div>

											</div>
										</div>
										
										
									</div>
								</div>
							</div>


						</div>
						<br>
						<div class="table-responsive">
							<table
								class="table table-bordered table-hover tzable-striped table-condensed dataTable no-footer"
								id="tbl_Fito">
								<thead>
									<tr>
										
										<th>Componente</th>
										<th>Resultado</th>
										<th style="display: none;">Componente</th>
										<th style="display: none;">resultado</th>
										<th>Opciones</th>
									</tr>
								</thead>
								<tbody id="tblPeticion"></tbody>
							</table>
						</div>
					</div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline">Cancelar</button>
                <button type="button" class="btn green" onclick='javascript: Enviar();'>Guardar</button>
            </div>
        </div>
        </div>
       </form>
    </div>
</div>

<div id="modal-update" class="modal fade" tabindex="-1" aria-hidden="true"  >
<input type="hidden" name="idUpdate" id="idUpdate" class="form-control" placeholder=""> 
    <div class="modal-dialog" style="width: 900px;">
       <form id="form-update" class="horizontal-form" role="form">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">Registro de nuevo Analisis</h4>
            </div>
            <div class="modal-body">
                <div class="scroller" style="height:400px; " data-always-visible="1" data-rail-visible1="1">
                         <div class="form-body">
                         
							
								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Laboratorio</label> <input
												type="text" name="laboratorioUpdate" id="laboratorioUpdate" class="form-control"
												placeholder="Laboratorio"> 

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Identificador</label> <input
												type="text" name="identificador" id="IdentificadorUpdate" class="form-control"
												placeholder="Identificador"> 

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Productor</label> 
												<select name="codProductorUpdate" class="codProductor form-control" id="codProductorUpdate">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Etapa</label> 
												<select name="codParcela" class="codParcelaUpdate form-control" id="codParcelaUpdate">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
											<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Turno</label> 
												<select name="codTurnoUpdate" class="codTurnoUpdate form-control" id="codTurnoUpdate">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Especie</label> 
												<select name="idEspecie" class="idEspecie form-control" id="idEspecieUpdate">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Variedad</label> 
												<select name="idVariedadUpdate" class="idVariedadUpdate form-control" id="idVariedadUpdate">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Fecha</label> 
											<input id="fechaUpdate" class="form-control" type="date" value="">

										</div>
									</div>
									<!--/span-->
								</div>


							


						</div>
						<div class="table-responsive">
							<table
								class="table table-bordered table-hover tzable-striped table-condensed dataTable no-footer"
								id="tbl_Fito">
								<thead>
									<tr>
										
										<th>Componente</th>
										<th>Resultado</th>
										<th style="display: none;">Componente</th>
										<th style="display: none;">resultado</th>
										<th>Opciones</th>
									</tr>
								</thead>
								<tbody id="tblPeticionU"></tbody>
							</table>
						</div>
					</div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline">Cancelar</button>
                <button type="button" class="btn green" onclick='javascript: actualizar();'>Actualizar</button>
            </div>
        </div>
        </div>
       </form>
    </div>
</div>
