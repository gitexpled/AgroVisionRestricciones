<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div style="margin-top: 42px;" class="portlet light portlet-fit portlet-datatable bordered">

		<div class="portlet-body">
			<div class="table-container">

				<div class="table-actions-wrapper">
					<div class="btn-group">
						<button data-toggle="modal" data-target="#modal-newLimite" id="sample_editable_1_new" class="btn green  btn-outline">
							Agregar Limite <i class="fa fa-plus"></i>
						</button>
						<button data-toggle="modal" data-target="#modal-crea-folio" id="sample_editable_1_new" class="btn green  btn-outline">
							Carga Excel <i class="fa fa-plus"></i>
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
							<li><a href="javascript:;"> Export a CSV </a></li>

						</ul>
					</div>-->
				</div>
				<table class="table table-striped table-bordered table-hover table-checkable"
					id="datatable_ajax">
					<thead>
						<tr role="row" class="heading">
							<th width="10%">Mercado</th>
							<th width="10%">Especie</th>
							<th width="10%">LMR</th>
							<th width="10%">Ingr. Activo</th>
							
							<th width="10%">Fuente</th>
							<th width="10%">Creacion</th>
							<th width="10%">Modificación</th>
							<th width="10%">Actions</th>
						</tr>
						<tr role="row" class="filter">
							<td><select name="vw_l.idMercado" class="form-control form-filter input-sm mercados"><option value="">Seleccionar</option></select></td>
							<td><select name="vw_l.idEspecie" class="form-control form-filter input-sm especies"><option value="">Seleccionar</option></select></td>
							<td>&nbsp;</td>
							<td><select name="vw_l.codProducto" class="form-control form-filter input-sm productos"><option value="">Seleccionar</option></select></td>
							
							<td><select name="vw_l.idFuente" class="form-control form-filter input-sm fuentes"><option value="">Seleccionar</option></select></td>
							<td>
								<div class="input-group date date-picker margin-bottom-5"
									data-date-format="dd/mm/yyyy">
									<input type="text" class="form-control form-filter input-sm"
										readonly name="vw_l.creacion_from" placeholder="From"> <span
										class="input-group-btn">
										<button class="btn btn-sm default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>								<div class="input-group date date-picker"
									data-date-format="dd/mm/yyyy">
									<input type="text" class="form-control form-filter input-sm"
										readonly name="vw_l.creacion_to" placeholder="To"> <span
										class="input-group-btn">
										<button class="btn btn-sm default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>

							</td>
							<td>
								<div class="input-group date date-picker margin-bottom-5"
									data-date-format="dd/mm/yyyy">
									<input type="text" class="form-control form-filter input-sm"
										readonly name="vw_l.baja_from" placeholder="From"> <span
										class="input-group-btn">
										<button class="btn btn-sm default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
								<div class="input-group date date-picker"
									data-date-format="dd/mm/yyyy">
									<input type="text" class="form-control form-filter input-sm"
										readonly name="vw_l.baja_to" placeholder="To"> <span
										class="input-group-btn">
										<button class="btn btn-sm default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
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
<div id="modal-modifica-cuenta" class="modal fade" tabindex="-1"
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
							<label class="col-md-4 control-label">LMR: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Limite"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="updateLimite" name="updateLimite"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Ingr. Activo</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<select name="updateCodProducto" class="productos form-control" id="updateCodProducto">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Especie</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<select name="updateEspecie" class="especies form-control" id="updateEspecie">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Mercado</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<select name="updateMercado" class="mercados form-control" id="updateMercado">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label">Tipo</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<select name="updateTipoProducto" class="tiposProductos form-control" id="updateTipoProducto">
                                      		<option value="">Seleccionar</option>
                                     </select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Fuente</label>
							<div class="col-md-8">
								<select name="updateFuente" class="fuentes form-control" id="updateFuente">
                                 	<option value="">Seleccionar</option>
                                </select> <br />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Creación</label>
							<div class="col-md-8">
								<input readOnly type="text" id="updateCreacion" name="updateCreacion" value="" class="form-control"><br />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Modificación</label>
							<div class="col-md-8">
								<input readOnly type="text" id="updateModificacion" name="updateModificacion" value="" class="form-control"> <br />
							</div>
						</div>
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
<div id="modal-newLimite" class="modal fade" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
       <form id="form-InsertLimite" class="form-horizontal" role="form">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">Nuevo LMR</h4>
            </div>
            <div class="modal-body">
                <div class="scroller" style="height:300px" data-always-visible="1" data-rail-visible1="1">
                         <div class="form-body">
                             <div class="form-group">
                                 <label class="col-md-2 control-label">Limite</label>
                                <div class="col-md-10">
                                     <div class="input-inline input-large">
                                         <div class="input-group">
                                             <span class="input-group-addon">
                                                 <i class="fa fa-user"></i>
                                             </span>
                                             <input name="regLimite" id="regLimite" type="text" class="form-control" placeholder="Nombre"> 
                                           </div>
                                     </div>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label class="col-md-2 control-label">Ingr. Activo</label>
                                <div class="col-md-10">
                                     <div class="input-inline input-large">
                                         <div class="input-group">
                                         	<span class="input-group-addon" id="sizing-addon1">@</span>
                                         	<select name="regProducto" class="productos form-control" id="regProducto">
                                         		<option value="">Seleccionar</option>
                                         	</select>
                                         </div>
                                     </div>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label class="col-md-2 control-label">Especie</label>
                                <div class="col-md-10">
                                     <div class="input-inline input-large">
                                         <div class="input-group">
                                         	<span  class="input-group-addon"><i class="fa fa-users"></i></span>
                                         	<select name="regEspecie" class="especies form-control" id="regEspecie">
                                         		<option value="">Seleccionar</option>
                                         	</select>
                                         </div>
                                     </div>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label class="col-md-2 control-label">Mercado</label>
                                <div class="col-md-10">
                                     <div class="input-inline input-large">
                                         <div class="input-group">
                                         	<span  class="input-group-addon"><i class="fa fa-users"></i></span>
                                         	<select name="regMercado" class="mercados form-control" id="regMercado">
                                         		<option value="">Seleccionar</option>
                                         	</select>
                                         </div>
                                     </div>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label class="col-md-2 control-label">Tipo Producto</label>
                                <div class="col-md-10">
                                     <div class="input-inline input-large">
                                         <div class="input-group">
                                         	 <span class="input-group-addon">
                                                 <i class="fa fa-lock fa-fw"></i>
                                             </span>
                                         	<select name="regTipoProducto" class="tiposProductos form-control" id="regTipoProducto">
                                         		<option value="">Seleccionar</option>
                                         	</select>
                                         </div>
                                     </div>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label class="col-md-2 control-label">Fuente</label>
                                <div class="col-md-10">
                                     <div class="input-inline input-large">
                                         <div class="input-group">
                                         	 <span class="input-group-addon">
                                                 <i class="fa fa-lock fa-fw"></i>
                                             </span>
                                         	<select name="regFuente" class="fuentes form-control" id="regFuente">
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
<div id="modal-crea-folio" class="modal fade" tabindex="-1"
	data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog">
		<form id="cargaExcel" enctype="multipart/form-data">
			<div class="modal-content">
				<div class="form-body">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h2 class="modal-title"></h2>
					</div>
					<div class="form-group">
					  <label class="col-md-4 control-label">Archivo LMR: </label>
					  <div class="col-md-8">
					    <div class="input-icon right">
					      <i data-container="body" data-original-title="File" class="fa fa-info-circle tooltips"></i>
					      <input type="file" id="file" name="file" class="form-control">
					      <br />
					      <a href="https://dev-net.goplicity.com/descargas/mval/base_lmr.xlsx" download class="btn btn-link p-0 mt-2">
					        <i class="fa fa-download"></i> Descargar Excel base
					      </a>
					    </div>
					  </div>
					  <div class="clearfix"></div>
					</div>
					<div id="cargando" style="display: none; text-align: center; padding-bottom: 1rem; font-weight: 700;">
					  Cargando<span id="puntos">.</span>
					</div>
					<div id="excel-error"
				         class="alert alert-danger"
				         style="display:none; white-space:pre-wrap"></div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn default">
							Cerrar <i class="fa fa-sign-out"></i>
						</button>
						<button type="submit" id="modificar-cuenta"
							class="btn gtd-blue-hard button-modifica-cuenta">
							Crear <i class="fa fa-pencil-square"></i>
						</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
