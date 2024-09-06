$.fn.serializeObject = function()
{
	
   var o = {};
   var a = this.serializeArray();
 
   $.each(a, function() {
       if (o[this.name]) {
           if (!o[this.name].push) {
               o[this.name] = [o[this.name]];
           }
           o[this.name].push(this.value || '');
       } else {
           o[this.name] = this.value || '';
       }
   });
   return o;
};
var numero = 0;

function agregarFila(){
	var lab = $("#laboratorio").val();
	var ident = $("#Identificador").val();
	var codProduct = $("#updateCodProducto").val();
	var fechaDate = $("#fecha").val();
	var codNew = $("#codProductoNew").val();
	var limiteInpu = $("#limiteInput").val();
	
	 var fecha1 = fechaDate.split('-');
	 var fecha2 = fecha1[0]+"-"+fecha1[1]+"-"+fecha1[2];
	 var d = new Date(); 
	 var segundos = d.getSeconds();
	 
	 if((""+segundos+"").length == 1 ){
		 var nuevoNumero = "0"+segundos;
		 datetext = d.getHours()+":"+d.getMinutes()+":"+nuevoNumero;
	 }else{
		 datetext = d.getHours()+":"+d.getMinutes()+":"+segundos;
	 }
	 
	 
	 var resultadofecha = fecha2 +" "+datetext;
	
	
	if (lab === ''){
		
		alert("Debe Ingresar un Laboratorio");
		 $( "#laboratorio" ).focus();
		return;
	}
	else if(ident === ''){alert("Debe Ingresar un Identificador"); $( "#Identificador" ).focus();return;}
	else if(codProduct === ''){alert("Debe Ingresar un Productor"); $( "#updateCodProducto" ).focus();return;}
	else if(fechaDate === ''){alert("Debe Ingresar una Fecha"); $( "#firstName" ).focus();return;}
	else if(codNew === ''){alert("Debe Ingresar un Componente "); $( "#codProductoNew" ).focus();return;}
	else if(limiteInpu === ''){alert("Debe Ingresar un Resultado"); $( "#limiteInput" ).focus();return;}
	
	$("#tblPeticion")
	.append(
			"" + "<tr id=td"+numero+ ">"
			   + "<td class='tdcodNew'  style='text-align: center;'>"+codNew+"</td>"
			   + "<td class='tdlimite'  style='text-align: center;'>"+limiteInpu+"</td>"
			   + "<td class='' style='text-align: center;'><a  href='javascript:;' id=td"+ numero+"  onclick='javascript: eliminarFila(this.id);' class='btn btn-icon-only red'><i class='fa fa-times'></i></a></td>"
	           +"</tr>");
	numero = numero + 1;
	
    $("#codProductoNew").val("");
	$("#limiteInput").val("");
}

function eliminarFila(id) {

	$("#" + id + " td").each(function() {
		$("#" + id + " tr").remove();
		$("#" + id + "").remove();
	});

}// end Function

