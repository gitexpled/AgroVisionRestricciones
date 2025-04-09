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
		
		$('#form-new').repeater({
			 isFirstItemUndeletable: true,
           defaultValues: {
               'limite': '',
               'codProducto': ''
           },
           show: function () {
        	   
        	   
        	   $(this).slideDown();
              // if ($('#laboratorio').val()=="2")
        	   //$(this).setList();
              
       		
              
           },
           hide: function (deleteElement) {
               if(confirm('Esta seguro de que desea eliminar el registro?')) {
                   $(this).slideUp(deleteElement);
               }
           },
           ready: function (setIndexes) {
        	  
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
						"ajax" : {"url" : "/AgroVisionRestricciones/"+"json/alarmaProductor/view"},
						"columnDefs" : [
						        
								{
									"targets" : [ 6 ],
									"render" : function(data, type, full) {
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm '>";

										var str = full[4]; 
										var res = full[4];
										html +="<a class='col-md-4 btn grey btn-table  pull-right button-grilla-view'  data-toggle='modal' id='linkView' data-id='"+ full[0]+'_'+ full[1]+'_'+ full[2]+'_'+ full[3]+'_'+full[4]+'_'+full[6]+"' href='#modal-view'><i class='fa fa-search-plus'></i></a>";
										
										html += "<a class='col-md-4 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[0]+'_'+ full[1]+'_'+ full[2]+'_'+ full[3]+'_'+full[4]+'_'+full[6]
												+ "' href='#modal-update'><i class='fa fa-pencil-square'></i></a> ";
										html += "<a onclick='' class='col-md-4 btn red btn-table pull-right button-grilla-elimina-cuenta drop'   data-id='"
												+ full[0]+'_'+ full[1]+'_'+ full[2]+'_'+ full[3]+'_'+full[4]
												+ "' data-toggle='modal'><i class='fa fa-trash-o'></i></a>";
										html += "</div>";

										return html;

									}
								}],
						"order" : [ [ 1, "desc" ] ]

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
					ID=id;
					$.ajax({
						type : 'GET',
						url : "/AgroVisionRestricciones/json/alarmaProductor/" + id,
						data : "",
						success : function(data) {
						
							$("#keyId").val("");
							
							$("#codProductor").val(data.codProductor);
							$("#codParcela").val(data.codParcela);
							$("#codCampo").val(data.codCampo);
							$("#codTurno").val(data.codTurno);
							$("#idVariedad").val(data.idVariedad);
							
							$("#keyId").val(id);
							
							
							$("#codProductorNew").val("");
							$("#codParcelaNew").val("");
							$("#codCampoNew").val("");
							$("#codTurnoNew").val("");
							$("#idVariedadNew").val("");
							
							
							
							var $repeater = $('#form-update').repeater({
						           show: function () {
						        	   $(this).slideDown();
						           },
						           hide: function (deleteElement) {
						               if(confirm('Esta seguro de que desea eliminar el registro?')) {
						                   $(this).slideUp(deleteElement);
						               }
						           },
						           ready: function (setIndexes) {
						        	  
						           }
						       });
							
							$repeater.setList(data.detalle);

						}
					});
				});
	}

	var update = function() {

		var row = {};
		var form1 = $('#form-update');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						codProductorNew : {
							required : true
						},
						codParcelaNew : {
							required : true
						},
						codTurnoNew : {
							required : true
						},
						idVariedadNew : {
							required : true
						}
						

					},

					messages : {

						codProductorNew : {
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

						row.codProductorNew = $('#codProductorNew').val();
						row.codParcelaNew = $('#codParcelaNew').val();
						row.codCampoNew = $('#codCampoNew').val();
						row.codTurnoNew = $('#codTurnoNew').val();
						row.idVariedadNew = $('#idVariedadNew').val();
						row.keyID = $("#keyId").val();;
						row.idTemporada = $('#idTempActual').val();
						console.log(row);
						$.ajax({
									url : "/AgroVisionRestricciones/"+"json/alarmaProductor/put",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-update').modal("toggle");
										swal({
											  title: 'se actualiza dato en planilla!',
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

		
		
		var form1 = $('#form-new');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						laboratorio : {
							required : true,
							rangelength : [ 2, 50 ],
							alfanumerico : true
						},
						'detalle[][limite]' : {
							required : true,
							rangelength : [ 2, 50 ],
							alfanumerico : true
						}

					},

					messages : {

						regTipoProducto : {
							required: "Este campo es obligatorio",
							rangelength : "No debe ser menor a 2 y mayor a 50 caracteres",
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

						row.tipoProducto = $('#regTipoProducto').val();
						row.idUser = "asasas";
						
						
						
						
						
						var obj = $('#form-new').serializeObject();
						var formdata= $('#form-new').serialize();
						
						var formdata = $("#form-new").serializeArray();
						var data = {};
						i=0
						row = {};
						 obj.detalle=[]
						$(formdata ).each(function(index, o){
						    //data[obj.name] = obj.value;
							 console.log(o.name+"->"+o.name.indexOf("detalle"));
						    if (o.name.indexOf("detalle")>=0)
						    {
						    	 console.log(o.name.indexOf("codProducto"));
						    	 
						    	 if (o.name.indexOf("codProducto")>=0)
						    		 row.codProducto=o.value;
						    	 if (o.name.indexOf("limite")>=0)
						    		 row.limite=o.value;
						    	 
						    	 i++
						    	 if (i>=2)
						    	 {
						    		 i=0;
						    		 obj.detalle.push(row)
						    		 row = {};
						    		 
						    	 }
						    }
						})
						
					    

						
						
						
						
						
						
						
						var search = JSON.stringify(obj);
						 console.log(search);
						
						$.ajax({
									url : "/AgroVisionRestricciones/"+"json/alarmaProductor/add",
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

				});
		
	}
	var drop = function(){
		$("body").on("click",".drop",function(e){
			var id = $(this).data("id");
			$.ajax({
				type : 'GET',
				url : "/AgroVisionRestricciones/"+"json/alarmaProductor/drop",
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
			populateForm();//recarga los select, check box, radio
			getView(); //metodo encargado trabajo con la grilla
			drop();
			insert();//metodo de validacion y envio a funcion de insercion
			
			getId();//obtiene los datos atraves de ajax para su edicion
			update();//metodo de validaciob y envio a funcion de edicion
			
			
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



$('#codProductorNew').on('change', function () {
	$('.codParcelaNew').empty();
	$('.codParcelaNew').append('<option value="">Seleccionar</option>');

	const get = {
		SP: "get_ParcelaByProductor",
		FILTERS: {
			p_productor: this.value
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
				options += "<option value='" + val.codigo + "'>" + val.nombre + "</option>";
			})

			$('.codParcelaNew').append(options);
		}, error: function (e) {
			console.log(e)
		}, complete: function () {
			//return data;
		}
	})
	return;
	

});


$('#codParcelaNew').on('change', function () {
	$('.codCampoNew').empty();
	$('.codCampoNew').append('<option value="">Seleccionar</option>');
	var productor = $('#codProductorNew').children("option:selected").val();

	const get = {
		SP: "get_CampoByProdEtapa",
		FILTERS: {
			p_productor: productor,
			p_etapa: this.value
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
				options += "<option value='" + val.codCampo + "'>" + val.nombre + "</option>";
			})

			$('.codCampoNew').append(options);
		}, error: function (e) {
			console.log(e)
		}, complete: function () {
			//return data;
		}
	})
	return;


}); 

$('#codCampoNew').on('change', function () {
	$('.codTurnoNew').empty();
	$('.codTurnoNew').append('<option value="">Seleccionar</option>');
	var productor = $('#codProductorNew').children("option:selected").val();
	var etapa = $('#codParcelaNew').children("option:selected").val();

	const get = {
		SP: "get_TurnosByProdEtapaCampo",
		FILTERS: {
			p_productor: productor,
			p_etapa: etapa,
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
				options += "<option value='" + val.codTurno + "'>" + val.nombre + "</option>";
			})

			$('.codTurnoNew').append(options);
		}, error: function (e) {
			console.log(e)
		}, complete: function () {
			//return data;
		}
	})
	return;


}); 


$('#codTurnoNew').on('change', function () {
	$('.idVariedadNew').empty();
	$('.idVariedadNew').append('<option value="">Seleccionar</option>');
	var productor = $('#codProductorNew').children("option:selected").val();
	var etapa = $('#codParcelaNew').children("option:selected").val();
	var campo = $('#codCampoNew').children("option:selected").val();

	const get = {
		SP: "get_VariedadByProdEtapaCampoTurno",
		FILTERS: {
			p_productor: productor,
			p_etapa: etapa,
			p_campo: campo,
			p_turno: this.value
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
				options += "<option value='" + val.variedad.replace("'", "@") + "'>" + val.variedad + "</option>";
			})

			$('.idVariedadNew').append(options);
		}, error: function (e) {
			console.log(e)
		}, complete: function () {
			//return data;
		}
	})
	return;


}); 

 

