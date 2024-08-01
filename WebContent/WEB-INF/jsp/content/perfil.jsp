
<div class="portlet light bordered">
	<div class="portlet-title">
		<div class="caption">
			<i class="icon-equalizer font-red-sunglo"></i> <span
				class="caption-subject font-red-sunglo bold uppercase">Mi Perfil</span>
		</div>
		
	</div>
<form action="#" id="updatePerfil-form" class="form-horizontal" role="form">
		<!-- BEGIN FORM-->
			<div class="form-body">
				<div class="form-group">
                      <label class="col-md-2 control-label">Usuario</label>
                     <div class="col-md-10">
                          <div class="input-inline input-large">
                              <div class="input-group">
                              	<span  class="input-group-addon"><i class="fa fa-users"></i></span>
                              	<input readOnly name="user" id="user" type="text" class="form-control" placeholder="Usuario">
                              </div>
                          </div>
                      </div>
                  </div>
				<div class="form-group">
                      <label class="col-md-2 control-label">Nombre</label>
                     <div class="col-md-10">
                          <div class="input-inline input-large">
                              <div class="input-group">
                                  <span class="input-group-addon">
                                      <i class="fa fa-user"></i>
                                  </span>
                                  <input name="nombre" id="nombre" type="text" class="form-control" placeholder="Nombre"> 
                                </div>
                          </div>
                      </div>
                  </div>
                  <div class="form-group">
                      <label class="col-md-2 control-label">Apellido</label>
                     <div class="col-md-10">
                          <div class="input-inline input-large">
                              <div class="input-group">
                              	<span class="input-group-addon">
                                      <i class="fa fa-user"></i>
                                  </span>
                              	<input name="apellido" id="apellido" type="text" class="form-control" placeholder="Apellido">
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
                                      <i class="fa fa-envelope"></i>
                                  </span>
                              	<input name="mail" id="mail" type="text" class="form-control" placeholder="Mail">
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
                              	<input name="password" id="password" type="text" class="form-control" placeholder="Contraseña">
                              </div>
                          </div>
                      </div>
                  </div>     
			</div>
			<div class="form-actions">
				<div class="row">
					<div class="col-md-offset-3 col-md-4">
						<button type="submit" class="btn green">Modificar</button>
					</div>
				</div>
			</div>
</form>
<!-- END FORM-->
</div>