function Enviar(){
	
	var row = {};
	var nFilas = $("#tblPeticion tr").length;

	if (nFilas == 0) {
		alert("Debe Agregar a la Lista Antes de Enviar");
		return;
	} else {

		
		Array_codProductoNew = [];
		Array_limiteInput = [];
		
			
	
		$("td.tdcodNew").each(function() {
			Array_codProductoNew.push($(this).text());
		});
		$("td.tdlimite").each(function() {
			Array_limiteInput.push($(this).text());
		});
		
		
		row.laboratorio=$("#laboratorio").val();
		row.identificador=$("#Identificador").val();
		row.codProductor=$("#codProductor").val();
		row.fecha=$("#fecha").val();
		row.idEspecie=$("#idEspecie").val();
		
		row.codParcela=$("#codParcela").val();
		row.codTurno=$("#codTurno").val();
		row.idVariedad=$("#idVariedad").val();
		//var obj = $('#form-new').serializeObject();
		row.detalle=[];
		var data = {};
		for (var i = 0; i < nFilas; i++) {
			
				data = {};
				data.codProducto =Array_codProductoNew[i];
				data.limite= Array_limiteInput[i];
				row.detalle.push(data);
			}
			
			

		}
		console.log(row);
		
		
		
		

		var search = JSON.stringify(row);
		console.log(search);
	
		$("#laboratorio").val("");
		$("#Identificador").val("");
		$("#codProductor").val("");
		$("#firstName").val("");
		$("#codProductoNew").val("");
		$("#limiteInput").val("");
		$("#tblPeticion").empty("");
		$("#codParcela").val("");
		$("#idVariedad").val("");
		$("#codTurno").val("");
		 
		$.ajax({
					url : "/AgroVisionRestricciones/"+"json/cargaManual2/add",
					type : "PUT",
					data : search,
					beforeSend : function(xhr) {
						xhr.setRequestHeader("Accept",
								"application/json");
						xhr.setRequestHeader("Content-Type",
								"application/json");
					},

					success : function(data, textStatus, jqXHR) {
						
						$('#modal-insert').modal('toggle');
					swal({
						  title: 'Se ingreso correctamente la carga manual!',
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

function actualizar(){
	 var lab = $("#laboratorioUpdate").val();
	 var identif =  $("#IdentificadorUpdate").val();
	 var cod_productor =  $("#codProductorUpdate").val(); 
	 var fecha_update = $("#fechaUpdate").val();
	 var limite = $("#limiteInputUpdate").val();
	 var cod_producto = $("#codProductoUpdate").val();
	 var id_update = $("#idUpdate").val();
	 
	 var cod_Parcela = $("#codParcelaUpdate").val();
	 var id_Variedad = $("#idVariedadUpdate").val();
	 var codTurno = $("#codTurnoUpdate").val();
	 var ID=$("#idUpdate").val();
	 
	 
	 
	    if (lab === ''){alert("Debe Ingresar un Laboratorio"); $( "#laboratorioUpdate" ).focus();return;}
		else if(identif === ''){alert("Debe Ingresar un Identificador"); $( "#IdentificadorUpdate" ).focus();return;}
		else if(cod_productor === ''){alert("Debe Ingresar un Productor"); $( "#codProductorUpdate" ).focus();return;}
		else if(fecha_update === ''){alert("Debe Ingresar una Fecha"); $( "#fechaUpdate" ).focus();return;}
		else if(cod_producto === ''){alert("Debe Ingresar un Componente "); $( "#codProductoUpdate" ).focus();return;}
		else if(limite === ''){alert("Debe Ingresar un Resultado"); $( "#limiteInputUpdate" ).focus();return;}
	 
	
	 var valNew = fecha_update.split('-');
	 var valNew2 = valNew[0]+"-"+valNew[1]+"-"+valNew[2];
	 var d = new Date(); 
	 var segundos = d.getSeconds();
	 
	 if((""+segundos+"").length == 1 ){
		 var nuevoNumero = "0"+segundos;
		 datetext = d.getHours()+":"+d.getMinutes()+":"+nuevoNumero;
	 }else{
		 datetext = d.getHours()+":"+d.getMinutes()+":"+segundos;
	 }
	 
	 
	 var resultadofecha = valNew2 +" "+datetext;
	 
	 
	var enviarDatos = {

		    laboratorio : lab,
			identificador : identif,
			codProductor : cod_productor,
			fecha : resultadofecha,
			codProducto : cod_producto,
			limite : limite,
			idCargaManual : id_update,
			codParcela:cod_Parcela,
			codTurno,codTurno,
			idVariedad:id_Variedad,
			idCargaManual:ID

		}
	
	$.ajax({
		url : "/AgroVisionRestricciones/"+"json/cargaManual2/updateAnalisis",
		type : "PUT",
		data : JSON.stringify(enviarDatos),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept","application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},
		success : function(data, textStatus, jqXHR) {
			  
			$('#modal-update').modal('toggle');
			swal({
				  title: 'Se actualizo correctamente la carga manual!',
				  animation: true,
				  customClass: 'animated tada'
				})
				var table = $('#datatable_ajax')
						.DataTable({
							bRetrieve : true
						});
			
			
				table.ajax.reload();
		  
		},
		error : function(ex) {
			
		}

	})
}
var controllerPage = function() {

	 var ID;
	var populateForm = function()
	{
		
		
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
					options += "<option value='"+val.codigo+"'>"+val.codigo+" | "+val.nombre+"</option>";
				})
				
				$('.codProductor').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
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
			
				$('.idEspecie').append(options);
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
			
				$('.idVariedad').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
		
	
	}
	var getView = function() {

		var grid = new Datatable();

		grid.init({
					src : $("#datatable_ajax"),
					onSuccess : function(grid, response) {},
					onError : function(grid) {
						// execute some code on network or other general error
					},
					onDataLoad : function(grid) {
						// execute some code on ajax data load
					},
					loadingMessage : 'Loading...',
					dataTable : { 
						"responsive": false,
						"bStateSave" : true, 
						"lengthMenu" : [ [ 10, 20, 50, 100, 150, -1 ],[ 10, 20, 50, 100, 150, "All" ] ],
						"pageLength" : 20, // default record count per page
						"ajax" : {"url" : "/AgroVisionRestricciones/"+"json/cargaManual2/view"},
						"columnDefs" : [
						        { "visible": false, "targets": [0] },     
								{
									"targets" : [ 5 ],
									"render" : function(data, type, full) {
										
										
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm'>";


										html += "<a class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[0]
												+ "' href='#modal-update'><i class='fa fa-pencil-square'></i></a> ";

										html += "</div>";

										return html;

									}
								}],
						"order" : [ [ 1, "des" ] ]

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


	
	var getId = function() {
		$("#modal-update").on(
				'show.bs.modal',
				function(e) {

					var button = $(e.relatedTarget);// Button which is clicked
					var id = button.data('id');// Get id of the button
					
					//$('#codProductorUpdate').prop('selectedIndex',-1);
					
					
					
					ID=id;
					$("#idUpdate").val(ID);
					$.ajax({
						type : 'GET',
						url : "/AgroVisionRestricciones/"+"json/cargaManual2/" + id,
						data : "",
						success : function(data) {
							
							
							var codProductor = data.codProductor;
							
							
							var codProducto = data.codProducto;
							var codParcela = data.codParcela;
							var codTurno = data.codTurno;
							var idVariedad = data.idVariedad;
							
							$("#laboratorioUpdate").val(data.laboratorio);
							$("#IdentificadorUpdate").val(data.identificador);
							
							$("#codProductorUpdate option[value='"+codProductor+"']").attr("selected","selected");
							$('#codProductorUpdate').empty();
							$('#codProductorUpdate').append('<option value="">Seleccionar</option>');
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
										if(val.codigo==codProductor)
											options += "<option value='"+val.codigo+"'  selected='selected' >"+val.codigo+" | "+val.nombre+"</option>";
										else
											options += "<option value='"+val.codigo+"'>"+val.codigo+" | "+val.nombre+"</option>";
									})
									
									$('#codProductorUpdate').append(options);
								},
								error : function(jqXHR, textStatus,
										errorThrown) {
								}
							});
							
							
							
							$('.codParcelaUpdate').empty();
							$('.codParcelaUpdate').append('<option value="">Seleccionar</option>');
							$.ajax({
								url : "/AgroVisionRestricciones/"+"json/parcela/getAllByProductor/"+codProductor,
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
										if (val.codigo==codParcela)
											options += "<option value='"+val.codigo+"'  selected='selected'>"+val.nombre+"</option>";
										else
											options += "<option value='"+val.codigo+"'>"+val.nombre+"</option>";
									})
								
									$('.codParcelaUpdate').append(options);
								},
								error : function(jqXHR, textStatus,
										errorThrown) {
								}
							});
							
							
							
							
								$('.codTurnoUpdate').empty();
								$('.codTurnoUpdate').append('<option value="">Seleccionar</option>');
								
								$.ajax({
									url : "/AgroVisionRestricciones/"+"json/turno/getAllByParcela/"+codProductor+"/"+codParcela,
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
											if (val.codTurno==codTurno)
											options += "<option value='"+val.codTurno+"'   selected='selected'>"+val.nombre+"</option>";
											else
											options += "<option value='"+val.codTurno+"'>"+val.nombre+"</option>";
										})
									
										$('.codTurnoUpdate').append(options);
									},
									error : function(jqXHR, textStatus,
											errorThrown) {
									}
								});
							   
							
							
								$('.idVariedadUpdate').empty();
								$('.idVariedadUpdate').append('<option value="">Seleccionar</option>');
								
								$.ajax({
									url : "/AgroVisionRestricciones/"+"json/variedad/getAllByTurno/"+codParcela+"/"+codTurno,
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
											if (val.idVariedad==idVariedad)
											options += "<option value='"+val.idVariedad+"' selected='selected'>"+val.nombre+"</option>";
											else
											options += "<option value='"+val.idVariedad+"'>"+val.nombre+"</option>";
										})
									
										$('.idVariedadUpdate').append(options);
									},
									error : function(jqXHR, textStatus,
											errorThrown) {
									}
								});
							   
							
							
						 
						
						    
							
							 var res = data.fecha.slice(0, 10);
							$("#fechaUpdate").val(res);
							$("#idEspecieUpdate").val(data.idEspecie);
							
							
							/*
							
							$("#codProductoUpdate option[value='"+codProducto+"']").attr("selected","selected");
							$("#idUpdate").val(data.idCargaManual);
							$("#limiteInputUpdate").val(data.limite);
							*/
							 $("#tblPeticionU").empty();
							numero=0;
							for(var k in data.detalle) {
								
								   $("#tblPeticionU")
									.append(
											"" + "<tr id=td"+numero+ ">"
											   + "<td class='tdcodNew'  style='text-align: center;'>"+data.detalle[k].codProducto+"</td>"
											   + "<td class='tdlimite'  style='text-align: center;'>"+data.detalle[k].limite+"</td>"
											   + "<td   style='text-align: center;'> </td>"
											   //+ "<td class='' style='text-align: center;'><a  href='javascript:;' id=td"+ numero+"  onclick='javascript: eliminarFila(this.id);' class='btn btn-icon-only red'><i class='fa fa-times'></i></a></td>"
									           +"</tr>");
								  
								   ++numero;
								}
							
							