let tabla2;
$("body").on("click","#linkView",function(e){
			var id = $(this).data("id");
			const myArray = id.split("_");
			
			pv_productor=myArray[0];
			pv_etapa=myArray[1];
			pv_campo=myArray[2];
			pv_turno=myArray[3];
			pv_variedad=myArray[4];
			pv_especie=myArray[5];
			
			console.log($(this))
			const getDet = {
			SP: "get_trazabilidadError",
			FILTERS: {
				p_productor: pv_productor,
				p_etapa: pv_etapa,
				p_campo: pv_campo,
				p_turno: pv_turno,
				p_variedad: pv_variedad,
				p_especie: pv_especie
			}
			}
			callSp(getDet).then(function(res){
			console.log(res)
			if(res.error != 0){
			alert(res.message);
			return;
			}
			let datos = [];
			$.each(res.data, function(k,v){
				
				let tbl = [v.Productor, v.UserLab, v.code, v.sampleNumber, v.muestreo]
				datos.push(tbl)
			})
			
			if(tabla2){
				tabla2.destroy();
		        $('#tbl_det').empty();
			}
			columnas = ["Productor","UserLab","Codigo", "Numero", "Muestreo"];
			var finalColumn = [];
			for(var i = 0; i < columnas.length; i++){
				finalColumn.push({title: columnas[i]})
			}
			tabla2 = $('#tbl_det').DataTable({
				data: datos,
				columns: finalColumn,
				autoWidth: false,
				ordering: false,
				pageLength:50
			});
			$("#tbl_det_filter").hide();
			$("#tbl_det_length").hide();
			
		   
		    } );
	})
  
  
  
  
  
  
  
  
  