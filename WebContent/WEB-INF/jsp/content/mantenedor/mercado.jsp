<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="portlet light portlet-fit portlet-datatable bordered">

		<div class="portlet-body">
			<div class="table-container">
				<div class="table-actions-wrapper">
					<div class="btn-group">
					<!--	<button data-toggle="modal" data-target="#modal-newMercado" id="sample_editable_1_new" class="btn green  btn-outline">
							Agregar Mercado <i class="fa fa-plus"></i>
						</button> -->
					</div>
					<!--<div class="btn-group">
						<a class="btn red btn-outline " href="javascript:;"
							data-toggle="dropdown"> <i class="fa fa-share"></i> <span
							class="hidden-xs"> Herramientas </span> <i
							class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu pull-right">
							<li><a href="javascript:;"> Export a Excel </a></li>

						</ul>
					</div>-->
				</div>
				<table
					class="table table-striped table-bordered table-hover table-checkable"
					id="datatable_ajax">
					<thead>
						<tr role="row" class="heading">
							<th width="25%">Mercado</th>
							<th width="25%">ISO</th>
							<th width="10%">Prefijo</th>
							<th width="10%">regla</th>
							<th width="10%">Cliente</th>
							<th width="10%">Sap</th>
							<th width="15%">Creado</th>
							<th width="15%">Modificado</th>
							<th width="15%">Actions</th>
						</tr>
						<tr role="row" class="filter">
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_mercado">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_mercado2">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_pf">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_regla">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_cliente">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_sap">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_creado">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_modificado"></td>
							<td>
								<div class="margin-bottom-5">
									<div class="col-md-2">
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
<div id="modal-modifica-mercado" class="modal fade" tabindex="-1"
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
							<label class="col-md-4 control-label">Mercado: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Usuario"
										class="fa fa-info-circle tooltips"></i> <input type="text"" readonly="readonly"
										id="updateMercado"  name="updateMercado"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">ISO: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Usuario"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="updateMercado2" name="updateMercado2"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Prefijo: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Usuario"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="updatePf" name="updatePf"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Regla Fosetyl<span
								class="required">*</span></label>
							<div class="col-md-8">
								<select id="update_regla" name="update_regla"
									class="form-control">
									<option value="">Elegir</option>
								</select> <br /> <br />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-4 control-label">Cliente<span
								class="required">*</span></label>
							<div class="col-md-8">
								<select id="update_cliente" name="update_cliente"
									class="form-control">
									<option value="">Elegir</option>
								</select> <br /> <br />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-4 control-label">Mercado<span
								class="required">*</span></label>
							<div class="col-md-8">
								<select id="update_idMercadoPadre" name="update_idMercadoPadre"
									class="form-control">
									<option value="">Elegir</option>
								</select> <br /> <br />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-4 control-label">Porcentaje LMR: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Usuario"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="update_porcentaje" name="update_porcentaje"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-4 control-label">Regla Porcentaje<span
								class="required">*</span></label>
							<div class="col-md-8">
								<select id="update_restPorcentaje" name="update_restPorcentaje"
									class="form-control">
									<option value="">Elegir</option>
								</select> <br /> <br />
							</div>
						</div>
						
						
						
						<div class="form-group">
							<label class="col-md-4 control-label">Sum Porcentaje: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="valor"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="update_restValor" name="update_restValor"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-4 control-label">Regla Productor<span
								class="required">*</span></label>
							<div class="col-md-8">
								<select id="update_productor" name="update_productor"
									class="form-control">
									<option value="">Elegir</option>
								</select> <br /> <br />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Regla Moleculas<span
								class="required">*</span></label>
							<div class="col-md-8">
								<select id="update_retricionMolecula" name="update_retricionMolecula"
									class="form-control">
									<option value="">Elegir</option>
								</select> <br /> <br />
							</div>
						</div>
						
						
						<div class="form-group">
							<label class="col-md-4 control-label">Molecula: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Usuario"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="update_molecula" name="update_molecula"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>
						
						
						
						
						
						
						
						<div class="form-group">
							<label class="col-md-4 control-label">Regla ArfD<span
								class="required">*</span></label>
							<div class="col-md-8">
								<select id="update_restArfD" name="update_restArfD"
									class="form-control">
									<option value="">Elegir</option>
								</select> <br /> <br />
							</div>
						</div>
							
						<div class="form-group">
							<label class="col-md-4 control-label">ArfD Porcentaje: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="valor"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="update_restPorArfD" name="update_restPorArfD"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-4 control-label">Regla ArfD porcentaje<span
								class="required">*</span></label>
							<div class="col-md-8">
								<select id="update_restSumArfD" name="update_restSumArfD"
									class="form-control">
									<option value="">Elegir</option>
								</select> <br /> <br />
							</div>
						</div>
						
						
						
						
						<div class="form-group">
							<label class="col-md-4 control-label">Sum ArfD Porcentaje: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="valor"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="update_restValorArfD" name="update_restValorArfD"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-4 control-label">SAP: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="valor"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="updateSAP" name="updateSAP"
										value="" class="form-control"> <br />
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
						<div class="clearfix"></div>

					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn default">
							Cerrar <i class="fa fa-sign-out"></i>
						</button>
						<!--  <button type="submit" id="modificar-cuenta"
							class="btn gtd-blue-hard button-modifica-cuenta">
							Modificar <i class="fa fa-pencil-square"></i>
						</button>-->
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<div id="modal-newMercado" class="modal fade" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
       <form id="form-InsertMercado" class="form-horizontal" role="form">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">Registro Nuevo Mercado</h4>
            </div>
            <div class="modal-body">
                <div class="scroller" style="height:370px" data-always-visible="1" data-rail-visible1="1">
                    <div class="form-body">
                        <div class="form-group">
                            <label class="col-md-4 control-label">Mercado</label>
                           <div class="col-md-8">
                                <div class="input-inline input-large">
                                    <div class="input-group">
                                        <span class="input-group-addon" id="sizing-addon1">@</span>
                                        <input name="regMercado" id="regMercado" type="text" class="form-control" placeholder="Mercado"> 
                                      </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">ISO</label>
                           <div class="col-md-8">
                                <div class="input-inline input-large">
                                    <div class="input-group">
                                        <span class="input-group-addon" id="sizing-addon1">@</span>
                                        <input name="regMercado2" id="regMercado2" type="text" class="form-control" placeholder="ISO"> 
                                      </div>
                                </div>
                            </div>
                        </div>
                         <div class="form-group">
                            <label class="col-md-4 control-label">Prefijo</label>
                           <div class="col-md-8">
                                <div class="input-inline input-large">
                                    <div class="input-group">
                                        <span class="input-group-addon" id="sizing-addon1">@</span>
                                        <input name="regPf" id="regPf" type="text" class="form-control" placeholder="Prefijo"> 
                                      </div>
                                </div>
                            </div>
                        </div>
                        
                         <div class="form-group">
                            <label class="col-md-4 control-label">Regla</label>
                           <div class="col-md-8">
                                <div class="input-inline input-large">
                                    <div class="input-group">
                                        
                                        <select name="regla" class="cleanRegla form-control" id="regla">
                                         		<option value="">Seleccionar</option>
                                         		<option value="Y">Activo</option>
                                         		<option value="N">Desactivado</option>
                                         	</select>
                                      </div>
                                </div>
                            </div>
                        </div>
                           <div class="form-group">
                            <label class="col-md-4 control-label">Cliente</label>
                           <div class="col-md-8">
                                <div class="input-inline input-large">
                                    <div class="input-group">
                                        
                                        <select name="cliente" class="cleanCliente form-control" id="cliente">
                                         		<option value="">Seleccionar</option>
                                         		<option value="Y">SI</option>
                                         		<option value="N">NO</option>
                                         	</select>
                                      </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-4 control-label">Mercado</label>
                           <div class="col-md-8">
                                <div class="input-inline input-large">
                                    <div class="input-group">
                                        
                                        <select name="idMercadoPadre" class="cleanIdMercadoPadre form-control" id="idMercadoPadre">
                                         		<option value="">Seleccionar</option>
                                         		
                                         	</select>
                                      </div>
                                </div>
                            </div>
                        </div>
                        
                        
                          <div class="form-group">
                            <label class="col-md-4 control-label">Porcentaje LMR</label>
                           <div class="col-md-8">
                                <div class="input-inline input-large">
                                    <div class="input-group">
                                        <span class="input-group-addon" id="sizing-addon1">@</span>
                                        <input name="regPorcentaje" id="regPorcentaje" type="text" class="form-control" placeholder="Porcentaje"> 
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