//							var $repeater = $('#form-update').repeater({
//						           show: function () {
//						        	   $(this).slideDown();
//						           },
//						           hide: function (deleteElement) {
//						               if(confirm('Esta seguro de que desea eliminar el registro?')) {
//						                   $(this).slideUp(deleteElement);
//						               }
//						           },
//						           ready: function (setIndexes) {
//						        	  
//						           }
//						       });
//							
//							$repeater.setList(data.detalle);

						}
					});
				});
	}

//	var update = function() {
//
//		var row = {};
//		var form1 = $('#form-update');
//
//		form1.validate({
//					errorElement : 'span', 
//					errorClass : 'help-block help-block-error',
//					focusInvalid : true, 
//					rules : {
//						updateTipoProducto : {
//							required : true,
//							rangelength : [ 5, 50 ],
//							alfanumerico : true
//						}
//
//					},
//
//					messages : {
//
//						updateTipoProducto : {
//							required : "Este campo es obligatorio",
//							rangelength : "Debe ser mayor a 5 y menor a 50",
//							alfanumerico : "Ingrese sólo valores alfanumericos"
//						}
//
//					},
//
//					errorPlacement : function(error, element) { // render error
//																// placement for
//																// each input
//																// type
//						if (element.parent(".input-group").size() > 0) {
//							error.insertAfter(element.parent(".input-group"));
//						} else if (element.attr("data-error-container")) {
//							error
//									.appendTo(element
//											.attr("data-error-container"));
//						} else if (element.parents('.radio-list').size() > 0) {
//							error.appendTo(element.parents('.radio-list').attr(
//									"data-error-container"));
//						} else if (element.parents('.radio-inline').size() > 0) {
//							error.appendTo(element.parents('.radio-inline')
//									.attr("data-error-container"));
//						} else if (element.parents('.checkbox-list').size() > 0) {
//							error.appendTo(element.parents('.checkbox-list')
//									.attr("data-error-container"));
//						} else if (element.parents('.checkbox-inline').size() > 0) {
//							error.appendTo(element.parents('.checkbox-inline')
//									.attr("data-error-container"));
//						} else {
//							error.insertAfter(element);
//						}
//					},
//
//					invalidHandler : function(event, validator) { 
//						App
//								.alert({
//									container : '#modal-modifica-cuenta .modal-body', 
//									place : 'prepend', // append or prepent in
//														// container
//									type : 'danger', // alert's type
//									message : 'Por favor Corrija los errores antes de continuar', // alert's
//																									// message
//									close : false, // make alert closable
//									reset : true, // close all previouse
//													// alerts first
//									focus : false, // auto scroll to the alert
//													// after shown
//									closeInSeconds : 5
//								});
//					},
//
//					highlight : function(element) { // hightlight error inputs
//						$(element).closest('.form-group').addClass('has-error');
//					},
//
//					unhighlight : function(element) { // revert the change
//														// done by hightlight
//						$(element).closest('.form-group').removeClass(
//								'has-error'); // set error class to the
//												// control group
//					},
//
//					success : function(label) {
//						label.closest('.form-group').removeClass('has-error'); 
//					},
//
//					submitHandler : function(form) {
//
//
//						// parametrosCuenta.Cuenta = cuenta;
//
//						row.tipoProducto = $('#updateTipoProducto').val();
//						row.idTipoProducto = ID;
//						
//						$.ajax({
//									url : "/AgroVisionRestricciones/"+"json/tipoProducto/put",
//									type : "PUT",
//									data : JSON.stringify(row),
//									beforeSend : function(xhr) {
//										xhr.setRequestHeader("Accept",
//												"application/json");
//										xhr.setRequestHeader("Content-Type",
//												"application/json");
//									},
//
//									success : function(data, textStatus, jqXHR) {
//										$('#modal-modifica-tipoProducto').modal("toggle");
//										swal({
//											  title: 'Tipo Producto Modificada exitosamente!',
//											  animation: true,
//											  customClass: 'animated tada'
//											})
//										var table = $('#datatable_ajax')
//												.DataTable({
//													bRetrieve : true
//												});
//										table.ajax.reload();
//										
//									},
//									error : function(jqXHR, textStatus,
//											errorThrown) {
//									}
//								});
//
//					}
//
//				});
//
//	}
	
	//var insert = function(){
		
		
		
