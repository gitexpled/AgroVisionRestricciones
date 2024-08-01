<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="portlet light portlet-fit portlet-datatable bordered">

		<div class="portlet-body">
			<div class="table-container">

					<div class="table-actions-wrapper">
					<!--  <div class="btn-group">
						<button data-toggle="modal" data-target="#modal-insert" id="modal-new" class="btn green  btn-outline">
							Agregar Bloqueo <i class="fa fa-plus"></i>
						</button>
					</div>-->
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
							<th width="1%">Id</th>
							<th width="20%">Cod. Productor</th>
							<th width="20%">Mercado</th>
							<th width="20%">Especie</th>
							<th width="10%">Bloqueo</th>
				
							<th width="20%">Action</th>
						</tr>
						<tr role="row" class="filter">
							<td></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name=vw_codProductor>
							</td>
							<td>
								 <select name="vw_idMercado" class="form-control form-filter input-sm mercado" id="vw_idMercado" >
                                       		<option value="">Seleccionar</option>
                                       		<option value="N">No Exporta</option>
                                       		<option value="Y">Exporta</option>
                                      </select>
							</td>
								
								<td><input type="text"
								class="form-control form-filter input-sm" name="vw_especie">
							</td>
							<td>
								 <select name="vw_b" class="form-control form-filter input-sm" id="vw_b" >
                                       		<option value="">Seleccionar</option>
                                       		<option value="N">No Exporta</option>
                                       		<option value="Y">Exporta</option>
                                      </select>
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
<!-- formulario insert -->
<div id="modal-insert" class="modal fade" tabindex="-1" aria-hidden="true"  >
    <div class="modal-dialog" style="width: 900px;">
       <form id="form-new" class="horizontal-form" role="form">
        <div class="modal-content">
				<div class="form-body">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h2 class="modal-title"></h2>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-md-4 control-label">Productor: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									 <select name="codProductor" class="codProductor form-control" id="codProductor">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Mercado: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									 <select name="idMercado" class="mercado form-control" id="idMercado">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Especie: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									 <select name="idEspecie" class="especie form-control" id="idEspecie">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label">Comentario</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<textarea 
										id="comentario" name="comentario"
										 class="form-control"> </textarea><br />
								</div>
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-4 control-label">bloqueo: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									 <select name="b" class="b form-control" id="b">
                                       		<option value="">Seleccionar</option>
                                       		<option value="N">Bloqueado</option>
                                       		<option value="Y">No Bloqueado</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Estado<span
								class="required">*</span></label>
							<div class="col-md-8">
								<select id="activo" name="activo"
									class="form-control">
									<option value="Y">Activo</option>
								</select> <br /> <br />
							</div>
						</div>




						<div class="clearfix"></div>

					</div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline">Cancelar</button>
                <button type="submit" class="btn green">Guardar</button>
            </div>
        </div>
        </div>
       </form>
    </div>
</div>



<!-- formulario update -->
<div id="modal-update" class="modal fade" tabindex="-1" aria-hidden="true"  >
    <div class="modal-dialog" style="width: 900px;">
       <form id="form-update" class="horizontal-form" role="form">
        <div class="modal-content">
				<div class="form-body">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h2 class="modal-title"></h2>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-md-4 control-label">Productor: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									 <select name="codProductor" class="codProductor form-control updateForm" id="codProductoru"  disabled="disabled">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Mercado: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									 <select name="idMercado" class="mercado form-control" id="idMercadou"   disabled="disabled">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Especie: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									 <select name="idEspecie" class="especie form-control updateForm" id="idEspecieu"  disabled="disabled">
                                       		<option value="">Seleccionar</option>
                                      </select> <br />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label">Comentario</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<textarea 
										id="comentariou" name="comentario"
										 class="form-control updateForm"> </textarea><br />
								</div>
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-4 control-label">bloqueo: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									 <select name="b" class="b form-control updateForm" id="bu" >
                                       		<option value="">Seleccionar</option>
                                       		<option value="N">No Exporta</option>
                                       		<option value="Y">Exporta</option>
                                      </select> <br />
								</div>
							</div>
						</div>
						<
						
						
						<div class="form-group">
							<label class="col-md-4 control-label">Usuario</label>
							<div class="col-md-8">
								<input id="usuariou" name="usuario" disabled="disabled"
									class="form-control updateForm" />
									 <br />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">creado</label>
							<div class="col-md-8">
								<input id="creadou" name="creado" disabled="disabled"
									class="form-control updateForm" />
									 <br />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">modificado</label>
							<div class="col-md-8">
								<input id="modificadou" name="modificado" disabled="disabled"
									class="form-control updateForm" />
									 <br />
							</div>
						</div>




						<div class="clearfix"></div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline">Cancelar</button>
                <button type="submit" class="btn green">Guardar</button>
            </div>
        </div>
        </div>
       </form>
    </div>
</div>