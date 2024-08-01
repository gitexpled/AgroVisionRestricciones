var mainPerfil = function(){
	var chargeData = function(){
		$.ajax({
			type: 'GET',
			url: '/AgroVisionRestricciones/json/user/'+$('#idUserPefil').val(),
			data: "",
			success: function(data){
				console.log(data);
				$('#user').val(data.user);
				$('#nombre').val(data.nombre);
				$('#apellido').val(data.apellido);
				$('#mail').val(data.mail);
				$('#password').val(data.password)
			}
		})
	}
	
	var editar = function() {

		var row = {};

		var form1 = $('#updatePerfil-form');

		form1.validate({
					errorElement : 'span', 
					errorClass : 'help-block help-block-error',
					focusInvalid : true, 
					rules : {
						user : {
							required : true,
							rangelength : [ 5, 50 ],
							alfanumerico : true
						},
						nombre : {
							required : true,
							rangelength : [ 2, 50 ],
							alfanumerico : true
						},
						apellido :{
							required : true,
							rangelength : [ 2, 50 ],
							alfanumerico : true
						},
						mail : {
							email: true,
							required: true
						},
						password : {
							required: true,
							rangelength : [5,50]
						}

					},

					messages : {

						user : {
							required : "Este campo es obligatorio",
							rangelength : "Debe ser mayor a 5 y menor a 50",
							alfanumerico : "Ingrese sólo valores alfanumericos"
						},
						nombre : {
							required : "Este campo es obligatorio",
							rangelength : "Debe ser mayor a 2 y menor a 50",
							alfanumerico : "Ingrese sólo valores alfanumericos"
						},
						apellido :{
							required : "Este campo es obligatorio",
							rangelength : "Debe ser mayor a 2 y menor a 50",
							alfanumerico : "Ingrese sólo valores alfanumericos"
						},
						mail : {
							email: "Ingrese un correo valido.",
							required: "Este campo es obligatorio"
						},
						password : {
							required : "Este campo es obligatorio",
							rangelength : "Debe ser mayor a 5 y menor a 50"
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

						row.id = $('#idUserPefil').val();
						row.nombre = $("#nombre").val();
						row.apellido = $("#apellido").val();
						row.user = $("#user").val();
						row.password = $("#password").val();
						row.mail = $('#mail').val();

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
	
	return {

		// main function to initiate the module
		init : function() {
			chargeData();
			editar();
			jQuery.validator.addMethod("alfanumerico",
					function(value, element) {
						return this.optional(element)
								|| /^[a-zA-Z0-9._-]+$/.test(value);
					}, "solo números ddddd");
		}

	};
}();

jQuery(document).ready(function() {
	mainPerfil.init();
});