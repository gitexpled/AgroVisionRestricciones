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
							"url" : "/AgroVisionRestricciones/"+"json/limite/view", // ajax
																				// source
						},
						"columnDefs" : [
								{
									"targets" : [ 8 ],
									"render" : function(data, type, full) {
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm'>";


										html += "<a class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[8]
												+ "' href='#modal-modifica-cuenta'><i class='fa fa-pencil-square'></i></a> ";

										html += "</div>";

										return html;
									}
								},
								{
									"targets" : [ 8 ]
								} ],
						"order" : [ [ 1, "desc" ] ]
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

	var setEstado = function(){
		$("body").on("click",".changeStatus",function(e){
			var id = $(this).data("id");
			$.ajax({
				type : 'GET',
				url : "/AgroVisionRestricciones/"+"json/limite/setStatus",
				data : {
					id: id
				},
				success : function(data) {
					swal({
						  title: 'Usuario Modificado exitosamente!',
						  animation: true,
						  customClass: 'animated tada'
						})
					var table = $('#datatable_ajax').DataTable({
								bRetrieve : true
					});
					table.ajax.reload();
				}
			});
		})
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
						url : "/AgroVisionRestricciones/json/limite/" + id,
						data : "",
						success : function(data) {
							console.log(data);
							$("#updateLimite").val(data.limite);
							$("#updateCodProducto").val(data.codProducto);
							$("#updateMercado").val(data.idMercado);
							$("#updateTipoProducto").val(data.idTipoProducto);
							$('#updateFuente').val(data.idFuente);
							$('#updateCreacion').val(data.creado);
							$('#updateModificacion').val(data.modificado);
							$('#updateEspecie').val(data.idEspecie);
							console.log(data.idEspecie);
						}
					});
				});
	}

	var chargeData = function()
	{
		
		$.ajax({
			url : "/AgroVisionRestricciones/"+"json/diccionario/getAllOutFilterEqual",
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
					options += "<option value='"+val.codProducto+"'>"+val.codProducto+"</option>";
				})
				$('.productos').append(options);
				
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
		$.ajax({
			url : "/AgroVisionRestricciones/"+"json/mercado/getAllOutFilter",
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
				$('.mercados').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
		$.ajax({
			url : "/AgroVisionRestricciones/"+"json/tipoProducto/getAllOutFilter",
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
					options += "<option value='"+val.idTipoProducto+"'>"+val.tipoProducto+"</option>";
				})
				$('.tiposProductos').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
		$.ajax({
			url : "/AgroVisionRestricciones/"+"json/fuente/getAllOutFilter",
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
					options += "<option value='"+val.idFuente+"'>"+val.nombre+"</option>";
				})
				$('.fuentes').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
	}
	var editar = function() {
		var form1=$('#modifica-cuenta-form');
		var row = {};
		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						updateLimite : {
							required : true,
							range: [0,1000]
						},
						updateCodProducto : {
							required : true
						},
						updateMercado :{
							required : true
						},
						updateTipoProducto : {
							required: true
						},
						updateFuente : {
							required: true
						}

					},

					messages : {

						updateLimite : {
							required : "Este campo es obligatorio",
							range: "Ingrese valores entre 0 y 1000"
						},
						updateCodProducto : {
							required : "Este campo es obligatorio"
						},
						updateMercado :{
							required : "Este campo es obligatorio"
						},
						updateTipoProducto : {
							required : "Este campo es obligatorio"
						},
						updateFuente : {
							required : "Este campo es obligatorio"
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

						row.idLimite = ID;
						row.codProducto = $("#updateCodProducto").val();
						row.idMercado = $("#updateMercado").val();
						row.idTipoProducto = $("#updateTipoProducto").val();
						row.idFuente = $("#updateFuente").val();
						row.limite = $('#updateLimite').val();
						row.idEspecie = $('#updateEspecie').val();
						console.log(row);
						$.ajax({
							url : "/AgroVisionRestricciones/"+"json/limite/validaLimite",
							type : "POST",
							data : JSON.stringify(row),
							beforeSend : function(xhr) {
								xhr.setRequestHeader("Accept",
										"application/json");
								xhr.setRequestHeader("Content-Type",
										"application/json");
							},

							success : function(resp, textStatus, jqXHR) {
								console.log(resp);
								if(resp.mensaje=="")
								{
									$.ajax({
										url : "/AgroVisionRestricciones/"+"json/limite/put",
										type : "PUT",
										data : JSON.stringify(row),
										beforeSend : function(xhr) {
											xhr.setRequestHeader("Accept",
													"application/json");
											xhr.setRequestHeader("Content-Type",
													"application/json");
										},

										success : function(data, textStatus, jqXHR) {
											$('#modal-modifica-cuenta').modal("toggle");
											swal({
												  title: 'Limite Modificado exitosamente!',
												  animation: true,
												  customClass: 'animated tada'
												})
												$('#modifica-cuenta-form').trigger("reset");
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
								}else
								{
									$('.modal-backdrop').css("z-index","-1");
									$('#modal-modifica-cuenta').css('display','none');
									swal({
										  type: 'error',
										  text: resp.mensaje
										}).then(function (a) {
											if(a.value){
												$('.modal-backdrop').css("z-index","1054");
												$('#modal-modifica-cuenta').css("display","block");
											}
										})
								}
							},
							error : function(jqXHR, textStatus,
									errorThrown) {
								console.log("error");

							}
						});

					}

				});

	}
	
	var insertUser = function(){
		var row = {};


		
		var form1 = $('#form-InsertLimite');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						regLimite : {
							required : true,
							alfanumerico : true,
							range: [0,1000]
						},
						regProducto : {
							required : true
						},
						regMercado: {
							required: true
						},
						regTipoProducto: {
							required: true
						},
						regFuente : {
							required: true
						}

					},

					messages : {

						regLimite : {
							required: "Este campo es obligatorio",
							alfanumerico : "ingrese valores alfanumericos",
							range: "Ingrese valores entre 0 y 1000"
						},
						regProducto : {
							required: "Este campo es obligatorio"
						},
						regMercado: {
							required: "Este campo es obligatorio"
						},
						regTipoProducto: {
							required: "Este campo es obligatorio"
						},
						regFuente : {
							required: "Este campo es obligatorio."
						}

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

						row.limite = $('#regLimite').val();
						row.codProducto = $('#regProducto').val();
						row.idMercado = $('#regMercado').val();
						row.idTipoProducto = $('#regTipoProducto').val();
						row.idFuente = $('#regFuente').val();
						row.idEspecie = $('#regEspecie').val();
						console.log(row);
						
						$.ajax({
							url : "/AgroVisionRestricciones/"+"json/limite/validaLimite",
							type : "POST",
							data : JSON.stringify(row),
							beforeSend : function(xhr) {
								xhr.setRequestHeader("Accept",
										"application/json");
								xhr.setRequestHeader("Content-Type",
										"application/json");
							},

							success : function(resp, textStatus, jqXHR) {
								console.log(resp);
								if(resp.mensaje=="")
								{
										$.ajax({
											url : "/AgroVisionRestricciones/"+"json/limite/insertLimite",
											type : "PUT",
											data : JSON.stringify(row),
											beforeSend : function(xhr) {
												xhr.setRequestHeader("Accept",
														"application/json");
												xhr.setRequestHeader("Content-Type",
														"application/json");
											},
		
											success : function(data, textStatus, jqXHR) {
												$('#modal-newLimite').modal('toggle');
											swal({
												  title: 'Limite ingresado exitosamente!',
												  animation: true,
												  customClass: 'animated tada'
												})
												$('#form-InsertLimite').trigger("reset");
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
								}else
								{
									$('#modal-newLimite').modal("toggle");
									swal({
										  type: 'error',
										  text: resp.mensaje
										}).then(function (a) {
											if(a.value){
												$('#modal-newLimite').modal("show");
											}
										})
								}
							},
							error : function(jqXHR, textStatus,
									errorThrown) {
								console.log("error");

							}
						});
						/**/
					}

				});
	}
	var cargaExcel = function() {

		var row = {};

		var form1 = $('#cargaExcel');

		form1.validate({
			errorElement : 'span', 
			errorClass : 'help-block help-block-error',
			focusInvalid : true, 
			rules : {
				file : {required : true},
				excelEspecie : {required : true},
				excelTipoProducto : {required : true},
				excelFuente : {required : true},
				

			},

			messages : {

				file : {
					required: "Seleccione el archivo"
					
				}

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

				var oMyForm = new FormData();
				  oMyForm.append("file", file.files[0]);
				  oMyForm.append("hola","hola");
				  oMyForm.append("idEspecie", $('#excelEspecie').val());
				  oMyForm.append("idTipoProducto", $('#excelTipoProducto').val());
				  oMyForm.append("idFuente", $('#excelFuente').val());

				$.ajax({
							url : "/AgroVisionRestricciones/"+"json/limite/put",
							data: oMyForm,
						    dataType: 'text',
						    processData: false,
						    contentType: false,
						    type: 'POST',
							success : function(data, textStatus, jqXHR) {
								$('#modal-crea-folio').modal('toggle');
							swal({
								  title: 'Excel Cargado exitosamente!',
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
	var create = function() {
		$("#modal-crea-folio").on(
				'show.bs.modal',
				function(e) {
					$("#file").val("");
					
				});
	}

	return {

		// main function to initiate the module
		init : function() {

			initPickers();
			handleDemo1();
			insertUser();
			editar();
			chargeData();
			setEstado();
			obtener();
			create();
			cargaExcel();
			jQuery.validator.addMethod("alfanumerico",
					function(value, element) {
						return this.optional(element)
								|| /^[a-zA-Z0-9._-]+$/.test(value);
					}, "solo números ddddd");
		}

	};

}();

jQuery(document).ready(function() {
	$.ajax({
		url : "/AgroVisionRestricciones/"+"json/especie/getAllOutFilter",
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
				options += "<option value='"+val.idEspecie+"'>"+val.especie+"</option>";
			})
		
			$('.especies').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
	TableDatatablesAjax.init();
});