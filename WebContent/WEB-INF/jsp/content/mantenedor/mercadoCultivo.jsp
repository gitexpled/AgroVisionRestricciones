<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="portlet light portlet-fit portlet-datatable bordered">

		<div class="portlet-body">
			<div class="table-container">

				<div class="table-actions-wrapper">
					<div class="btn-group">
						<button data-toggle="modal" data-target="#modal-newProductor" id="sample_editable_1_new" class="btn green  btn-outline">
							Agregar Mercado Cultivo <i class="fa fa-plus"></i>
						</button>
					</div>
					
				</div>
				<table
					class="table table-striped table-bordered table-hover table-checkable"
					id="datatable_ajax">
					<thead>
						<tr role="row" class="heading">
							<th width="10%">ID</th>
							<th width="10%">Cultivo</th>
							<th width="10%">Mercado</th>
							<th width="10%">Actions</th>
						</tr>
						<tr role="row" class="filter">
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_ID"></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_cultivo">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_mercado">
							</td>
							
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
							<label class="col-md-4 control-label">Cultivo</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<select name="updateCultivo" class="cultivo form-control" id="updateCultivo">
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
                <h4 class="modal-title">Registro Mercado Cultivo</h4>
            </div>
            <div class="modal-body">
                <div class="scroller" style="height:350px" data-always-visible="1" data-rail-visible1="1">
                    <div class="form-body">

                       <div class="form-group">
                            <label class="col-md-4 control-label">Cultivo</label>
                           <div class="col-md-8">
                                <div class="input-icon input-large">
                                    <div class="input-group">
                                   <select name="cultivo" class="cultivo form-control" id="cultivo">
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
                                   <select name="mercado" class="mercado form-control" id="mercado">
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