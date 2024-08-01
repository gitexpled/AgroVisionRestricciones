<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="portlet light portlet-fit portlet-datatable bordered">

		<div class="portlet-body">
			<div class="table-container">

				<div class="table-actions-wrapper">
					<div class="btn-group">
						<button data-toggle="modal" data-target="#modal-newUser" id="sample_editable_1_new" class="btn green  btn-outline">
							Agregar Usuario <i class="fa fa-plus"></i>
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
					</div> -->
				</div>
				<table
					class="table table-striped table-bordered table-hover table-checkable"
					id="datatable_ajax">
					<thead>
						<tr role="row" class="heading">
							<th width="10%">Nombre</th>
							<th width="10%">Apellido</th>
							<th width="10%">Usuario</th>
							<th width="10%">Mail</th>
							<th width="10%">Creacion</th>
							<th width="10%">Baja</th>
							<th width="5%">Estado</th>
							<th width="10%">Actions</th>
						</tr>
						<tr role="row" class="filter">
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_nombre"></td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_apellido">
							</td>

							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_user">
							</td>
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_mail"></td>
							<td>
								<div class="input-group date date-picker margin-bottom-5"
									data-date-format="dd/mm/yyyy">
									<input type="text" class="form-control form-filter input-sm"
										readonly name="vw_creacion_from" placeholder="From"> <span
										class="input-group-btn">
										<button class="btn btn-sm default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>								<div class="input-group date date-picker"
									data-date-format="dd/mm/yyyy">
									<input type="text" class="form-control form-filter input-sm"
										readonly name="vw_creacion_to" placeholder="To"> <span
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
										readonly name="vw_baja_from" placeholder="From"> <span
										class="input-group-btn">
										<button class="btn btn-sm default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
								<div class="input-group date date-picker"
									data-date-format="dd/mm/yyyy">
									<input type="text" class="form-control form-filter input-sm"
										readonly name="vw_baja_to" placeholder="To"> <span
										class="input-group-btn">
										<button class="btn btn-sm default" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</td>
							<td><select name="vw_estado"
								class="form-control form-filter input-sm">
									<option value="">Select...</option>
									<option value="0">Activo</option>
									<option value="1">Deshabilitado</option>
							</select></td>
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
							<label class="col-md-4 control-label">Usuario: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Usuario"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="update_usuario_usuario" name="Usuario"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Nombre</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Nombre"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="update_usuario_nombre" name="UpdateUsuarioNombre" value=""
										class="form-control"> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Apellido</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Apellido"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="update_usuario_apellido" name="UpdateUsuarioApellido"
										value="" class="form-control"> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Mail</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="mail"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="update_usuario_mail" name="UpdateUsuarioMail" value=""
										class="form-control"> <br />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Contraseña</label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Contraseña"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="update_usuario_password" name="UpdateUsuarioPassword"
										value="PROTEGIDA" class="form-control"> <br />
								</div>
							</div>
						</div>



						<div class="form-group">
							<label class="col-md-4 control-label">Estado<span
								class="required">*</span></label>
							<div class="col-md-8">
								<select id="update_usuario_estado" name="UpdateUsuarioEstado"
									class="form-control">
									<option value="">Elegir</option>
								</select> <br /> <br />
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
<div id="modal-newUser" class="modal fade" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
       <form id="form-InsertUser" class="form-horizontal" role="form">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">Registro Nuevo Usuario</h4>
            </div>
            <div class="modal-body">
                <div class="scroller" style="height:330px" data-always-visible="1" data-rail-visible1="1">
                         <div class="form-body">
                             <div class="form-group">
                                 <label class="col-md-2 control-label">Nombre</label>
                                <div class="col-md-10">
                                     <div class="input-inline input-large">
                                         <div class="input-group">
                                             <span class="input-group-addon">
                                                 <i class="fa fa-user"></i>
                                             </span>
                                             <input name="regNombre" id="regNombre" type="text" class="cleanReg form-control" placeholder="Nombre"> 
                                           </div>
                                     </div>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label class="col-md-2 control-label">Apellido</label>
                                <div class="col-md-10">
                                     <div class="input-inline input-large">
                                         <div class="input-group">
                                         	<span class="input-group-addon" id="sizing-addon1">@</span>
                                         	<input name="regApellido" id="regApellido" type="text" class="cleanReg form-control" placeholder="Apellido">
                                         </div>
                                     </div>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label class="col-md-2 control-label">Usuario</label>
                                <div class="col-md-10">
                                     <div class="input-inline input-large">
                                         <div class="input-group">
                                         	<span  class="input-group-addon"><i class="fa fa-users"></i></span>
                                         	<input name="regUser" id="regUser" type="text" class="cleanReg form-control" placeholder="Usuario">
                                         </div>
                                     </div>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label class="col-md-2 control-label">Mail</label>
                                <div class="col-md-10">
                                     <div class="input-inline input-large">
                                         <div class="input-group">
                                         	 <span class="input-group-addon">
                                                 <i class="fa fa-lock fa-fw"></i>
                                             </span>
                                         	<input name="regMail" id="regMail" type="text" class="cleanReg form-control" placeholder="Contraseña">
                                         </div>
                                     </div>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label class="col-md-2 control-label">Contraseña</label>
                                <div class="col-md-10">
                                     <div class="input-inline input-large">
                                         <div class="input-group">
                                         	 <span class="input-group-addon">
                                                 <i class="fa fa-lock fa-fw"></i>
                                             </span>
                                         	<input name="regPassword" id="regPassword" type="password" class="cleanReg form-control" placeholder="Contraseña">
                                         </div>
                                     </div>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label class="col-md-2 control-label">Estado</label>
                                <div class="col-md-10">
                                     <div class="input-inline input-large">
                                         <div class="input-group">
                                         	 <span class="input-group-addon">
                                                 <i class="fa fa-lock fa-fw"></i>
                                             </span>
                                         	<select name="regEstado" class="cleanReg form-control" id="regEstado">
                                         		<option value="">Seleccionar</option>
                                         		<option value="0">Habilitado</option>
                                         		<option value="1">Deshabilitado</option>
                                         	</select>
                                         </div>
                                     </div>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label class="col-md-2 control-label">Perfil</label>
                                <div class="col-md-10">
                                     <div class="input-inline input-large">
                                         <div class="input-group">
                                         	 <span class="input-group-addon">
                                                 <i class="fa fa-lock fa-fw"></i>
                                             </span>
                                         	<select name="regPerfil" class="cleanReg form-control" id="regPerfil">
                                         		<option value="">Seleccionar</option>
                                         		<option value="1">Administrador</option>
                                         		<option value="3">Usuario</option>
                                         		<option value="4">Operaciones</option>
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






