var PROYECT = "/AgroVisionRestricciones/";
var USER = {};
//$('.select-multiple').select2({
//	multiple: true,
//	placeholder: "Seleccionar"
//});
$(document).ready(function(){
	$('.select-multiple').select2({
		multiple: true,
		placeholder: "Seleccionar"
	});
})
function dateHoy(){
	var hoy = new Date();
	var yyyy = hoy.getFullYear();
	var mm = hoy.getMonth()+1;
	var dd = hoy.getDate();
	if(mm < 10){
		mm = "0"+mm;
	}
	if(dd < 10){
		dd = "0"+dd;
	}
	hoy = (yyyy+"-"+mm+"-"+dd);
	return hoy;
}
$(".select2.select2-container.select2-container--bootstrap").attr("style", "");
$('.select-multiple').each(function(){
	var glovalInput;
	$(this).change(function(){
		var inputSelect = $(this).html();
		var input = $(this).val();
		var newVal;
		var arrSel = [];
		if(input){
			for(var i = 0; i < input.length; i++){
				if(input[i] != -1){
					arrSel.push(input[i]);
				}
			}
		}
		if(glovalInput && input){
			for(var i = 0; i < input.length; i++){
				if(glovalInput.indexOf(input[i]) == -1){
					newVal = input[i];
				}
			}
			if(newVal == -1){
				$(this).html(inputSelect);
				$(this).val(-1);
			}else{
				$(this).html(inputSelect);
				$(this).val(arrSel);
			}
		}
		var input = $(this).val();
		glovalInput = input;
	})
})
function callSp(INPUT){
	if(INPUT.LOADING == undefined){
		INPUT.LOADING = true;
	}
	var data;
	return new Promise( function(resolve) {
		INPUT.LOADING?loading.show():'';
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
var loading = function loading(){}
loading.hide = function(){
	$("#loading").hide();
}
loading.show = function(){
	$("#loading").show();
}