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
	let values = [];
	var acv = {
		TABLE: "asignarCertificadoVariedad",
	}
	var varArr = $("#idVariedad").val();
	
	var AC = {
		TABLE: "asignarCertificado",
		VALUES: {
			codProductor: $("#codProductor").val(),
			codParcela: $("#codParcela").val(),
			codTurno: $("#codTurno").val(),
			idVariedad: 0,
			fecha: $("#fecha").val(),
			idCertificado: $("#idCertificado").val()
		}
	}
	Replace(AC).then(function(res){
		console.log(res);
		if(res.error == 0){
			var id = res.data[0].id;
			for(var i = 0; i < varArr.length; i++){
				let variedad = varArr[i]*1 == 0?-1:varArr[i];
				values.push({
					idAsignarCertificado: id,
					idVariedad: variedad
				})
			}
			acv.VALUES = values;
			Replace(acv).then(function(res){
				console.log(res);
				$('#modal-insert').modal('toggle');
				swal({
					  title: 'Se ingreso correctamente la carga manual!',
					  animation: true,
					  customClass: 'animated tada'
				})
				var table = $('#datatable_ajax').DataTable({bRetrieve : true});
				table.ajax.reload();
			})
		}else{
			alert(res.mensaje)
		}
		
	})
	
//	var row = {};
//	row.codProductor=$("#codProductor").val();
//	row.fecha=$("#fecha").val();
//	row.idCertificado = $("#idCertificado").val();
//	row.codParcela=$("#codParcela").val();
//	row.codTurno=$("#codTurno").val();
//	row.idVariedad=$("#idVariedad").val();
//	console.log(row);
//	var search = JSON.stringify(row);
//	console.log(search);
//
//	$("#laboratorio").val("");
//	$("#Identificador").val("");
//	$("#codProductor").val("");
//	$("#firstName").val("");
//	$("#codProductoNew").val("");
//	$("#limiteInput").val("");
//	$("#tblPeticion").empty("");
//	$("#codParcela").val("");
//	$("#idVariedad").val("");
//	$("#codTurno").val("");
//		 
//		$.ajax({
//			url : "restPesticidaOlmos/"+"json/asignarCertificado/add",
//			type : "PUT",
//			data : search,
//			beforeSend : function(xhr) {
//				xhr.setRequestHeader("Accept",
//						"application/json");
//				xhr.setRequestHeader("Content-Type",
//						"application/json");
//			},
//
//			success : function(data, textStatus, jqXHR) {
//				
//				$('#modal-insert').modal('toggle');
//			swal({
//				  title: 'Se ingreso correctamente la carga manual!',
//				  animation: true,
//				  customClass: 'animated tada'
//				})
//				var table = $('#datatable_ajax')
//						.DataTable({
//							bRetrieve : true
//						});
//			
//			
//				table.ajax.reload();
//			},
//			error : function(jqXHR, textStatus,
//					errorThrown) {
//				console.log("error");
//
//			}
//		});

}