//		var row = {};
//
//		
//		
//		var form1 = $('#form-new');
//
//		form1.validate({
//					errorElement : 'span', 
//					errorClass : 'help-block help-block-error',
//					focusInvalid : true, 
//					rules : {
//						    laboratorio : {
//							required : true,
//							rangelength : [ 2, 50 ],
//							alfanumerico : true
//						},
//						'detalle[][limite]' : {
//							required : true,
//							rangelength : [ 2, 50 ],
//							alfanumerico : true
//						}
//
//					},
//
//					messages : {
//
//						regTipoProducto : {
//							required: "Este campo es obligatorio",
//							rangelength : "No debe ser menor a 2 y mayor a 50 caracteres",
//							alfanumerico : "ingrese valores alfanumericos"
//						}
//
//					},
//
//					errorPlacement : function(error, element) { 
//						if (element.parent(".input-group").size() > 0) {
//							error.insertAfter(element.parent(".input-group"));
//						} else if (element.attr("data-error-container")) {
//							error
//									.appendTo(element
//											.attr("data-error-container"));
//						} else if (element.parents('.radio-list').size() > 0) {
//							error.appendTo(element.parents('.radio-list').attr(
//									"data-error-container"));
//						} else if (element.parents('.radio-inline').size() > 0) {
//							error.appendTo(element.parents('.radio-inline')
//									.attr("data-error-container"));
//						} else if (element.parents('.checkbox-list').size() > 0) {
//							error.appendTo(element.parents('.checkbox-list')
//									.attr("data-error-container"));
//						} else if (element.parents('.checkbox-inline').size() > 0) {
//							error.appendTo(element.parents('.checkbox-inline')
//									.attr("data-error-container"));
//						} else {
//							error.insertAfter(element); // for other inputs,
//														// just perform default
//														// behavior
//						}
//					},
//
//					invalidHandler : function(event, validator) { // display
//																	// error
//					},
//
//					highlight : function(element) { // hightlight error inputs
//						$(element).closest('.form-group').addClass('has-error'); 
//					},
//
//					unhighlight : function(element) { 
//						$(element).closest('.form-group').removeClass(
//								'has-error');
//					},
//
//					success : function(label) {
//						label.closest('.form-group').removeClass('has-error'); 
//					},
//
//					submitHandler : function(form) {
//
//						row.tipoProducto = $('#regTipoProducto').val();
//						row.idUser = "asasas";
//						
//						
//						
//						
//						
//						var obj = $('#form-new').serializeObject();
//						var formdata= $('#form-new').serialize();
//						
//						var formdata = $("#form-new").serializeArray();
//						var data = {};
//						i=0
//						row = {};
//						 obj.detalle=[]
//						$(formdata ).each(function(index, o){
//						    //data[obj.name] = obj.value;
//							 console.log(o.name+"->"+o.name.indexOf("detalle"));
//						    if (o.name.indexOf("detalle")>=0)
//						    {
//						    	 console.log(o.name.indexOf("codProducto"));
//						    	 
//						    	 if (o.name.indexOf("codProducto")>=0)
//						    		 row.codProducto=o.value;
//						    	 if (o.name.indexOf("limite")>=0)
//						    		 row.limite=o.value;
//						    	 
//						    	 i++
//						    	 if (i>=2)
//						    	 {
//						    		 i=0;
//						    		 obj.detalle.push(row)
//						    		 row = {};
//						    		 
//						    	 }
//						    }
//						})
//						
//					    
//
//						
//						
//						
//						
//						
//						
//						
//						var search = JSON.stringify(obj);
//						 
//						 console.log(search);
//						 
//						$.ajax({
//									url : "/AgroVisionRestricciones/"+"json/cargaManual2/add",
//									type : "PUT",
//									data : search,
//									beforeSend : function(xhr) {
//										xhr.setRequestHeader("Accept",
//												"application/json");
//										xhr.setRequestHeader("Content-Type",
//												"application/json");
//									},
//
//									success : function(data, textStatus, jqXHR) {
//										$('#modal-insert').modal('toggle');
//									swal({
//										  title: 'Se ingreso correctamente la carga manual!',
//										  animation: true,
//										  customClass: 'animated tada'
//										})
//										var table = $('#datatable_ajax')
//												.DataTable({
//													bRetrieve : true
//												});
//										table.ajax.reload();
//									},
//									error : function(jqXHR, textStatus,
//											errorThrown) {
//										console.log("error");
//
//									}
//								});
//					}
//
//				});
		
