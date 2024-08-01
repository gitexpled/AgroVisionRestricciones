<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="portlet light portlet-fit portlet-datatable bordered">

		<div class="portlet-body">
			<div class="table-container">

					<div class="table-actions-wrapper">
					<div class="btn-group">
						
						
					</div>
				</div>
				<table
					class="table table-striped table-bordered table-hover table-checkable"
					id="datatable_ajax">
					<thead>
						<tr role="row" class="heading">
							<th width="20%">Ingr. Activo informe</th>
							<th width="20%">N° errores</th>
							
						
							<th width="20%">Actions</th>
						</tr>
						<tr role="row" class="filter">
							
							<td><input type="text"
								class="form-control form-filter input-sm" name="vw_codProducto">
							</td>
							
							
							<td></td>
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
											<i class="fa fa-times"></i> Borrar Filtros
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

<div id="modal-update" class="modal fade" tabindex="-1" aria-hidden="true"  >
    <div class="modal-dialog" style="width: 900px;">
       <form id="form-update" class="horizontal-form" role="form">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">Corrección</h4>
            </div>
            <div class="modal-body">
                <div class="scroller" style="height:400px; " data-always-visible="1" data-rail-visible1="1">
                         <div class="form-body">
                         
							
								<div class="row">
									<div class="col-md-5">
										<div class="form-group">
											<label class="control-label">Ingr. Activo informe</label> <input
												type="text" name="codProducto" disabled="disabled" id="codProducto" class="form-control"
												placeholder="codProducto"> 

										</div>
									</div>
								
									<!--/span-->
									<div class="col-md-5">
										<div class="form-group">
											<label class="control-label">Ingr. Activo real</label> 
												<select name="codProductoNew" class="codProducto form-control" id="codProductoNew">
                                       		<option value="">Seleccionar</option>
                                      </select>

										</div>
									</div>
									<!--/span-->
								</div>


						


						</div>
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