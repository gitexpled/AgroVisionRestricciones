<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="portlet light portlet-fit portlet-datatable bordered">

		<div class="portlet-body">
			<div class="table-container">

				<div class="table-actions-wrapper">
					<div class="btn-group">
						<button data-toggle="modal" data-target="#modal-newProductor" id="sample_editable_1_new" class="btn green  btn-outline">
							Agregar Bloqueo <i class="fa fa-plus"></i>
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
							<th width="10%">Cód Etapa</th>
							<th width="10%">Cód Campo</th>
							<th width="10%">Cód Variedad</th>
							<th width="10%">Cód Mercado</th>
							<th width="10%">Comentario</th>
							<th width="10%">Creado</th>
							<th width="10%">Estado</th>
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
								class="form-control form-filter input-sm" name="vw_codCampo"></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codVariedad"></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codMercado"></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codComentario"></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_creado">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_estado"></td>
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
									<select name="codProductorUpdate" class="codProductor form-control" id="codProductorUpdate">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Cod. Etapa</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<select name="codParcelaUpdate" class="codParcela form-control" id="codParcelaUpdate">
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
							<label class="col-md-4 control-label">Comentario</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<input name="comentario" id="comentarioUpdate" type="text" class="form-control" placeholder="Comentario">
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
                <h4 class="modal-title">Registro Nuevo Bloqueo</h4>
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
                            <label class="col-md-4 control-label">Campo</label>
                           <div class="col-md-8">
                                <div class="input-icon input-large">
                                    <div class="input-group">
                                   <select name="codCampo" class="codCampo form-control" id="codCampo">
                                       		<option value="">Seleccionar</option>
                                      </select>
                                      </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">Cod. Variedad</label>
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
                           <div class="col-xs-12 col-sm-12 col-md-12">
                            <label class="col-md-4 control-label">Mercado</label>
                           <div class="col-xs-8 col-sm-8 col-md-8">
                                   <select name="idMercado" class="mercado form-control select-multiple" id="idMercado">
<!--                                        		<option value="">Seleccionar</option> -->
                                      </select>
                            </div>
                        </div>
                        </div>
                        <div class="form-group">
                           <label class="col-md-4 control-label">Comentario</label>
                           <div class="col-md-8">
                                <div class="input-icon input-large">
                                    <div class="input-group">
                                   
                                        	<input name="comentario" id="idComentario" type="text" class="form-control " placeholder="Comentario">
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
