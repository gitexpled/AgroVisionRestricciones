var TableDatatablesAjax = function() {
	var handleDemo1 = function() {
		$.fn.dataTable.ext.errMode = 'none';
		var grid = new Datatable();
		grid.init({
					src : $("#datatable_ajax"),
					onSuccess : function(grid, response) {
						console.log(response.data)
						for(var i = 0; i < response.data.length; i++){
							response.data[i][3] = response.data[i][3] == "-1"? "Todos": $variedad[response.data[i][3]];
							response.data[i][4] = response.data[i][4] == "-1"? "Todos": $mercado[response.data[i][4]];
							console.log(response.data[i][3])
//							for(var x = 0; x < response[i].length; x++){
//								response[i][x]
//							}
						}
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
							"url" : "/AgroVisionRestricciones/"+"json/bloqueoParcela/view", // ajax
																				// source
						},
						"columnDefs" : [
								{
									"targets" : [8],
									"render" : function(data, type, full) {
										console.log(data)
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm'>";

										if(full[7] == 'Activo'){
												html += "<a onclick='' class='col-md-6 btn red btn-table pull-right button-grilla-elimina-cuenta drop'   data-id='"
												+ full[0]
												+ "' data-toggle='modal'><i class='fa fa-trash-o'></i></a>";

										} else {
												//html += "<a  class='col-md-6 btn red btn-table pull-right button-grilla-modifica-cuenta'   data-id='"
												//+ full[0]
												//+ "' data-toggle='modal'><i class='fa fa-trash-o'></i></a>";

												html += "<a  class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta drop'  data-id='"
												+ full[0]
												+ "' data-toggle='modal'><i class='fa fa-pencil-square'></i></a> ";
										}
										
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
		// grid.setAjaxParam("customActionType", "group_action");
		// grid.getDataTable().ajax.reload();
		// grid.clearAjaxParams();
	}

	var setEstado = function(){
		$("body").on("click",".changeStatus",function(e){
			var id = $(this).data("id");
			$.ajax({
				type : 'GET',
				url : "/AgroVisionRestricciones/"+"json/user/setStatus",
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
	var limpiar = function() {
		$("#modal-newProductor").on(
				'show.bs.modal',
				function(e) {

					 $('#codParcela').val("");
					$('#codVariedad').val("");
					
				
				});
	}

	var obtener = function() {
		$("#modal-modifica-productor").on(
				'show.bs.modal',
				function(e) {

					var button = $(e.relatedTarget);// Button which is clicked
					var id = button.data('id');// Get id of the button
					ID=id;
					$.ajax({
						type : 'GET',
						url : "/AgroVisionRestricciones/json/bloqueoParcela/" + id,
						data : "",
						success : function(data) {
							console.log(data);
							$("#codParcelaUpdate").val(data.codParcela);
							$("#codProductorUpdate").val(data.codProductor);
							$("#codTurnoUpdate").val(data.codTurno);
							
							$("#updateId").val(id);
							
								
							
							$.ajax({
								type: 'GET',
								url : "/AgroVisionRestricciones/json/user/"+data.idUser,
								data:"",
								success: function(user){
									$('#updateUserMod').val(user.user);
								}
							})
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
						
						codParcela : {
							required : true,
							rangelength : [ 4, 50 ]
						},
						idMercado : {
							required : true,
							rangelength : [ 4, 50 ]
						}

					},

					messages : {

						updateNombre : {
							required : "Este campo es obligatorio",
							rangelength : "Debe ser mayor a 5 y menor a 50",
							alfanumerico : "Ingrese sólo valores alfanumericos"
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

						row.idVariedad = $('#updatecodVariedad').val();;
						row.codParcela = $("#updatecodParcela").val();

						row.id = $('#updateId').val();;
						
						$.ajax({
									url : "/AgroVisionRestricciones/"+"json/bloqueoParcela/put",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-modifica-productor').modal("toggle");
										swal({
											  title: 'Productor Modificado exitosamente!',
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
	
	var insertProductor = function(){
		var row = {};
		
		var form1 = $('#form-InsertProductor');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						codProductor : {
							required : true
						},
						codParcela : {
							required : true,
							
						},
						codTurno : {
							required : true,
							rangelength : [ 2, 4 ]
						},
						nombre : {
							required : true,
							rangelength : [ 4, 30 ]
						},
						idMercado : {
							required : true,
							
						},

					},

					messages : {
						codProductor : {
							required: "Este campo es obligatorio"
						},
						codParcela : {
							required: "Este campo es obligatorio"
							
						},
						idMercado : {
							required: "Este campo es obligatorio"
							
						},
						codTurno : {
							required: "Este campo es obligatorio",
							rangelength : "No debe ser menor a 2 y mayor a 4 caracteres",
							alfanumerico : "ingrese valores alfanumericos"
						},
						nombre : {
							required: "Este campo es obligatorio",
							rangelength : "No debe ser menor a 4 y mayor a 30 caracteres",
							alfanumerico : "ingrese valores alfanumericos"
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

						debugger;
						
//						row.codParcela = $('#codParcela').val();
//						row.codProductor = $('#codProductor').val();
//						row.comentario = $('#idComentario').val();
//						row.idVariedad = $('#idVariedad').val();
//						row.idMercado = $('#idMercado').val();
						//row.estado = $('#nombre').val();
						var mercados = $('#idMercado').val();
						var arrayV = [];
						for(var i = 0; i < mercados.length; i++){
							var row = {
								codProductor: $('#codProductor').val(),
								codParcela: $('#codParcela').val(),
								comentario: $('#idComentario').val(),
								estado: 1,
								creado: dateHoy(),
								idVariedad: $('#idVariedad').val(),
								idMercado: mercados[i],
							}
							arrayV.push(row)
						}
						var input = {
							TABLE: "bloqueoParcela",
							VALUES: arrayV
						}
						$.ajax({
									url : "/AgroVisionRestricciones/json/Replace",
									type : "PUT",
									data : JSON.stringify(input),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-newProductor').modal('toggle');
									swal({
										  title: 'turno ingresado exitosamente!',
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

	var drop = function(){
		$("body").on("click",".drop",function(e){
			var id = $(this).data("id");
			$.ajax({
				type : 'GET',
				url : "/AgroVisionRestricciones/"+"json/bloqueoParcela/drop",
				data : {
					id: id
				},
				success : function(data) {
					swal({
						  title: data.mensaje,
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
	return {

		// main function to initiate the module
		init : function() {
			drop();
			limpiar();
			handleDemo1();
			insertProductor();
			editar();
			setEstado();
			obtener();
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
	$.ajax({
		url : "/AgroVisionRestricciones/"+"json/productor/getAllOutFilter",
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
				options += "<option value='"+val.codigo+"'>"+val.nombre+"</option>";
			})
		
			$('.codProductor').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
	$.ajax({
		url : "/AgroVisionRestricciones/"+"json/parcela/getAllOutFilter",
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
				options += "<option value='"+val.codigo+"'>"+val.nombre+"</option>";
			})
		
			$('.codParcela').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
	$.ajax({
		url : "/AgroVisionRestricciones/"+"json/variedad/getAllOutFilter",
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
				options += "<option value='"+val.idVariedad+"'>"+val.nombre+"</option>";
			})
		
			$('.codVariedad').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
});


$('#codProductor').on('change', function() {
	$('.codParcela').empty();
	$('.codParcela').append('<option value="">Seleccionar</option>');
	$.ajax({
		url : "/AgroVisionRestricciones/"+"json/parcela/getAllByProductor/"+this.value,
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
				options += "<option value='"+val.codigo+"'>"+val.nombre+"</option>";
			})
		
			$('.codParcela').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });
var $mercado = [];
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
		options += "<option value='-1'>TODO</option>";
		$(data).each(function(key, val){
			$mercado[val.idMercado] = val.mercado;
			options += "<option value='"+val.idMercado+"'>"+val.mercado+"</option>";
		})
		$('.mercado').append(options);
	},
	error : function(jqXHR, textStatus,
			errorThrown) {
	}
});
var $variedad = [];
$.ajax({
	url : "/AgroVisionRestricciones/"+"json/variedad/getAllOutFilter",
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
		options += "<option value='-1'>TODO</option>";
		$(data).each(function(key, val){
			$variedad[val.cod] = val.nombre;
			options += "<option value='"+val.cod+"'>"+val.cod+" | "+val.nombre+"</option>";
		})
	
		$('.idVariedad').append(options);
	},
	error : function(jqXHR, textStatus,
			errorThrown) {
	}
});