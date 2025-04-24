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
							"url" : "/AgroVisionRestricciones/"+"json/parcela/view", // ajax
																				// source
						},
						"columnDefs" : [
								{
									"targets" : [ 5 ],
									"render" : function(data, type, full) {
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm'>";


										html += "<a  class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[1]
												+ "' href='#modal-modifica-productor'><i class='fa fa-pencil-square'></i></a> ";
										html += "<a onclick='' class='col-md-6 btn red btn-table pull-right button-grilla-elimina-cuenta drop'   data-id='"
												+ full[0]+'_'+ full[1]
												+ "' data-toggle='modal'><i class='fa fa-trash-o'></i></a>";
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

					 $('#regCodigo').val("");
					$('#codigoProductor').val("");
					$('#regNombre').val("");
				
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
						url : "/AgroVisionRestricciones/json/parcela/" + id,
						data : "",
						success : function(data) {
							$("#updateUser").val(data.codigo);
							$("#updateNombre").val(data.nombre);
							$("#updateFeCreacion").val(data.creado);
							$("#updateModificacion").val(data.modificado);
							$("#updateProductor").val(data.codigoProductor);
						
								
							
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
						
						updateNombre : {
							required : true,
							rangelength : [ 4, 50 ],
							alfanumerico : true
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

						row.codigo = $('#updateUser').val();;
						row.nombre = $("#updateNombre").val();
						row.idUser = $("#idUserPefil").val();
						row.codigoProductor = $('#updateProductor').val();;
						
						$.ajax({
									url : "/AgroVisionRestricciones/"+"json/parcela/put",
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
						regCodigo : {
							required : true,
							rangelength : [ 2, 6 ]
						},
						regNombre : {
							required : true,
							rangelength : [ 4, 50 ],
							alfanumerico : true
						},
						codigoProductor : {
							required : true
						}

					},

					messages : {
						codigoProductor : {
							required: "Este campo es obligatorio"
						},
						regCodigo : {
							required: "Este campo es obligatorio",
							rangelength : "No debe ser menor a 4 y mayor a 50 caracteres",
							alfanumerico : "ingrese valores alfanumericos"
						},
						regNombre : {
							required: "Este campo es obligatorio",
							rangelength : "No debe ser menor a 4 y mayor a 50 caracteres",
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

						row.codigo = $('#regCodigo').val();
						row.nombre = $('#regNombre').val();
						row.idUser = $("#idUserPefil").val();
						row.codigoProductor = $('#codigoProductor').val();
						loading.show();

						$.ajax({
									url : "/AgroVisionRestricciones/"+"json/parcela/insert",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										loading.hide();
										$('#modal-newProductor').modal('toggle');
									swal({
										  title: 'Productor ingresado exitosamente!',
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
				url : "/AgroVisionRestricciones/"+"json/parcela/drop",
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
let tabla;
const get = {
	SP: "get_jerarquias"
}
callSp(get).then(function(res){
	console.log(res)
	if(res.error != 0){
		alert(res.message);
		return;
	}
	let datos = [];
	$.each(res.data, function(k,v){
		let tbl = [v.Sociedad, v.Etapa, v.EtapaDenomina, v.Campo, v.CampoDenomina, v.Turno, v.TurnoDenomina, v.Especie, v.EspecieDenomina, v.Variedad, v.VariedadDenomina, v.Fundo, v.FundoDenomina, v.Productor, v.ProductorNombre,(v.estado? 'Activo': 'Inactivo') ,""]
		datos.push(tbl)
	})
	
	if(tabla){
		tabla.destroy();
        $('#tbl_RendimientoVlidadr').empty();
	}
	columnas = ["Sociedad", "Etapa", "EtapaDenomina", "Campo", "CampoDenomina", "Turno", "TurnoDenomina", "Especie", "EspecieDenomina", "Variedad", "VariedadDenomina", "Fundo", "FundoDenomina", "Productor", "ProductorNombre", "Estado"];
	var finalColumn = [];
	for(var i = 0; i < columnas.length; i++){
		finalColumn.push({title: columnas[i]})
	}
	finalColumn.push({
	  title: "Acción",
	  render: function(data, type, row, meta) {
	    return `<button class="btn btn-warning btn-sm delete-row" data-index="${meta.row}">
	              <i class="fa fa-minus-square" aria-hidden="true"></i>
	            </button>`;
	  }
	});
	tabla = $('#tbl_RendimientoVlidadr').DataTable({
		data: datos,
		columns: finalColumn,
		autoWidth: true,
		ordering: false
	});
	$('#tbl_RendimientoVlidadr tbody').on('click', '.delete-row', function () {
	  const rowIndex = $(this).data('index');
	  const row = tabla.row(rowIndex).data();
	  const {id, estado}=res.data[rowIndex];
	  console.log(estado)
	  const accionEstado = !estado? 'Activar': 'Inactivar'
	  const accionEstadoFinal = !estado? 'ACTIVO': 'INACTIVO'
	  swal({
            title: "¿Estás seguro?",
            text: "Se va a "+accionEstado+" el item seleccionado.",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Sí, Actualizar",
            cancelButtonText: "Cancelar"
        }).then(function (result) {
			console.log(res.data[rowIndex])
			
		    if (result.value) {
				$.ajax({
				    type: 'PUT',
				    url: '/AgroVisionRestricciones/json/Jerarquia/deleteJerarquia',
				    contentType: 'application/json',
				    data: JSON.stringify({ id, estado:+!estado }),
				    success: function (data) {
				      if (data.success) {
				        swal(
				          'Estado actualizado',
				          'El registro fue marcado como '+accionEstadoFinal+'.',
				          'success'
				        );
						row[15] = accionEstadoFinal;
			            tabla.row(rowIndex).data(row).draw(false);
				      } else {
				        swal(
				          'Error',
				          data.message || 'No se pudo actualizar la jerarquía.',
				          'error'
				        );
				      }

				      var table = $('#datatable_ajax').DataTable({
				        bRetrieve: true
				      });
				      table.ajax.reload();
				    },
				    error: function () {
				      swal(
				        'Error',
				        'Ocurrió un error inesperado al actualizar la jerarquía.',
				        'error'
				      );
				    }
			  	});
		    }
	  });
	});

	$("#tbl_RendimientoVlidadr_filter").hide();
	$("#tbl_RendimientoVlidadr_length").hide();
	
	$('#tbl_RendimientoVlidadr thead tr').clone(true).appendTo( '#tbl_RendimientoVlidadr thead' );
    $('#tbl_RendimientoVlidadr thead tr:eq(1) th').each( function (i) {
    	if($(this).text() != "" && $(this).text() != "Detalle"){
    		var title = $(this).text();
            $(this).html( '<input type="text" class="form-control input-sm" placeholder="'+title+'" />' );
     
            $( 'input', this ).on( 'keyup change', function () {
                if ( tabla.column(i).search() !== this.value ) {
                	tabla.column(i).search( this.value ).draw();
                }
            } );
    	}else{
    		$(this).html("");
    	}
    } );
})

function callSp(INPUT){
	if(INPUT.LOADING == undefined){
		INPUT.LOADING = true;
	}
	var data;
	return new Promise( function(resolve) {
		//INPUT.LOADING?loading.show():'';
		setTimeout(function(){
			$.ajax({
				url: PROYECT+"json/CallSp",
				type:	"PUT",
				dataType: 'json',
				data: JSON.stringify(INPUT),
				async: false,
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept","application/json");
					xhr.setRequestHeader("Content-Type","application/json");
				},
				success: function(data){
					data.error != 0?console.log(data.mensaje):'';
					data = data;
					if(INPUT.ALERTA){
						if(data.error != 0){
							alerta(data.mensaje);
						}
					}
					resolve(data);
				},error: function(e){
					console.log(e)
				},complete: function(){
					INPUT.LOADING?loading.hide():'';
					return data;
				}
			})
		}, 10);
	})
}