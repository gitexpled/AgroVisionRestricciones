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
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm'>";

										var str = full[4]; 
										 var res = str.replace("/ ", "");
										html += "<a class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal'  data-id='"
												+ full[0]+'_'+ full[1]+'_'+ full[2]+'_'+ full[3]+'_'+res
												+ "' href='#modal-update'><i class='fa fa-pencil-square'></i></a> ";
										html += "<a onclick='' class='col-md-6 btn red btn-table pull-right button-grilla-elimina-cuenta drop'   data-id='"
												+ full[0]+'_'+ full[1]+'_'+ full[2]+'_'+ full[3]+'_'+res
												+ "' data-toggle='modal'><i class='fa fa-trash-o'></i></a>";
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
					ID=id;
					$.ajax({
						type : 'GET',
						url : "/AgroVisionRestricciones/json/alarmaProductor/" + id,
						data : "",
						success : function(data) {
						
							$("#keyId").val("");
							
							$("#codProductor").val(data.codProductor);
							$("#codParcela").val(data.codParcela);
							$("#codTurno").val(data.codTurno);
							$("#idVariedad").val(data.idVariedad);
							$("#proyecto").val(data.proyecto);
							$("#keyId").val(id);
							
							
							$("#codProductorNew").val("");
							$("#codParcelaNew").val("");
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
						row.codTurnoNew = $('#codTurnoNew').val();
						row.idVariedadNew = $('#idVariedadNew').val();
						row.keyID = $("#keyId").val();;
						row.idTemporada = $('#idTempActual').val();
						
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



$('#codProductorNew').on('change', function() {
	$('.codParcelaNew').empty();
	$('.codParcelaNew').append('<option value="">Seleccionar</option>');
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
		
			$('.codParcelaNew').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });

$('#codParcelaNew').on('change', function() {
	$('.codTurnoNew').empty();
	$('.codTurnoNew').append('<option value="">Seleccionar</option>');
	var productor=$('#codProductorNew').children("option:selected").val();
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
		
			$('.codTurnoNew').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });
$('#codTurnoNew').on('change', function() {
	$('.idVariedadNew').empty();
	$('.idVariedadNew').append('<option value="">Seleccionar</option>');
	var codParcela=$('#codParcelaNew').children("option:selected").val();
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
				options += "<option value='"+val.cod+"'>"+val.cod+'-'+val.nombre+"</option>";
			})
		
			$('.idVariedadNew').append(options);
		},
		error : function(jqXHR, textStatus,
				errorThrown) {
		}
	});
   
  });

  
  
  
  
  
  
  
  
  
  
  
  
  