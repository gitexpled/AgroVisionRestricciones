<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="portlet light portlet-fit portlet-datatable bordered">

		<div class="portlet-body">
			<div class="table-container">

				<div class="table-actions-wrapper">
					<div class="btn-group">
						<button data-toggle="modal" data-target="#modal-newProductor" id="sample_editable_1_new" class="btn green  btn-outline">
							Agregar Mercado Productor <i class="fa fa-plus"></i>
						</button>
					</div>
					
				</div>
				<table
					class="table table-striped table-bordered table-hover table-checkable"
					id="datatable_ajax">
					<thead>
						<tr role="row" class="heading">
							<th width="10%">ID</th>
							<th width="10%">Cód Productor</th>
							<th width="10%">Cód parcela</th>
							<th width="10%">Cód Turno</th>
							<th width="10%">Cod Variedad</th>
							<th width="10%">Mercado</th>
							<th width="10%">Creado</th>
							<th width="10%">Modificado</th>
							<th width="10%">Actions</th>
						</tr>
						<tr role="row" class="filter">
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_ID"></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codProductor"></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codParcela"></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codTurno"></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codVariedad">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_mercado">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_creado">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_modificado"></td>
							<td>
								<div class="margin-bottom-5">
									<button
										class="btn btn-sm green btn-outline filter-submit margin-bottom">
										<i class="fa fa-search"></i>
									</button>
									<button class="btn btn-sm red btn-outline filter-cancel">
									<i class="fa fa-times"></i> Borrar filtros
								</button>
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
<div id="modal-modifica-productor" class="modal fade" tabindex="-1"
	data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog">
		<form id="modifica-cuenta-form">
			<div class="modal-content">
				<div class="form-body">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h2 class="modal-title"></h2>
					</div>
					<div class="modal-body">
					<div class="form-group">
							<label class="col-md-4 control-label">Cod. Productor</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<select name="updatecodProductor" class="codProductor form-control" id="updatecodProductor">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Cod. Etapa</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<select name="updatecodParcela" class="codParcela form-control" id="updatecodParcela">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Cod. Turno</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<select name="updatecodTurno" class="codTurno form-control" id="updatecodTurno">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Cod. Variedad</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<select name="updatecodVariedad" class="codVariedad form-control" id="updatecodVariedad">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Mercado</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<select name="updateMercado" class="mercado form-control" id="updateMercado">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<!--  
						<div class="form-group">
							<label class="col-md-4 control-label">Fecha Creación:</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Apellido"
										class="fa fa-info-circle tooltips"></i> <input readOnly type="text"
										id="updateFeCreacion" name="updateFeCreacion"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label">Ultima Modificación</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Contraseña"
										class="fa fa-info-circle tooltips"></i> <input readOnly type="text"
										id="updateModificacion" name="updateModificacion"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Usuario Modificación</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Contraseña"
										class="fa fa-info-circle tooltips"></i> <input readOnly type="text"
										id="updateUserMod" name="updateUserMod"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>-->
						<div class="clearfix"></div>

					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn default">
							Cerrar <i class="fa fa-sign-out"></i>
						</button>
						<button type="submit" id="modificar-cuenta"
							class="btn gtd-blue-hard button-modifica-cuenta">
							Modificar <i class="fa fa-pencil-square"></i>
						</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<div id="modal-newProductor" class="modal fade" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
       <form id="form-InsertProductor" class="form-horizontal" role="form">
      
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">Registro Mercado Productor</h4>
            </div>
            <div class="modal-body">
                <div class="scroller" style="height:300px" data-always-visible="1" data-rail-visible1="1">
                    <div class="form-body">
                    
                     <div class="form-group">
                            <label class="col-md-4 control-label">Cod Productor</label>
                           <div class="col-md-8">
                                <div class="input-icon input-large">
                                    <div class="input-group">
                                   <select name="codProductor" class="codProductor form-control" id="codProductor">
                                       		<option value="">Seleccionar</option>
                                      </select>
                                      </div>
                                </div>
                            </div>
                        </div>
                    	
						 <div class="form-group">
                            <label class="col-md-4 control-label">Cod Etapa</label>
                           <div class="col-md-8">
                                <div class="input-icon input-large">
                                    <div class="input-group">
                                   <select name="codParcela" class="codParcela form-control" id="codParcela">
                                       		<option value="">Seleccionar</option>
                                      </select>
                                      </div>
                                </div>
                            </div>
                        </div>
                         <div class="form-group">
                            <label class="col-md-4 control-label">Cod Turno</label>
                           <div class="col-md-8">
                                <div class="input-icon input-large">
                                    <div class="input-group">
                                   <select name="codTurno" class="codTurno form-control" id="codTurno">
                                       		<option value="">Seleccionar</option>
                                      </select>
                                      </div>
                                </div>
                            </div>
                        </div>
                       <div class="form-group">
                            <label class="col-md-4 control-label">Variedad</label>
                           <div class="col-md-8">
                                <div class="input-icon input-large">
                                    <div class="input-group">
                                   <select name="idVariedad" class="idVariedad form-control" id="idVariedad">
                                       		<option value="">Seleccionar</option>
                                      </select>
                                      </div>
                                </div>
                            </div>
                        </div>
                        
                        
                        <div class="form-group">
                            <label class="col-md-4 control-label">Mercado</label>
                           <div class="col-md-8">
                                <div class="input-icon input-large">
                                    <div class="input-group">
                                   <select name="idMercado" class="mercado form-control" id="idMercado">
                                       		<option value="">Seleccionar</option>
                                      </select>
                                      </div>
                                </div>
                            </div>
                        </div>
                        
                         
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline">Cancelar</button>
                <button type="submit" class="btn green">Guardar</button>
            </div>
        </div>
       </form>
    </div>
</div>