var TableDatatablesAjax = function() {
	
	
	
	var populateForm = function()
	{
		
		
		$.ajax({
			url : "/AgroVisionRestricciones/"+"json/especie/getAllOutFilter",
			type : "GET",
			data : "",
			timeout: 500000,
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept",
						"application/json");
				xhr.setRequestHeader("Content-Type",
						"application/json");
			},

			success : function(data, textStatus, jqXHR) {
				var options = "";
				
				$(data).each(function(key, val){
					var url_string = location.href; //window.location.href
					var url = new URL(url_string);
					var c = url.searchParams.get("key");
					console.log(c);
					if (c==val.especie)
						options += "<option value='"+val.idEspecie+"' selected>"+val.especie+"</option>";
					else
						options += "<option value='"+val.idEspecie+"' >"+val.especie+"</option>";	
				})
			
				$('.btn_especie').append(options);
				//document.getElementById("buscarID").click();
				//$('#buscarID').trigger($.Event("click", { keyCode: 13 }));
				//$("#datatable_ajax").DataTable().ajax.reload();
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
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
						"scrollY": 500,
				        "scrollX": true,
				        "searching": true,
				        "searchable": true,
				        
						"bStateSave" : true, // save datatable
												// state(pagination, sort, etc)
												// in cookie.
					
						"lengthMenu" : [ [  -1 ],
								[  "All" ] // change per
																// page values
																// here
						],
						"pageLength" : -1, // default record count per page
						"ajax" : {
							"url" : "/AgroVisionRestricciones/"+"json/estadoProductor/view", // ajax
							"timeout": 500000,		
																				// source
						},
						"columnDefs" : [
						           {
											"targets" : [0,1,2,3,4,5,6,7],
						           "render" : function(data, type, row,meta ) {
										var html =data;
										 ;
										
										return html;
									}
								    }
						           ,
									{
									"targets" : [-1] ,
									
									"render" : function(data, type, full) {
										var html = "<div style='float:left!important;' class='btn-group pull-right  btn-group-sm '>";

										html += "<a    class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'   "
											+ "' target='_blank' href='caData/"+full[4]+"'><i class='fa fa-database'></i></a> ";

										
										html += "<a  class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta' "
											+ "' target='_blank' href='caExcel/"+full[4]+"'><i class='fa fa-file-excel-o'></i></a> ";

									html += "</div>";

									html="";
										
										
										return html;
									}
								},
						            {

									"targets" : '_all' ,
									"searchable": false,
									"render" : function(data, type, full,meta) {
										var html = "";
										var especie=$("#viewEspecie").val()
										var colName=meta.settings.aoColumns[meta.col].sTitle;
										console.log(full)
										if (data=='NO')
											html="<a data-toggle='modal'  data-id='/AgroVisionRestricciones/json/detalleRest/"+colName+"/"+especie+"/"+full[3]+"/"+full[4]+"/"+full[5]+"/"+full[6]+"/"+full[7]+"' href='#modal-informe'>NO</a>";
										else
											html="<a data-toggle='modal'  data-id='/AgroVisionRestricciones/json/detalleRest/"+colName+"/"+especie+"/"+full[3]+"/"+full[4]+"/"+full[5]+"/"+full[6]+"/"+full[7]+"'  href='#modal-informe'>SI</a>";
										
										return html;
									}}],
						"order" : [ [ 1, "des" ] ]
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
		
		
		
        $(".filter-submit-header").on('click', function(e) {

        	e.preventDefault();
        	  $('textarea.form-filter2, select.form-filter2, input.form-filter2:not([type="radio"],[type="checkbox"])').each(function() {
        		  grid.setAjaxParam($(this).attr("name"), $(this).val());
               
              });
        	
            grid.submitFilter();
            

            
        });
        
        $('#viewEspecie').change(function(e){
        	e.preventDefault();
      	  $('textarea.form-filter2, select.form-filter2, input.form-filter2:not([type="radio"],[type="checkbox"])').each(function() {
      		  grid.setAjaxParam($(this).attr("name"), $(this).val());
             
            });
      	
          grid.submitFilter();
        })

        $(".filter-submit2").on('click', function(e) {
        	
            e.preventDefault();
           // $('textarea.form-filter2, select.form-filter2, input.form-filter2:not([type="radio"],[type="checkbox"])').each(function() {
      		  grid.setAjaxParam("vw_especie", $('#viewEspecie').val());
              grid.setAjaxParam("vw_productor", $('#vw_productor').val());
              grid.setAjaxParam("vw_etapa", $('#vw_etapa').val());
              grid.setAjaxParam("vw_campo", $('#vw_campo').val());
              grid.setAjaxParam("vw_turno", $('#vw_turno').val());
              grid.setAjaxParam("vw_variedad", $('#vw_variedad').val());
               
           // });
            grid.submitFilter();
        });
        
        $(".filter-cancel").on('click', function(e) {
        	
            e.preventDefault();
           // $('textarea.form-filter2, select.form-filter2, input.form-filter2:not([type="radio"],[type="checkbox"])').each(function() {
      		 
              grid.setAjaxParam("vw_productor", '');
              grid.setAjaxParam("vw_etapa", '');
              grid.setAjaxParam("vw_campo", '');
              grid.setAjaxParam("vw_turno", '');
              grid.setAjaxParam("vw_variedad", '');
              
              
              $('#vw_productor').val('');
              $('#vw_etapa').val('');
              $('#vw_campo').val('');
              $('#vw_turno').val('');
              $('#vw_variedad').val('');
              
               
           // });
            grid.submitFilter();
        });

	}

	
	
	var obtener = function() {
		$("#modal-modifica-tipoProducto").on(
				'show.bs.modal',
				function(e) {

					var button = $(e.relatedTarget);// Button which is clicked
					var id = button.data('id');// Get id of the button
					ID=id;
					$.ajax({
						type : 'GET',
						url : "/AgroVisionRestricciones/json/tipoProducto/" + id,
						data : "",
						success : function(data) {
							console.log(data)
							$("#updateTipoProducto").val(data.tipoProducto);
							$("#updateFeCreacion").val(data.creado);

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
						updateTipoProducto : {
							required : true,
							rangelength : [ 5, 50 ],
							alfanumerico : true
						}

					},

					messages : {

						updateTipoProducto : {
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

						row.tipoProducto = $('#updateTipoProducto').val();
						row.idTipoProducto = ID;
						
						$.ajax({
									url : "/AgroVisionRestricciones/"+"json/tipoProducto/put",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-modifica-tipoProducto').modal("toggle");
										swal({
											  title: 'Tipo Producto Modificada exitosamente!',
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
	
	var insertTipoProducto = function(){
		var row = {};

		
		
		var form1 = $('#form-InsertTipoProducto');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						regTipoProducto : {
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
						row.idUser = $('#idUserPefil').val();
						console.log(row);

						$.ajax({
									url : "/AgroVisionRestricciones/"+"json/tipoProducto/insertTipoProducto",
									type : "PUT",
									data : JSON.stringify(row),
									beforeSend : function(xhr) {
										xhr.setRequestHeader("Accept",
												"application/json");
										xhr.setRequestHeader("Content-Type",
												"application/json");
									},

									success : function(data, textStatus, jqXHR) {
										$('#modal-newTipoProducto').modal('toggle');
									swal({
										  title: 'Tipo Producto ingresada exitosamente!',
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
	
	var obtener = function() {
		$("#modal-informe").on(
				'show.bs.modal',
				function(e) {
					$('#infoRaw').html("");
					var button = $(e.relatedTarget);// Button which is clicked
					var id = button.data('id');// Get id of the button
					ID=id;
					$.ajax({
						   url:id,
						   type:'GET',
						   success: function(data){
						       $('#infoRaw').html(data);
						   }
						});
					});

	}
	
	var handlePortletAjax = function () {
        //custom portlet reload handler
        $('#my_portlet .portlet-title a.reload').click(function(e){
            e.preventDefault();  // prevent default event
            e.stopPropagation(); // stop event handling here(cancel the default reload handler)
            // do here some custom work:
            App.alert({
                'type': 'danger', 
                'icon': 'warning',
                'message': 'Custom reload handler!',
                'container': $('#my_portlet .portlet-body') 
            });
        })
    }

	return {

		// main function to initiate the module
		init : function() {
			handlePortletAjax();
			populateForm();
			handleDemo1();
			
			insertTipoProducto();
			editar();
			obtener();
			jQuery.validator.addMethod("alfanumerico",
					function(value, element) {
						return this.optional(element)
								|| /^[a-zA-Z0-9._-]+$/.test(value);
					}, "solo números ddddd");
		}

	};

}();
function syncDatos(){
	var s = {
		SP:"insertRestricciones"
	}
	console.log(s)
	callSp(s).then(function(res){
		console.log(res);
		if(res.error == 0){
			alert("Carga completa");
			$("#viewEspecie").trigger("change")
		}else{
			alert(res.mensaje)
		}
		
	})
}

jQuery(document).ready(function() {
	TableDatatablesAjax.init();
	
});