function actualizar(){
	var lab = $("#laboratorioUpdate").val();
	var identif =  $("#idCertificadoUpdate").val();
	var cod_productor =  $("#codProductorUpdate").val(); 
	var fecha_update = $("#fechaUpdate").val();
	var limite = $("#limiteInputUpdate").val();
	var cod_producto = $("#codProductoUpdate").val();
	var id_update = $("#idUpdate").val();
 
	var cod_Parcela = $("#codParcelaUpdate").val();
	var id_Variedad = $("#idVariedadUpdate").val();
	var codTurno = $("#codTurnoUpdate").val();
	var ID=$("#idUpdate").val();
	if(identif === ''){alert("Debe Ingresar un Identificador"); $( "#idCertificadoUpdate" ).focus();return;}
	else if(cod_productor === ''){alert("Debe Ingresar un Productor"); $( "#codProductorUpdate" ).focus();return;}
	else if(fecha_update === ''){alert("Debe Ingresar una Fecha"); $( "#fechaUpdate" ).focus();return;}
	else if(cod_producto === ''){alert("Debe Ingresar un Componente "); $( "#codProductoUpdate" ).focus();return;}
		
	
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
	var ID=$("#idUpdate").val();
	var values = [];
	var acv = {
		TABLE: "asignarCertificadoVariedad",
	}
	var varArr = $("#idVariedadUpdate").val();
	
	var AC = {
		TABLE: "asignarCertificado",
		SET: {
			codProductor: cod_productor,
			codParcela: cod_Parcela,
			codTurno: codTurno,
			idVariedad: 0,
			fecha: resultadofecha,
			idCertificado: identif
		},
		WHERE: {
			id: ID
		}
	}
	var del = {
		TABLE: "asignarCertificadoVariedad",
		WHERE: {
			idAsignarCertificado: ID
		}
	}
	Delete(del).then(function(ResDel){
		if(ResDel.error == 0){
			Update(AC).then(function(res){
				console.log(res);
				if(res.error == 0){
					for(var i = 0; i < varArr.length; i++){
						let variedad = varArr[i]*1 == 0?-1:varArr[i];
						values.push({
							idAsignarCertificado: ID,
							idVariedad: variedad
						})
					}
					acv.VALUES = values;
					Replace(acv).then(function(res){
						console.log(res);
						$('#modal-update').modal('toggle');
						swal({
							  title: 'Se actualizo correctamente la carga manual!',
							  animation: true,
							  customClass: 'animated tada'
						})
						var table = $('#datatable_ajax').DataTable({bRetrieve : true});
						table.ajax.reload();
					})
				}else{
					alert(res.mensaje)
				}
			})
		}else{
			swal({
				  title: ResDel.mensaje,
				  animation: true,
				  customClass: 'animated tada'
			})
		}
	})
	
	
	
	
//	 var lab = $("#laboratorioUpdate").val();
//	 var identif =  $("#idCertificadoUpdate").val();
//	 var cod_productor =  $("#codProductorUpdate").val(); 
//	 var fecha_update = $("#fechaUpdate").val();
//	 var limite = $("#limiteInputUpdate").val();
//	 var cod_producto = $("#codProductoUpdate").val();
//	 var id_update = $("#idUpdate").val();
//	 
//	 var cod_Parcela = $("#codParcelaUpdate").val();
//	 var id_Variedad = $("#idVariedadUpdate").val();
//	 var codTurno = $("#codTurnoUpdate").val();
//	 var ID=$("#idUpdate").val();
//	 
//	 
//	 
//	 if(identif === ''){alert("Debe Ingresar un Identificador"); $( "#idCertificadoUpdate" ).focus();return;}
//	 else if(cod_productor === ''){alert("Debe Ingresar un Productor"); $( "#codProductorUpdate" ).focus();return;}
//	 else if(fecha_update === ''){alert("Debe Ingresar una Fecha"); $( "#fechaUpdate" ).focus();return;}
//	 else if(cod_producto === ''){alert("Debe Ingresar un Componente "); $( "#codProductoUpdate" ).focus();return;}
//		
//	
//	 var valNew = fecha_update.split('-');
//	 var valNew2 = valNew[0]+"-"+valNew[1]+"-"+valNew[2];
//	 var d = new Date(); 
//	 var segundos = d.getSeconds();
//	 
//	 if((""+segundos+"").length == 1 ){
//		 var nuevoNumero = "0"+segundos;
//		 datetext = d.getHours()+":"+d.getMinutes()+":"+nuevoNumero;
//	 }else{
//		 datetext = d.getHours()+":"+d.getMinutes()+":"+segundos;
//	 }
//	 
//	 
//	var resultadofecha = valNew2 +" "+datetext;
//	 
//			
//	var enviarDatos = {
//		    idCertificado : identif,
//			codProductor : cod_productor,
//			fecha : resultadofecha,
//			codParcela:cod_Parcela,
//			codTurno:codTurno,
//			idVariedad:id_Variedad,
//			id:ID
//	}
//	console.log(enviarDatos);
//	$.ajax({
//		url : "restPesticidaOlmos/"+"json/asignarCertificado/updateAsignacion",
//		type : "PUT",
//		data : JSON.stringify(enviarDatos),
//		beforeSend : function(xhr) {
//			xhr.setRequestHeader("Accept","application/json");
//			xhr.setRequestHeader("Content-Type","application/json");
//		},
//		success : function(data, textStatus, jqXHR) {
//			  
//			$('#modal-update').modal('toggle');
//			swal({
//				  title: 'Se actualizo correctamente la carga manual!',
//				  animation: true,
//				  customClass: 'animated tada'
//				})
//				var table = $('#datatable_ajax')
//						.DataTable({
//							bRetrieve : true
//						});
//			
//			
//				table.ajax.reload();
//		  
//		},
//		error : function(ex) {
//			
//		}
//
//	})
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
				
				var options = "<option value='-1'>Todos</option>";
				$.each(data, function(ks,val){
					options += "<option value='"+val.idVariedad+"'>"+val.nombre+"</option>";
				})
				$(".idVariedad").html(options);
				
//				var options = "<option value='0'>Todas</option>";
//				
//				$(data).each(function(key, val){
//					options += "<option value='"+val.idVariedad+"'>"+val.nombre+"</option>";
//				})
//			
//				$('.idVariedad').html(options);
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
						"ajax" : {"url" : "/AgroVisionRestricciones/"+"json/asignarCertificado/view"},
						"columnDefs" : [
						        { "visible": false, "targets": [0] },     
								{
									"targets" : [ 7 ],
									"render" : function(data, type, full) {
										
										
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm'>";


										html += "<a class='col-md-6 btn grey btn-table  pull-left button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[0]
												+ "' href='#modal-update'><i class='fa fa-pencil-square'></i></a> ";
										html += "<a class='col-md-6 btn red btn-table  pull-left' onclick='deleteM(" 
											+ full[0]
											+ ")' ><i class='fa fa-trash'></i></a> ";

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
					console.log(id);
					$.ajax({
						type : 'GET',
						url : "/AgroVisionRestricciones/"+"json/asignarCertificado/" + id,
						data : "",
						success : function(data) {
							console.log(data);
							$('#codProductorUpdate').val(data.codProductor);
							$('#codProductorUpdate').trigger("change");
							setTimeout(function(){ 
								$("#codParcelaUpdate").val(data.codParcela);
								$('#codParcelaUpdate').trigger("change");
							}, 1000);
							setTimeout(function(){ 
								$("#codTurnoUpdate").val(data.codTurno);
								$('#codTurnoUpdate').trigger("change");
							}, 2000);
							setTimeout(function(){ 
								$("#idVariedadUpdate").val(data.idVariedad*1);
								$("#idVariedadUpdate").trigger("change");
							}, 3000);
							$("#idCertificadoUpdate").val(data.idCertificado);
							var res = data.fecha.slice(0, 10);
							$("#fechaUpdate").val(res);
							
							
							
						 
						
						    
							
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
//									url : "restPesticidaOlmos/"+"json/tipoProducto/put",
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
//									url : "restPesticidaOlmos/"+"json/cargaManual/add",
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
	$('.codTurno').empty();
	$('.codTurno').append('<option value="">Seleccionar</option>');
	var productor=$('#codProductor').children("option:selected").val();
	$.ajax({
		url : "/AgroVisionRestricciones/"+"json/turno/getAllByParcela/"+productor+"/"+this.value,
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
				options += "<option value='"+val.codTurno+"'>"+val.nombre+"</option>";
			})
		
			$('.codTurno').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });
$('#codTurno').on('change', function() {
	$('.idVariedad').empty();
	$('.idVariedad').append('<option value="-1">Todas</option>');
	var codParcela=$('#codParcela').children("option:selected").val();
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
	$('.codTurnoUpdate').empty();
	$('.codTurnoUpdate').append('<option value="">Seleccionar</option>');
	var productor=$('#codProductorUpdate').children("option:selected").val();
	$.ajax({
		url : "/AgroVisionRestricciones/"+"json/turno/getAllByParcela/"+productor+"/"+this.value,
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
				options += "<option value='"+val.codTurno+"'>"+val.nombre+"</option>";
			})
		
			$('.codTurnoUpdate').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
});
$.ajax({
	url : "/AgroVisionRestricciones/"+"json/certificado/getCertificados",
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
			options += "<option value='"+val.id+"'>"+val.nombre+"</option>";
		})
	
		$('.idCertificado').append(options);
	},
	error : function(jqXHR, textStatus,
			errorThrown) {
	}
});
function deleteM(id){
	var c = confirm("Se eliminara toda la informacion referente a este registro  \n¿Desea Continuar?");
	if(c){
		var t = {
			SP: "deleteAsignarCertificado",
			FILTERS: {
				p_id: id
			}
		}
		console.log(t)
		callSp(t).then(function(res){
			if(res.error == 0){
				alert("Eliminado");
				location.reload();
			}else{
				alert(res.message)
			}
		})
	}
}
$('#codTurnoUpdate').on('change', function() {
	$('.idVariedadUpdate').empty();
	$('.idVariedadUpdate').append('<option value="-1">Todas</option>');
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
















