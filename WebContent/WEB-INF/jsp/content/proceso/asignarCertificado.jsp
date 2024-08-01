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
							Asignar Certificado<i class="fa fa-plus"></i>
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
							<th width="20%">Productor</th>
							<th width="20%">Sector</th>
							<th width="10%">Equipo</th>
							<th width="20%">Variedad</th>
							<th width="20%">Certificado</th>							
							<th width="20%">Fecha</th>
							<th width="10%">Actions</th>
						</tr>
						<tr role="row" class="filter">
							<td></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codProductor">
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
								class="form-control form-filter input-sm" name="vw_certificado">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_fecha">
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
											<label class="control-label">Productor</label> 
												<select name="codProductor" class="codProductor form-control" id="codProductor">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Sector</label> 
												<select name="codParcela" class="codParcela form-control" id="codParcela">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
											<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Equipo</label> 
												<select name="codTurno" class="codTurno form-control" id="codTurno">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Variedad</label> 
												<select name="idVariedad" class="idVariedad form-control select-multiple" id="idVariedad"></select>

										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Certificado</label> 
												<select name="idCertificado" class="idCertificado form-control" id="idCertificado">
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





						</div>
						<br>
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
                <h4 class="modal-title">Actualizar Asignacion Certificado</h4>
            </div>
            <div class="modal-body">
                <div class="scroller" style="height:400px; " data-always-visible="1" data-rail-visible1="1">
                         <div class="form-body">
                         
							
								<div class="row">
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
											<label class="control-label">Sector</label> 
												<select name="codParcela" class="codParcelaUpdate form-control" id="codParcelaUpdate">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
											<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Equipo</label> 
												<select name="codTurnoUpdate" class="codTurnoUpdate form-control" id="codTurnoUpdate">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Variedad</label> 
												<select name="idVariedadUpdate" class="idVariedadUpdate form-control  select-multiple" id="idVariedadUpdate"></select>

										</div>
									</div>
									<!--/span-->
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Certificado</label> 
												<select name="idCertificadoUpdate" class="idCertificado form-control" id="idCertificadoUpdate">
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
