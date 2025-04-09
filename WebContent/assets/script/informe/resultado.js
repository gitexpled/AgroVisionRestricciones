let tabla;
let tabla2;
$("body").on("click","#linkView",function(e){
			var id = $(this).data("id");
			
			console.log($(this))
			const getDet = {
			SP: "get_ResultadoDet",
			FILTERS: {
				p_code: id
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
				
				let tbl = [v.Productor, v.Etapa, v.Campo, v.Turno, v.Variedad, v.producto, v.lmr]
				datos.push(tbl)
			})
			
			if(tabla2){
				tabla2.destroy();
		        $('#tbl_det').empty();
			}
			columnas = ["Productor","Etapa", "Campo", "Turno", "Variedad", "producto", "lmr"];
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
$("body").on("click","#linkPdf",function(e){
			var id = $(this).data("id");
			window.open("/AgroVisionRestricciones/json/resultado/viewPdf/" + id);
})
const get = {
	SP: "get_Resultado"
}

callSp(get).then(function(res){
	console.log(res)
	if(res.error != 0){
		alert(res.message);
		return;
	}
	let datos = [];
	
	$.each(res.data, function(k,v){
		var link="<div style='float:left!important;' class='btn-group pull-right  btn-group-sm'>";
		link +="<a class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'  data-toggle='modal' id='linkView' data-id='"+v.code+"' href='#modal-view'><i class='fa fa-search-plus'></i></a>";
		link +="<a class='col-md-6 btn grey btn-table  pull-right button-grilla-modifica-cuenta'   id='linkPdf' data-id='"+v.code+"' ><i class='fa fa-file-pdf-o'></i></a>";
		link +="</div>";
		
		let tbl = [v.creado,v.muestreo, v.nombre, v.userLab, v.code, v.sampleNumber, v.trazabilidad,v.variedad, v.procesado,link]
		datos.push(tbl)
	})
	
	if(tabla){
		tabla.destroy();
        $('#tbl_RendimientoVlidadr').empty();
	}
	columnas = ["Creado","muestreo", "Productor", "User Lab", "Codigo", "Numero", "Trazabilidad","variedad", "Procesado","ver&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"];
	var finalColumn = [];
	for(var i = 0; i < columnas.length; i++){
		finalColumn.push({title: columnas[i]})
	}
	tabla = $('#tbl_RendimientoVlidadr').DataTable({
		data: datos,
		columns: finalColumn,
		autoWidth: false,
		bAutoWidth: false,
		ordering: false,
    	 dom: 'Bfrtip',
		  buttons: [
		    {
                    extend: 'excelHtml5',
                    title: 'Resultados'
                }
		  ]
		
	});

	$("#tbl_RendimientoVlidadr_filter").hide();
	$("#tbl_RendimientoVlidadr_length").hide();

	
	$('#tbl_RendimientoVlidadr thead tr').clone(true).appendTo( '#tbl_RendimientoVlidadr thead' );
    $('#tbl_RendimientoVlidadr thead tr:eq(1) th').each( function (i) {
    	if($(this).text() != "" && i<9){
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