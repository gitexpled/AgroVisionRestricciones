<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="portlet light portlet-fit portlet-datatable bordered">

		<div class="portlet-body">
			<div class="table-container">

				<div class="table-actions-wrapper">
					<div class="btn-group">
						<button data-toggle="modal" data-target="#modal-newProductor" id="sample_editable_1_new" class="btn green  btn-outline">
							Agregar Parcela <i class="fa fa-plus"></i>
						</button>
					</div>
					
				</div>
				<table
					class="table table-striped table-bordered table-hover table-checkable"
					id="datatable_ajax">
					<thead>
						<tr role="row" class="heading">
							<th width="10%">Cód Productor</th>
							<th width="10%">Cod Parcela</th>
							<th width="10%">Nombre</th>
							<th width="10%">Creado</th>
							<th width="10%">Modificado</th>
							<th width="10%">Actions</th>
						
						</tr>
						<tr role="row" class="filter">
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codProductor"></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codParcela"></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_nombre">
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
							<label class="col-md-4 control-label">Codigo: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Usuario"
										class="fa fa-info-circle tooltips"></i> <input readOnly type="text"
										id="updateUser" name="updateUser"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Productor</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<select name="updateProductor" disabled="disabled" class="productor form-control" id="updateProductor">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div> 
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Nombre:</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Nombre"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="updateNombre" name="updateNombre" value=""
										class="form-control"> <br />
								</div>
							</div>
						</div>
						
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
						</div>
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
                <h4 class="modal-title">Registro Nuevo Parcela</h4>
            </div>
            <div class="modal-body">
                <div class="scroller" style="height:300px" data-always-visible="1" data-rail-visible1="1">
                    <div class="form-body">
                    	
						 <div class="form-group">
                            <label class="col-md-4 control-label">Código Productor</label>
                           <div class="col-md-8">
                                <div class="input-icon input-large">
                                    <div class="input-group">
                                   
                                        <select name="codigoProductor" class="productor form-control" id="codigoProductor">
                                       		<option value="">Seleccionar</option>
                                      </select>
                                      </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">Código</label>
                           <div class="col-md-8">
                                <div class="input-icon input-large">
                                    <div class="input-group">
                                   
                                        <input name="regCodigo" id="regCodigo" type="text" class="form-control" placeholder="Código"> 
                                      </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">Nombre</label>
                           <div class="col-md-8">
                                <div class="input-icon input-large">
                                    <div class="input-group">
                                    	
                                    	<input name="regNombre" id="regNombre" type="text" class="form-control" placeholder="Nombre">
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
