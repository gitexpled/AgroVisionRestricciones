<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">

	<div class="portlet light portlet-fit portlet-datatable bordered">
		<div class="portlet-title">
			
			<div class="btn-group" style="float: right;">
				<a class="btn red btn-outline btn-circle" href="javascript:;"
					data-toggle="dropdown"> <i class="fa fa-share"></i> <span
					class="hidden-xs"> Herramienta </span> <i class="fa fa-angle-down"></i>
				</a>
				<ul class="dropdown-menu pull-right">
					<li><a href="/AgroVisionRestricciones/webApp/exportaExcel/1"> Export to Excel </a></li>
					
				</ul>
			</div>
		<!-- 	<div class="btn-group" style="float: right;">
				<a class="btn red btn-outline btn-circle" onclick="syncDatos()"> Sync Datos </a>
			</div> -->
		 	<div class="btn-group" style="float: right;">
				<a class="btn red btn-outline btn-circle" href="javascript:;"
					data-toggle="dropdown"> <i class="fa fa-share"></i> <span
					class="hidden-xs">Sync SAP </span> <i class="fa fa-angle-down"></i>
				</a>
				<ul class="dropdown-menu pull-right">
					<li><a href="/AgroVisionRestricciones/json/SyncSAP/1" target="_blank">ENVIO SAP</a></li>
					<!--   <li><a href="/AgroVisionRestricciones/json/SyncSAP/2" target="_blank">CLIENTE</a></li>-->
					
				</ul>
			</div>
		</div> 

		<div class="portlet-body">
			<div class="table-container">

				<div class="table-actions-wrapper">
					<span> </span> <select
						class="form-filter2 input-inline input-small input-sm btn_especie"
						id="viewEspecie" name="vw_especie">


					</select>
				</div>
				<table
					class="table table-striped table-bordered table-hover table-checkable"
					id="datatable_ajax">
					<thead>
						<tr role="row" class="heading">
							<th width="30%">Temp.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;... </th>
							<th width="15%">Sociedad</th>
							
						
							<th width="15%">Productor</th>
							<th width="15%">Especie</th>
							<th width="15%">Etapa</th>
							<th width="15%">Campo</th>
							<th width="15%">Turno</th>
							<th width="15%">Variedad</th>
							<th width="5%">CLP</th>
							
							<c:forEach var="r" items="${th}">
								<th>${r.mercado}</th>
							</c:forEach>
							<!-- ${thMercado} -->

							
						</tr>
						<tr role="row" class="filter" id="filtroMVL">
						
							<td><div class="margin-bottom-5">
									<div class="col-md-4">
										<button
											class="btn btn-sm green btn-outline filter-submit2 margin-bottom" id="buscarID">
											<i class="fa fa-search"></i>
										</button>
									</div>
									<div class="col-md-6">
										<button class="col-6 btn btn-sm red btn-outline filter-cancel">
											<i class="fa fa-times"></i>
										</button>
									</div>
								</div></td>
							<td></td>
							<td></td>
							<td></td>
							<td><input type="text" class="form-control form-filter2 input-sm" name="vw_etapa" style="width: 50px"  id="vw_etapa"></td>
							<td><input type="text" class="form-control form-filter2 input-sm" name="vw_campo"  style="width: 50px" id="vw_campo"></td>
							<td><input type="text" class="form-control form-filter2 input-sm" name="vw_turno"  style="width: 50px" id="vw_turno"></td>
							<td><input type="text" class="form-control form-filter2 input-sm" name="vw_variedad"  style="width: 100px" id="vw_variedad"></td>
							<td></td>
							<c:forEach var="r" items="${th}">
								<td></td>
							</c:forEach>
							<td>
								

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
<div id="modal-modifica-tipoProducto" class="modal fade" tabindex="-1"
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
							<label class="col-md-4 control-label">Tipo de Producto: </label>
							<div class="col-md-8">
								<div class="input-icon right">
									<i data-container="body" data-original-title="Usuario"
										class="fa fa-info-circle tooltips"></i> <input type="text"
										id="updateTipoProducto" name="updateTipoProducto" value=""
										class="form-control"> <br />
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
<div id="modal-informe" class="modal fade" tabindex="-1" aria-hidden="true">
 <div class="modal-dialog" style="width: 1200px;">
		<form id="form-InsertTipoProducto" class="form-horizontal" role="form">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title">Informe Estado</h4>
				</div>
				<div class="modal-body">
					<div class="scroller" style="height: 600px" data-always-visible="1"
						data-rail-visible1="1">
						  <div class="portlet-body form portlet-empty" id="infoRaw"> </div>
                       
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal"
						class="btn dark btn-outline">Cancelar</button>
					
				</div>
			</div>
		</form>
	</div>
</div>

