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
							"url" : "/AgroVisionRestricciones/"+"json/habilitacionComercial/view", // ajax
																				// source
						},
						"columnDefs" : [
								{
									"targets" : [ 9 ],
									"render" : function(data, type, full) {
										var html = "<div class='btn-group pull-left  btn-group-sm'>";

										html += "<a onclick='' class='col-md-2 btn red btn-table pull-right button-grilla-elimina-cuenta changeStatus'   data-id='"
												+ full[0]
												+ "' data-toggle='modal'><i class='fa fa-trash-o'></i></a>";

										html += "<a class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[0]
												+ "' href='#modal-modifica'><i class='fa fa-pencil-square'></i></a> ";

										html += "</div>";

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

	var setEstado = function(){
		$("body").on("click",".changeStatus",function(e){
			var id = $(this).data("id");
			$.ajax({
				type : 'GET',
				url : "/AgroVisionRestricciones/"+"json/habilitacionComercial/delete",
				data : {
					id: id
				},
				success : function(data) {
					swal({
						  title: 'Registro eliminado exitosamente!',
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

					$('#codProductor').val("");
					$('#codEtapa').val("");
					$('#codCampo').val("");
					$('#idVariedad').val("");
					$('#codMercado').val("");
					$('#Habilitado').val("");
					
					console.log("limpio todo OK");
					
				
				});
	}

	var obtener = function() {
		$("#modal-modifica").on(
				'show.bs.modal',
				function(e) {

					var button = $(e.relatedTarget);// Button which is clicked
					var id = button.data('id');// Get id of the button
					ID=id;
					$.ajax({
						type : 'GET',
						url : "/AgroVisionRestricciones/json/habilitacionComercial/" + id,
						data : "",
						success : function(data) {
							console.log(data)
							var codProductor = data.productor;
							var codEtapa = data.etapa;
							var codCampo = data.campo;
							var idVariedad = data.idVariedad;
							var mercado = data.mercado;
							var habilitado = data.habilitado;
						
							
							$("#updateId").val(id);
							
							$('#updateHabilitado').empty();
							$("#updateHabilitado").append('<option value="Y">Si</option>');
							$("#updateHabilitado").append('<option value="N">No</option>');
							$("#updateHabilitado option[value='"+ habilitado + "']").attr("selected", "selected");
							
								
							$.ajax({
							url: "/AgroVisionRestricciones/" + "json/productor/getAllOutFilter",
							type: "GET",
							data: "",
							beforeSend: function (xhr) {
								xhr.setRequestHeader("Accept",
									"application/json");
								xhr.setRequestHeader("Content-Type",
									"application/json");
							},

							success: function (data, textStatus, jqXHR) {
								var options = "";

								$(data).each(function (key, val) {
									if (val.codigo == codProductor)
										options += "<option value='" + val.codigo + "'  selected='selected' >" + val.codigo + " | " + val.nombre + "</option>";
									else
										options += "<option value='" + val.codigo + "'>" + val.codigo + " | " + val.nombre + "</option>";
								})

								$('#codProductorUpdate').append(options);
							},
							error: function (jqXHR, textStatus,
								errorThrown) {
							}
						});
						
						
						$('.codEtapaUpdate').empty();
						$('.codEtapaUpdate').append('<option value="">Seleccionar</option>');
					
						const getPP = {
							SP: "get_ParcelaByProductor",
							FILTERS: {
								p_productor: codProductor
							}
						}
						console.log(codEtapa)
						$.ajax({
							url: PROYECT + "json/CallSp",
							type: "PUT",
							dataType: 'json',
							data: JSON.stringify(getPP),
							async: false,
							beforeSend: function (xhr) {
								xhr.setRequestHeader("Accept", "application/json");
								xhr.setRequestHeader("Content-Type", "application/json");
							},
							success: function (res) {
								console.log(res)
								var options = "";
					
								$(res.data).each(function (key, val) {
									let selected = val.codigo == codEtapa? "selected": ""
									options += "<option value='" + val.codigo + "' "+selected+">" + val.nombre + "</option>";
								})
					
								$('.codEtapaUpdate').append(options);
							}, error: function (e) {
								console.log(e)
							}, complete: function () {
								//return data;
							}
						})

						$('.codCampoUpdate').empty();
						$('.codCampoUpdate').append('<option value="">Seleccionar</option>');
					
						const getTPP2 = {
							SP: "get_CampoByProdEtapa",
							FILTERS: {
								p_productor: codProductor,
								p_etapa: codEtapa
							}
						}
						console.log(getTPP2)
						$.ajax({
							url: PROYECT + "json/CallSp",
							type: "PUT",
							dataType: 'json',
							data: JSON.stringify(getTPP2),
							async: false,
							beforeSend: function (xhr) {
								xhr.setRequestHeader("Accept", "application/json");
								xhr.setRequestHeader("Content-Type", "application/json");
							},
							success: function (res) {
								console.log(res)
								var options = "";
					
								$(res.data).each(function (key, val) {
									let selected = val.codCampo == codCampo? "selected": ""
									options += "<option value='" + val.codCampo + "' "+selected+">" + val.nombre + "</option>";
								})
								$('.codCampoUpdate').append(options);
							}, error: function (e) {
								console.log(e)
							}, complete: function () {
								return data;
							}
						})
							$('.idVariedadUpdate').empty();
						$('.idVariedadUpdate').append('<option value="">Seleccionar</option>');
					
						const getTPP3 = {
							SP: "get_VariedadByProdEtapaCampo",
							FILTERS: {
								p_productor: codProductor,
								p_etapa: codEtapa,
								p_campo: codCampo
							}
						}
						console.log(getTPP3)
						$.ajax({
							url: PROYECT + "json/CallSp",
							type: "PUT",
							dataType: 'json',
							data: JSON.stringify(getTPP3),
							async: false,
							beforeSend: function (xhr) {
								xhr.setRequestHeader("Accept", "application/json");
								xhr.setRequestHeader("Content-Type", "application/json");
							},
							success: function (res) {
								console.log(res)
								var options = "";
					
								$(res.data).each(function (key, val) {
									let selected = val.variedad == idVariedad? "selected": ""
									options += "<option value='" + val.variedad.replace("'","@") + "' "+selected+">" + val.variedad + "</option>";
								})
								$('.idVariedadUpdate').append(options);
							}, error: function (e) {
								console.log(e)
							}, complete: function () {
								return data;
							}
						})
						$('.mercado2').empty();
						$('.mercado2').append('<option value="">Seleccionar</option>');
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
									let selected = val.mercado == mercado? "selected": ""
									options += "<option value='"+val.mercado+"' "+selected+">"+val.mercado+"</option>";
								})
								$('.mercado2').append(options);
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
						
						codProductorUpdate : {
							required : true

						},
						codEtapaUpdate : {
								required : true
	
						},
						codCampoUpdate : {
								required : true
	
						},
						idVariedadUpdate : {
								required : true
	
						},
						codMercadoUpdate : {
								required : true
	
						},
						updateHabilitado : {
								required : true
						}

					},

					messages : {

						codProductorUpdate : {
							required : "Este campo es obligatorio"
						},codEtapaUpdate : {
							required : "Este campo es obligatorio"
						},codCampoUpdate : {
							required : "Este campo es obligatorio"
						},idVariedad : {
							required : "Este campo es obligatorio"
						},codMercadoUpdate : {
							required : "Este campo es obligatorio"
						},updateHabilitado : {
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


						row.productor = $('#codProductorUpdate').val();;
						row.etapa = $('#codEtapaUpdate').val();;
						row.campo = $('#codCampoUpdate').val();;
						row.idVariedad = $('#idVariedadUpdate').val();;
						row.mercado = $('#codMercadoUpdate').val();;
						row.habilitado = $("#updateHabilitado").val();

						row.id = $('#updateId').val();;
						
						$.ajax({
									url : "/AgroVisionRestricciones/"+"json/habilitacionComercial/put",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-modifica').modal("toggle");
										swal({
											  title: 'Habilitacion Comercial Modificada exitosamente!',
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
	
	var insert = function(){
		var row = {};

		
		
		var form1 = $('#form-InsertProductor');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						codProductor : {
							required : true,
							
						},
						codEtapa : {
							required : true,
							
						},
						codCampo : {
							required : true,
							
						},
						codTurno : {
							required : true,
							
						},
						idVariedad : {
							required : true,
							
						},
						codMercado : {
							required : true,
							
						}

					},

					messages : {
						codigoProductor : {
							required: "Este campo es obligatorio"
						},
						codEtapa : {
							required: "Este campo es obligatorio"
						},
						codCampo : {
							required: "Este campo es obligatorio"
						},
						codTurno : {
							required: "Este campo es obligatorio"
						},
						idVariedad : {
							required: "Este campo es obligatorio"
						},
						codMercado : {
							required: "Este campo es obligatorio"
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

						row.productor = $('#codProductor').val();
						row.etapa = $('#codEtapa').val();
						row.campo = $('#codCampo').val();
						row.idVariedad = $('#idVariedad').val();
						row.mercado = $('#codMercado').val();
						row.habilitado = $('#habilitado').val();
						
						

						$.ajax({
									url : "/AgroVisionRestricciones/"+"json/habilitacionComercial/insert",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-newProductor').modal('toggle');
									swal({
										  title: 'Habilitacion Comercial ingresada exitosamente!',
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
			
			limpiar();
			handleDemo1();
			insert();
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
				options += "<option value='"+val.mercado+"'>"+val.mercado+"</option>";
			})
			$('.mercado').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
	
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
	
	$('#codProductor').on('change', function() {
		$('.codEtapa').empty();
		$('.codEtapa').append('<option value="">Seleccionar</option>');
		
		const get = {
			SP: "get_EtapaByProductor",
			FILTERS: {
				p_productor: this.value
			}
		}
		console.log(get)
		$.ajax({
			url: PROYECT+"json/CallSp",
			type:	"PUT",
			dataType: 'json',
			data: JSON.stringify(get),
			async: false,
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept","application/json");
				xhr.setRequestHeader("Content-Type","application/json");
			},
			success: function(res){
				console.log(res)
				var options = "";
				
				$(res.data).each(function(key, val){
					options += "<option value='"+val.codigo+"'>"+val.nombre+"</option>";
				})
			
				$('.codEtapa').append(options);
			},error: function(e){
				console.log(e)
			},complete: function(){
			}
		})
		return;
		
		
		
	   
	  });
	$('#codEtapa').on('change', function() {
		
		$('.codCampo').empty();
		$('.codCampo').append('<option value="">Seleccionar</option>');
		var productor=$('#codProductor').children("option:selected").val();
		
		const get = {
			SP: "get_CampoByProdEtapa",
			FILTERS: {
				p_productor: productor,
				p_etapa: this.value
			}
		}
		console.log(get)
		$.ajax({
			url: PROYECT+"json/CallSp",
			type:	"PUT",
			dataType: 'json',
			data: JSON.stringify(get),
			async: false,
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept","application/json");
				xhr.setRequestHeader("Content-Type","application/json");
			},
			success: function(res){
				console.log(res)
				var options = "";
				
				$(res.data).each(function(key, val){
					options += "<option value='"+val.codCampo+"'>"+val.nombre+"</option>";
				})
			
				$('.codCampo').append(options);
			},error: function(e){
				console.log(e)
			},complete: function(){
			}
		})
		return;
	});
	
	
$('#codCampo').on('change', function () {
	$('.idVariedad').empty();
	$('.idVariedad').append('<option value="">Seleccionar</option>');
	var productor = $('#codProductor').children("option:selected").val();
	var codEtapa = $('#codEtapa').children("option:selected").val();

	
	

	const get = {
		SP: "get_VariedadByProdEtapaCampo",
		FILTERS: {
			p_productor:productor,
			p_etapa: codEtapa,
			p_campo: this.value
		}
	}
	console.log(get)
	$.ajax({
		url: PROYECT + "json/CallSp",
		type: "PUT",
		dataType: 'json',
		data: JSON.stringify(get),
		async: false,
		beforeSend: function (xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success: function (res) {
			console.log(res)
			var options = "";

			$(res.data).each(function (key, val) {
				options += "<option value='" + val.variedad.replace("'","@") + "'>" + val.variedad + "</option>";
			})

			$('.idVariedad').append(options);
		}, error: function (e) {
			console.log(e)
		}, complete: function () {
			//return data;
		}
	})
	return;
	

});
	/////////////////////////////////////////////////////////
	
	$('#codProductorUpdate').on('change', function() {
		$('.codEtapaUpdate').empty();
		$('.codEtapaUpdate').append('<option value="">Seleccionar</option>');
		
		const get = {
			SP: "get_EtapaByProductor",
			FILTERS: {
				p_productor: this.value
			}
		}
		console.log(get)
		$.ajax({
			url: PROYECT+"json/CallSp",
			type:	"PUT",
			dataType: 'json',
			data: JSON.stringify(get),
			async: false,
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept","application/json");
				xhr.setRequestHeader("Content-Type","application/json");
			},
			success: function(res){
				console.log(res)
				var options = "";
				
				$(res.data).each(function(key, val){
					options += "<option value='"+val.codigo+"'>"+val.nombre+"</option>";
				})
			
				$('.codEtapaUpdate').append(options);
			},error: function(e){
				console.log(e)
			},complete: function(){
			}
		})
		return;
		
		
		
	   
	  });
	$('#codEtapaUpdate').on('change', function() {
		
		$('.codCampoUpdate').empty();
		$('.codCampoUpdate').append('<option value="">Seleccionar</option>');
		var productor=$('#codProductorUpdate').children("option:selected").val();
		
		const get = {
			SP: "get_CampoByProdEtapa",
			FILTERS: {
				p_productor: productor,
				p_etapa: this.value
			}
		}
		console.log(get)
		$.ajax({
			url: PROYECT+"json/CallSp",
			type:	"PUT",
			dataType: 'json',
			data: JSON.stringify(get),
			async: false,
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept","application/json");
				xhr.setRequestHeader("Content-Type","application/json");
			},
			success: function(res){
				console.log(res)
				var options = "";
				
				$(res.data).each(function(key, val){
					options += "<option value='"+val.codCampo+"'>"+val.nombre+"</option>";
				})
			
				$('.codCampoUpdate').append(options);
			},error: function(e){
				console.log(e)
			},complete: function(){
			}
		})
		return;
	});
	
	
$('#codCampoUpdate').on('change', function () {
	$('.idVariedadUpdate').empty();
	$('.idVariedadUpdate').append('<option value="">Seleccionar</option>');
	var productor = $('#codProductorUpdate').children("option:selected").val();
	var codEtapa = $('#codEtapaUpdate').children("option:selected").val();

	
	

	const get = {
		SP: "get_VariedadByProdEtapaCampo",
		FILTERS: {
			p_productor:productor,
			p_etapa: codEtapa,
			p_campo: this.value
		}
	}
	console.log(get)
	$.ajax({
		url: PROYECT + "json/CallSp",
		type: "PUT",
		dataType: 'json',
		data: JSON.stringify(get),
		async: false,
		beforeSend: function (xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success: function (res) {
			console.log(res)
			var options = "";

			$(res.data).each(function (key, val) {
				options += "<option value='" + val.variedad.replace("'","@") + "'>" + val.variedad + "</option>";
			})

			$('.idVariedadUpdate').append(options);
		}, error: function (e) {
			console.log(e)
		}, complete: function () {
			//return data;
		}
	})
	return;
	

});		
	
});