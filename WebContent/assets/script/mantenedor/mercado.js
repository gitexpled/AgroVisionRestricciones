
var TableDatatablesAjax = function() {
	var handleDemo1 = function() {

		var grid = new Datatable();

		grid.init({
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
					dataTable : { 

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
							"url" : "/AgroVisionRestricciones/"+"/json/mercado/view", // ajax
																				// source
						},
						"columnDefs" : [
								{
									"targets" : [ 8 ],
									"render" : function(data, type, full) {
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm'>";


										html += "<a style='width:100%;' class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[9]
												+ "' href='#modal-modifica-mercado'><i class='fa fa-pencil-square'></i></a> ";

										html += "</div>";

										return html;
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

	}

	var chargeData= function(){
		$.ajax({
			url : "/AgroVisionRestricciones/"+"json/mercado/getAllOutFilter2",
			type : "GET",
			data : "",
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept",
						"application/json");
				xhr.setRequestHeader("Content-Type",
						"application/json");
			},

			success : function(data, textStatus, jqXHR) {
				var options = "";
				$(data).each(function(key, val){
					options += "<option value='"+val.idMercado+"'>"+val.mercado+"</option>";
				})
				$('#idMercadoPadre').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
	}
	
	var obtener = function() {
		$("#modal-modifica-mercado").on(
				'show.bs.modal',
				function(e) {

					var button = $(e.relatedTarget);// Button which is clicked
					var id = button.data('id');// Get id of the button
					ID=id;
					$.ajax({
						type : 'GET',
						url : "/AgroVisionRestricciones/"+"/json/mercado/" + id,
						data : "",
						success : function(data) {
							console.log(data)
							$("#updateMercado").val(data.mercado);
							$("#updateSAP").val(data.sap);
							$("#updateMercado2").val(data.mercado2);
							$("#updatePf").val(data.pf);
							$("#updateFeCreacion").val(data.creado);
							$("#updateModificacion").val(data.modificado);
							$('#update_regla').children('option:not(:first)').remove();
							$("#update_regla").append('<option value="N">Activo</option>');
							$("#update_regla").append('<option value="Y">Desactivado</option>');
							$("#update_regla option[value='"+ data.regla + "']").attr("selected", "selected");

						
							$('#update_productor').children('option:not(:first)').remove();
							$("#update_productor").append('<option value="N">Desactivado</option>');
							$("#update_productor").append('<option value="Y">Activo</option>');
							$("#update_productor option[value='"+ data.productor + "']").attr("selected", "selected");
							
							$('#update_retricionMolecula').children('option:not(:first)').remove();
							$("#update_retricionMolecula").append('<option value="N">Desactivado</option>');
							$("#update_retricionMolecula").append('<option value="Y">Activo</option>');
							$("#update_retricionMolecula option[value='"+ data.retricionMolecula + "']").attr("selected", "selected");
							
							$("#update_molecula").val(data.molecula);
							
							
							$('#update_restPorcentaje').children('option:not(:first)').remove();
							$("#update_restPorcentaje").append('<option value="N">Desactivado</option>');
							$("#update_restPorcentaje").append('<option value="Y">Activo</option>');
							$("#update_restPorcentaje option[value='"+ data.restPorcentaje + "']").attr("selected", "selected");
							
							$("#update_restValor").val(data.restValor);
							
							
							$('#update_restArfD').children('option:not(:first)').remove();
							$("#update_restArfD").append('<option value="N">Desactivado</option>');
							$("#update_restArfD").append('<option value="Y">Activo</option>');
							$("#update_restArfD option[value='"+ data.restArfD + "']").attr("selected", "selected");
							
							
							$('#update_restSumArfD').children('option:not(:first)').remove();
							$("#update_restSumArfD").append('<option value="N">Desactivado</option>');
							$("#update_restSumArfD").append('<option value="Y">Activo</option>');
							$("#update_restSumArfD option[value='"+ data.restSumArfD + "']").attr("selected", "selected");
							
							
							$("#update_restValorArfD").val(data.restValorArfD);
							$("#update_restPorArfD").val(data.restPorArfD);
							
							
					
							$('#update_cliente').children('option:not(:first)').remove();
							$("#update_cliente").append('<option value="N">No</option>');
							$("#update_cliente").append('<option value="Y">Si</option>');
							$("#update_cliente option[value='"+ data.cliente + "']").attr("selected", "selected");
							
							
							$("#update_porcentaje").val(data.porcentaje);
							var idMercadoPadre = data.idMercadoPadre;
							$("#update_idMercadoPadre option[value='"+idMercadoPadre+"']").attr("selected","selected");
							$('#update_idMercadoPadre').empty();
							$('#update_idMercadoPadre').append('<option value="">Seleccionar</option>');
							$.ajax({
								url : "/AgroVisionRestricciones/"+"json/mercado/getAllOutFilter2",
								type : "GET",
								data : "",
								beforeSend : function(xhr) {
									xhr.setRequestHeader("Accept",
											"application/json");
									xhr.setRequestHeader("Content-Type",
											"application/json");
								},

								success : function(data, textStatus, jqXHR) {
									var options = "";
									
									$(data).each(function(key, val){
										if(val.idMercado==idMercadoPadre)
											options += "<option value='"+val.idMercado+"'  selected='selected' >"+val.mercado+"</option>";
										else
											options += "<option value='"+val.idMercado+"'>"+val.mercado+"</option>";
									})
									
									$('#update_idMercadoPadre').append(options);
								},
								error : function(jqXHR, textStatus,
										errorThrown) {
								}
							});
					}
					});
					
				

		
				});
	}

	var editar = function() {

		var row = {};

		var form1 = $('#modifica-cuenta-form');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						updateMercado : {
							required : true,
							rangelength : [ 2, 50 ],
							alfanumerico : true,
							
							},
							updateMercado2 : {
								required : true,
								rangelength : [ 2, 50 ],
								alfanumerico : true,
								
							},
							updatePf : {
								required : true,
								rangelength : [ 1, 4 ],
								alfanumerico : true,
								
							},
							update_regla:{
								required : true,
								alfanumerico : true,
							},
							update_cliente:{
								required : true,
								alfanumerico : true,
							},
							update_idMercadoPadre : {
								required : function () {
					                return $("#update_cliente").val()=='Y';
					            }
								
								}
							,
							update_porcentaje : {
								required  : function () {
					                return $("#update_cliente").val()=='Y';
					            },
					            range: [0,100]
								
								},
								updateSAP : {
								required : true,
								rangelength : [ 3, 4 ]
								
							},
								
						},

				
					messages : {

						updateMercado : {
							required: "Este campo es obligatorio",
							rangelength : "No debe ser menor a 2 y mayor a 50 caracteres",
							alfanumerico : "ingrese valores alfanumericos",
							remote: "Este mercado ya existe"
						
						},
						update_regla:{required: "Este campo es obligatorio"},
						update_cliente:{required: "Este campo es obligatorio"},
						update_idMercadoPadre:{required: "Este campo es obligatorio"},
						update_porcentaje:{required: "Este campo es obligatorio"}


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
							error.insertAfter(element);
						}
					},

					invalidHandler : function(event, validator) { 
						App
								.alert({
									container : '#modal-modifica-cuenta .modal-body', 
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
					},

					highlight : function(element) { // hightlight error inputs
						$(element).closest('.form-group').addClass('has-error');
					},

					unhighlight : function(element) { // revert the change
														// done by hightlight
						$(element).closest('.form-group').removeClass(
								'has-error'); // set error class to the
												// control group
					},

					success : function(label) {
						label.closest('.form-group').removeClass('has-error'); 
					},

					submitHandler : function(form) {


						// parametrosCuenta.Cuenta = cuenta;

						row.mercado = $('#updateMercado').val();;
						row.mercado2 = $('#updateMercado2').val();;
						row.pf = $('#updatePf').val();;
						row.idUser = $("#idUserPefil").val();
						row.idMercado = ID;
						row.regla = $("#update_regla").val();
						row.cliente = $("#update_cliente").val();
						row.sap = $("#updateSAP").val();
						
						row.idMercadoPadre = $("#update_idMercadoPadre").val();
						row.porcentaje = $("#update_porcentaje").val();
						
						
						row.productor = $("#update_productor").val();
						row.retricionMolecula = $("#update_retricionMolecula").val();
						row.molecula = $("#update_molecula").val();
						
						row.restPorcentaje = $("#update_restPorcentaje").val();
						row.restValor = $("#update_restValor").val();
						
						
						row.restArfD = $("#update_restArfD").val();
						row.restSumArfD = $("#update_restSumArfD").val();
						row.restValorArfD = $("#update_restValorArfD").val();
						row.restPorArfD = $("#update_restPorArfD").val();
						
						$.ajax({
									url : "/AgroVisionRestricciones/"+"/json/mercado/put",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-modifica-mercado').modal("toggle");
										swal({
											  title: 'Mercado Modificado exitosamente!',
											  animation: true,
											  customClass: 'animated tada'
											})
										var table = $('#datatable_ajax')
												.DataTable({
													bRetrieve : true
												});
										table.ajax.reload();
										
									},
									error : function(jqXHR, textStatus,
											errorThrown) {
									}
								});

					}

				});

	}
	
	var insertMercado = function(){
		var row = {};

		
		
		var form1 = $('#form-InsertMercado');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						regMercado : {
							required : true,
							rangelength : [ 2, 50 ],
							alfanumerico : true,
							remote: {
								url: "/AgroVisionRestricciones/"+"json/mercado/validaMercadoName",
								type: "POST",
								data: {
									mercado: function(){
										return $('#regMercado').val();
									}
								}
							}
						},
						regMercado2 : {
							required : true,
							rangelength : [ 2, 50 ],
							alfanumerico : true,
							
							},
						regPf : {
							required : true,
							rangelength : [ 1, 4 ],
							alfanumerico : true,
							
							},
							regla : {
								required : true,
							
								
								}
							,
							cliente : {
								required : true,
							
								
								}
							,
							idMercadoPadre : {
								required : function () {
					                return $("#cliente").val()=='Y';
					            }
							
								
								}
							,
							regPorcentaje : {
								required : function () {
					                return $("#cliente").val()=='Y';
					            },
								
								range: [0,100]
							
								
								}
						

					},

					messages : {

						regMercado : {
							required: "Este campo es obligatorio",
							rangelength : "No debe ser menor a 2 y mayor a 50 caracteres",
							alfanumerico : "ingrese valores alfanumericos",
							remote: "Este mercado ya existe"
						},
						regla:{required: "Este campo es obligatorio"},
						cliente:{required: "Este campo es obligatorio"},
						idMercadoPadre:{required: "Este campo es obligatorio"},
						cliente:{required: "Este campo es obligatorio"}

					},

					errorPlacement : function(error, element) { 
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
					},

					highlight : function(element) { // hightlight error inputs
						$(element).closest('.form-group').addClass('has-error'); 
					},

					unhighlight : function(element) { 
						$(element).closest('.form-group').removeClass(
								'has-error');
					},

					success : function(label) {
						label.closest('.form-group').removeClass('has-error'); 
					},

					submitHandler : function(form) {

						row.mercado = $('#regMercado').val();
						row.idUser = $('#idUserPefil').val();
						row.mercado2 = $('#regMercado2').val();
						row.pf = $('#regPf').val();
						row.regla = $('#regla').val();
						row.cliente = $('#cliente').val();
						
						row.idMercadoPadre = $('#regIdMercadoPadre').val();
						row.porcentaje = $('#regPorcentaje').val();
						console.log(row);

						$.ajax({
									url : "/AgroVisionRestricciones/"+"/json/mercado/insertMercado",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-newMercado').modal('toggle');
									swal({
										  title: 'Mercado ingresado exitosamente!',
										  animation: true,
										  customClass: 'animated tada'
										})
										var table = $('#datatable_ajax')
												.DataTable({
													bRetrieve : true
												});
										table.ajax.reload();
									},
									error : function(jqXHR, textStatus,
											errorThrown) {
										console.log("error");

									}
								});
					}

				});
	}

	return {

		// main function to initiate the module
		init : function() {

			handleDemo1();
			insertMercado();
			editar();
			obtener();
			chargeData();
			jQuery.validator.addMethod("alfanumerico",
					function(value, element) {
						return this.optional(element)
								|| /[a-zA-Z]/.test(value);
					}, "solo números ddddd");
		}

	};

}();

jQuery(document).ready(function() {
	TableDatatablesAjax.init();
});