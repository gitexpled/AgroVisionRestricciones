var TableDatatablesAjax = function() {
	 var ID;
	var initPickers = function() {
		// init date pickers
		$('.date-picker').datepicker({
			rtl : App.isRTL(),
			autoclose : true
		});
	}

	var handleDemo1 = function() {

		var grid = new Datatable();

		grid
				.init({
					src : $("#datatable_ajax"),
					onSuccess : function(grid, response) {
						// grid: grid object
						// response: json object of server side ajax response
						// execute some code after table records loaded
					},
					onError : function(grid) {
						// execute some code on network or other general error
					},
					onDataLoad : function(grid) {
						// execute some code on ajax data load
					},
					loadingMessage : 'Loading...',
					dataTable : { // here you can define a typical datatable
									// settings from
									// http://datatables.net/usage/options

						// Uncomment below line("dom" parameter) to fix the
						// dropdown overflow issue in the datatable cells. The
						// default datatable layout
						// setup uses scrollable div(table-scrollable) with
						// overflow:auto to enable vertical scroll(see:
						// assets/global/scripts/datatable.js).
						// So when dropdowns used the scrollable div should be
						// removed.
						// "dom": "<'row'<'col-md-8 col-sm-12'pli><'col-md-4
						// col-sm-12'<'table-group-actions
						// pull-right'>>r>t<'row'<'col-md-8
						// col-sm-12'pli><'col-md-4 col-sm-12'>>",

						"bStateSave" : true, // save datatable
												// state(pagination, sort, etc)
												// in cookie.
					
						"lengthMenu" : [ [ 10, 20, 50, 100, 150, -1 ],
								[ 10, 20, 50, 100, 150, "All" ] // change per
																// page values
																// here
						],
						"pageLength" : 20, // default record count per page
						"ajax" : {
							"url" : "http://localhost:8080/eDteWeb/json/shop", // ajax
																				// source
						},
						"columnDefs" : [
								{
									"targets" : [ 7 ],
									"render" : function(data, type, full) {

										var html = "<div class='btn-group pull-right  btn-group-sm'>";

										html += "<a class='btn red btn-table pull-right button-grilla-elimina-cuenta'   data-id='"
												+ full[0]
												+ "' data-toggle='modal'>Eliminar <i class='fa fa-trash-o'></i></a>";

										html += "<a class='btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[0]
												+ "' href='#modal-modifica-cuenta'>Modificar <i class='fa fa-pencil-square'></i></a> ";

										html += "</div>";

										return html;
									}
								},
								{
									"targets" : [ 6 ],
									"render" : function(data, type, full) {

										if (full[6] == 1)
											return "Desactivado";
										else
											return "Activo";

									}
								} ],
						"order" : [ [ 1, "asc" ] ]
					// set first column as a default sort by asc

					}
				});

		// handle group actionsubmit button click
		grid.getTableWrapper().on(
				'click',
				'.table-group-action-submit',
				function(e) {
					e.preventDefault();
					var action = $(".table-group-action-input", grid
							.getTableWrapper());
					if (action.val() != "" && grid.getSelectedRowsCount() > 0) {
						grid.setAjaxParam("customActionType", "group_action");
						grid.setAjaxParam("customActionName", action.val());
						grid.setAjaxParam("id", grid.getSelectedRows());
						grid.getDataTable().ajax.reload();
						grid.clearAjaxParams();
					} else if (action.val() == "") {
						App.alert({
							type : 'danger',
							icon : 'warning',
							message : 'Please select an action',
							container : grid.getTableWrapper(),
							place : 'prepend'
						});
					} else if (grid.getSelectedRowsCount() === 0) {
						App.alert({
							type : 'danger',
							icon : 'warning',
							message : 'No record selected',
							container : grid.getTableWrapper(),
							place : 'prepend'
						});
					}
				});

		// grid.setAjaxParam("customActionType", "group_action");
		// grid.getDataTable().ajax.reload();
		// grid.clearAjaxParams();
	}

	var obtener = function() {
		$("#modal-modifica-cuenta").on(
				'show.bs.modal',
				function(e) {

					var button = $(e.relatedTarget);// Button which is clicked
					var id = button.data('id');// Get id of the button
					ID=id;
					$.ajax({
						type : 'GET',
						url : "http://localhost:8080/eDteWeb/json/user/" + id,
						data : "",
						success : function(data) {
							$("#update_usuario_nombre").val(data.nombre);
							$("#update_usuario_usuario").val(data.user);
							$("#update_usuario_apellido").val(data.apellido);
							$("#update_usuario_password").val(data.password);

							$('#update_usuario_estado').children('option:not(:first)').remove();
							$("#update_usuario_estado").append(
									'<option value="0">Activo</option>');
							$("#update_usuario_estado").append(
									'<option value="1">Desactivado</option>');
							$(
									"#update_usuario_estado option[value='"
											+ data.estado + "']").attr(
									"selected", "selected");

							/*
							 * $.each(selectValues, function(key, value) {
							 * $('#mySelect') .append($("<option></option>")
							 * .attr("value",key) .text(value)); });
							 */

						}
					});
				});
	}

	var editar = function() {

		var row = {};

		var form1 = $('#modifica-cuenta-form');

		form1
				.validate({
					errorElement : 'span', // default input error message
											// container
					errorClass : 'help-block help-block-error', // default input
																// error message
																// class
					focusInvalid : true, // do not focus the last invalid
											// input
					// ignore: "", // validate all fields including form hidden
					// input
					rules : {
						Usuario : {
							required : true,
							rangelength : [ 5, 15 ],
							alfanumerico : true
						}

					},

					messages : {

						Usuario : {
							rangelength : "No debe ser menor a 8 y mayor a 15 caracteres",
							alfanumerico : "ingrese valores alfanumericos"
						},
						RepetirPasswordCuenta : {
							equalTo : "Las contraseñas deben coincidir"
						},
						Capacidad : {
							range : "La cuenta debe tener entre 1 a 50 GB de capacidad",
							number : "Solo debe ingresar números"

						}

					},

					errorPlacement : function(error, element) { // render error
																// placement for
																// each input
																// type
						if (element.parent(".input-group").size() > 0) {
							error.insertAfter(element.parent(".input-group"));
						} else if (element.attr("data-error-container")) {
							error
									.appendTo(element
											.attr("data-error-container"));
						} else if (element.parents('.radio-list').size() > 0) {
							error.appendTo(element.parents('.radio-list').attr(
									"data-error-container"));
						} else if (element.parents('.radio-inline').size() > 0) {
							error.appendTo(element.parents('.radio-inline')
									.attr("data-error-container"));
						} else if (element.parents('.checkbox-list').size() > 0) {
							error.appendTo(element.parents('.checkbox-list')
									.attr("data-error-container"));
						} else if (element.parents('.checkbox-inline').size() > 0) {
							error.appendTo(element.parents('.checkbox-inline')
									.attr("data-error-container"));
						} else {
							error.insertAfter(element); // for other inputs,
														// just perform default
														// behavior
						}
					},

					invalidHandler : function(event, validator) { // display
																	// error
																	// alert on
																	// form
																	// submit

						App
								.alert({
									container : '#modal-modifica-cuenta .modal-body', // alerts
																						// parent
																						// container(by
																						// default
																						// placed
																						// after
																						// the
																						// page
																						// breadcrumbs)
									place : 'prepend', // append or prepent in
														// container
									type : 'danger', // alert's type
									message : 'Por favor Corrija los errores antes de continuar', // alert's
																									// message
									close : false, // make alert closable
									reset : true, // close all previouse
													// alerts first
									focus : false, // auto scroll to the alert
													// after shown
									closeInSeconds : 5
								});

						// success1.hide();
						// error1.show();
						// Metronic.scrollTo(error1, -200);
					},

					highlight : function(element) { // hightlight error inputs
						$(element).closest('.form-group').addClass('has-error'); // set
																					// error
																					// class
																					// to
																					// the
																					// control
																					// group
					},

					unhighlight : function(element) { // revert the change
														// done by hightlight
						$(element).closest('.form-group').removeClass(
								'has-error'); // set error class to the
												// control group
					},

					success : function(label) {
						label.closest('.form-group').removeClass('has-error'); // set
																				// success
																				// class
																				// to
																				// the
																				// control
																				// group
					},

					submitHandler : function(form) {

						App.blockUI({
							target : '#modal-modifica-cuenta',
							overlayColor : 'none',
							boxed : true,
							centerX : true,
							centerY : true,
							message : 'Procesando...'
						});

						// parametrosCuenta.Cuenta = cuenta;

						row.id = ID;
						row.nombre = $("#update_usuario_nombre").val();
						row.apellido = $("#update_usuario_apellido").val();
						row.user = $("#update_usuario_usuario").val();
						row.password = $("#update_usuario_password").val();
						row.estado = $("#update_usuario_estado option:selected").val();


						$.ajax({
									url : "http://localhost:8080/eDteWeb/json/put",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										App.unblockUI('#modal-modifica-cuenta');

										$('#modal-modifica-cuenta .modal-footer')
												.append(
														' <input type="hidden" data-dismiss="modal" class="btn default cerrar" value="Cancelar" />');

										var table = $('#datatable_ajax')
												.DataTable({
													bRetrieve : true
												});
										table.ajax.reload();
										// handleDemo1();
										$('#modal-modifica-cuenta .modal-footer .cerrar')
												.click();

										App.alert({
											container : $('#alert_container')
													.val(), // alerts parent
															// container
											place : 'append', // append or
																// prepent in
																// container
											type : 'success', // alert's type
											message : data.mensaje, 
											close : true,
											reset : false,
											focus : true, 
											closeInSeconds : 5,
											icon : 'fa fa-check'
										});
									},
									error : function(jqXHR, textStatus,
											errorThrown) {

										App.unblockUI('#modal-modifica-cuenta');
										$('#modal-modifica-cuenta').modal(
												'hide')

										App.alert({
											container : $('#alert_container')
													.val(), // alerts parent
															// container
											place : 'append', // append or
																// prepent in
																// container
											type : 'danger', // alert's type
											message : textStatus + ': '
													+ errorThrown, // alert's
																	// message
											close : true, // make alert
															// closable
											reset : false, // close all
															// previouse alerts
															// first
											focus : true, // auto scroll to
															// the alert after
															// shown
											closeInSeconds : 50
										// auto close after defined seconds
										// icon: 'fa fa-check' // put icon class
										// before the message
										});

									}
								});

					}

				});

	}

	return {

		// main function to initiate the module
		init : function() {

			initPickers();
			handleDemo1();

			editar();
			obtener();
			jQuery.validator.addMethod("alfanumerico",
					function(value, element) {
						return this.optional(element)
								|| /^[a-zA-Z0-9._-]+$/.test(value);
					}, "solo números ddddd");

		}

	};

}();

jQuery(document).ready(function() {
	TableDatatablesAjax.init();
});