//	}

	return {

		// main function to initiate the module
		init : function() {
			populateForm();//recarga los select, check box, radio
			getView(); //metodo encargado trabajo con la grilla
			
//			insert();//metodo de validacion y envio a funcion de insercion
			
			getId();//obtiene los datos atraves de ajax para su edicion
//			update();//metodo de validaciob y envio a funcion de edicion
			
			
			//Agregar metodos de validacion a libreria
			jQuery.validator.addMethod("alfanumerico",
					function(value, element) {
						return this.optional(element)
								|| /^[a-zA-Z0-9._-]+$/.test(value);
					}, "solo números ddddd");
			
			$.validator.addMethod("repetidos", function(value, element) {
				return !this.optional(element) && !this.optional($(element).parent().prev().children("select")[0]);
			}, "Please select both the item and its amount.");
			
			//fin de metodos de validacion

		}

	};

}();

jQuery(document).ready(function() {
	controllerPage.init();
});



$('#codProductor').on('change', function() {
	$('.codParcela').empty();
	$('.codParcela').append('<option value="">Seleccionar</option>');
	
	const get = {
		SP: "get_ParcelaByProductor",
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
		
			$('.codParcela').append(options);
		},error: function(e){
			console.log(e)
		},complete: function(){
			return data;
		}
	})
	return;
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


