var TableDatatablesAjax = function() {
	 var ID;
	var initPickers = function() {
		// init date pickers
		$('.date-picker').datepicker({
			rtl : App.isRTL(),
			autoclose : true
		});
	}
	function cargarRoles(selector, selectedId = null) {
	    $.ajax({
	        url: "/AgroVisionRestricciones/json/roles",
	        type: "GET",
	        dataType: "json",
	        success: function (data) {
	            $(selector).empty().append('<option value="">Seleccionar</option>');
	            $.each(data, function (i, role) {
	                const selected = role.id == selectedId ? 'selected' : '';
	                $(selector).append(`<option value="${role.id}" ${selected}>${role.name}</option>`);
	            });
	        },
	        error: function (jqXHR, textStatus, errorThrown) {
	            console.error("Error al cargar los roles:", textStatus, errorThrown);
	            swal("Error", "No se pudieron cargar los perfiles. Intente nuevamente.", "error");
	        }
	    });
	}
	cargarRoles("#regPerfil");
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
							"url" : "/AgroVisionRestricciones/"+"json/user/view", // ajax
																				// source
						},
						"columnDefs" : [
								{
									"targets" : [ 7 ],
									"render" : function(data, type, full) {
										var html = "<div class='btn-group pull-right  btn-group-sm'>";

										html += "<a onclick='' class='col-md-6 btn red btn-table pull-right button-grilla-elimina-cuenta changeStatus'   data-id='"
												+ full[7]
												+ "' data-toggle='modal'><i class='fa fa-trash-o'></i></a>";

										html += "<a class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[7]
												+ "' href='#modal-modifica-cuenta'><i class='fa fa-pencil-square'></i></a> ";

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
	
	var obtener = function() {
	
		$("#modal-modifica-cuenta").on(
				'show.bs.modal',
				function(e) {

					var button = $(e.relatedTarget);// Button which is clicked
					var id = button.data('id');// Get id of the button
					ID=id;
					$.ajax({
						type : 'GET',
						url : "/AgroVisionRestricciones/json/user/" + id,
						data : "",
						success : function(data) {
							console.log("data===>",data)
							$("#update_usuario_nombre").val(data.nombre);
							$("#update_usuario_usuario").val(data.user);
							$("#update_usuario_apellido").val(data.apellido);
							$("#update_usuario_password").val(data.password);
							$("#update_usuario_mail").val(data.mail);
							$('#update_usuario_estado').children('option:not(:first)').remove();
							$("#update_usuario_estado").append('<option value="0">Activo</option>');
							$("#update_usuario_estado").append('<option value="1">Desactivado</option>');
							$("#update_usuario_estado option[value='"+ data.estado + "']").attr("selected", "selected");

							$('#update_regPerfil').children('option:not(:first)').remove();
							data.roles.forEach(item=>{
								$("#update_regPerfil").append('<option value="'+item.id+'">'+item.name+'</option>');
							})
							$("#update_regPerfil option[value='"+ data.idPerfil + "']").attr("selected", "selected");
							
							
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

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						Usuario : {
							required : true,
							rangelength : [ 4, 50 ],
							alfanumerico : true
						},
						UpdateUsuarioNombre : {
							required : true,
							rangelength : [ 4, 50 ],
							alfanumerico : true
						},
						UpdateUsuarioApellido :{
							required : true,
							rangelength : [ 4, 50 ],
							alfanumerico : true
						},
						UpdateUsuarioPassword : {
							required: true,
							rangelength : [4,50]
						},
						UpdateUsuarioEstado : {
							required: true
						},
						UpdateUsuarioMail:{
							required: true,
							email: true
						}

					},

					messages : {

						Usuario : {
							required : "Este campo es obligatorio",
							rangelength : "Debe ser mayor a 4 y menor a 50",
							alfanumerico : "Ingrese sólo valores alfanumericos"
						},
						UpdateUsuarioNombre : {
							required : "Este campo es obligatorio",
							rangelength : "Debe ser mayor a 4 y menor a 50",
							alfanumerico : "Ingrese sólo valores alfanumericos"
						},
						UpdateUsuarioApellido :{
							required : "Este campo es obligatorio",
							rangelength : "Debe ser mayor a 4 y menor a 50",
							alfanumerico : "Ingrese sólo valores alfanumericos"
						},
						UpdateUsuarioPassword : {
							required : "Este campo es obligatorio",
							rangelength : "Debe ser mayor a 4 y menor a 50"
						},
						UpdateUsuarioEstado : {
							required : "Este campo es obligatorio"
						},
						UpdateUsuarioMail:{
							required: "Este campo es obligatorio",
							email: "Debe ingresar un correo valido"
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

						row.id = ID;
						row.nombre = $("#update_usuario_nombre").val();
						row.apellido = $("#update_usuario_apellido").val();
						row.user = $("#update_usuario_usuario").val();
						row.password = $("#update_usuario_password").val();
						row.estado = $("#update_usuario_estado option:selected").val();
						row.mail = $('#update_usuario_mail').val();
						row.idPerfil = $("#update_regPerfil option:selected").val();

						$.ajax({
									url : "/AgroVisionRestricciones/"+"json/user/put",
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
											  title: 'Usuario Modificado exitosamente!',
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
	
	var insertUser = function(){
		var row = {};

		
		
		var form1 = $('#form-InsertUser');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						regNombre : {
							required : true,
							rangelength : [ 4, 50 ],
							alfanumerico : true
						},
						regApellido : {
							required : true,
							rangelength : [ 4, 50 ],
							alfanumerico : true
						},
						regUser: {
							required: true,
							rangelength : [4, 50],
							remote: {
								url: "/AgroVisionRestricciones/"+"json/user/validaUserName",
								type: "POST",
								data: {
									user: function(){
										return $('#regUser').val();
									}
								}
							}
						},
						regPassword: {
							required: true,
							rangelength : [4,50]
						},
						regEstado : {
							required: true
						},
						regPerfil : {
							required: true
						},
						regMail:{
							required: true,
							email: true
						}

					},

					messages : {

						regNombre : {
							required: "Este campo es obligatorio",
							rangelength : "No debe ser menor a 4 y mayor a 50 caracteres",
							alfanumerico : "ingrese valores alfanumericos"
						},
						regApellido : {
							required: "Este campo es obligatorio",
							rangelength : "No debe ser menor a 4 y mayor a 50 caracteres",
							alfanumerico : "ingrese valores alfanumericos"
						},
						regUser: {
							required: "Este campo es obligatorio",
							rangelength : "No debe ser menor a 4 y mayor a 50",
							remote: "El usuario ya existe"
						},
						regPassword: {
							required: "Este campo es obligatorio",
							rangelength : "No debe ser menor a 4 y mayor a 50"
						},
						regEstado : {
							required: "Este campo es obligatorio."
						},
						regPerfil : {
							required: "Este campo es obligatorio"
						},
						regMail:{
							required: "Este campo es obligatorio",
							email: "Debe ingresar un mail valido"
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

						row.nombre = $('#regNombre').val();
						row.apellido = $('#regApellido').val();
						row.user = $('#regUser').val();
						row.password = $('#regPassword').val();
						row.estado = $('#regEstado').val();
						row.idPerfil = $('#regPerfil').val();
						row.mail = $('#regMail').val();
						console.log(row);

						$.ajax({
									url : "/AgroVisionRestricciones/"+"json/user/insertUser",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('.cleanReg').val("");
										$('#modal-newUser').modal('toggle');
									swal({
										  title: 'Usuario ingresado exitosamente!',
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

			initPickers();
			handleDemo1();
			insertUser();
			editar();
			setEstado();
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