$('#codParcela').on('change', function() {
	$('.idVariedad').empty();
	$('.idVariedad').append('<option value="">Seleccionar</option>');
	var codParcela=$('#codParcela').children("option:selected").val();
	
	const get = {
		SP: "get_TurnosByProdParcela",
		FILTERS: {
			p_productor: productor,
			p_parcela: this.value
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
				options += "<option value='"+val.codTurno+"'>"+val.nombre+"</option>";
			})
		
			$('.codTurno').append(options);
		},error: function(e){
			console.log(e)
		},complete: function(){
			return data;
		}
	})
	return;
	$.ajax({
		url : "/AgroVisionRestricciones/"+"json/variedad/getAllByTurno/"+this.value,
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
		
			$('.idVariedad').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });


$('#codProductorUpdate').on('change', function() {
	$('.codParcelaUpdate').empty();
	$('.codParcelaUpdate').append('<option value="">Seleccionar</option>');
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
		
			$('.codParcelaUpdate').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });


$('#codParcelaUpdate').on('change', function() {
	$('.idVariedadUpdate').empty();
	$('.idVariedadUpdate').append('<option value="">Seleccionar</option>');
	var codParcela=$('#codParcelaUpdate').children("option:selected").val();
	$.ajax({
		url : "/AgroVisionRestricciones/"+"json/variedad/getAllByTurno/"+codParcela+"/"+this.value,
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
		
			$('.idVariedadUpdate').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });
















