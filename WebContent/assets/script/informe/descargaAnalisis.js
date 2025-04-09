let tabla;

const get = {
	SP: "get_analisis"
}

callSp(get).then(function(res){
	console.log(res)
	if(res.error != 0){
		alert(res.message);
		return;
	}
	let datos = [];
	
	$.each(res.data, function(k,v){
		
		
		let tbl = [v.temporada,v.creado, v.code, v.Productor, v.Etapa, v.Campo, v.Turno,v.EspecieDenomina,v.VariedadDenomina, v.Producto, v.resultado,v.vigente, v.carga, v.dfa]
		datos.push(tbl)
	})
	
	if(tabla){
		tabla.destroy();
        $('#tbl_RendimientoVlidadr').empty();
	}
	columnas = ["temporada","creado","code","Productor","Etapa","Campo","Turno","Especie","Variedad","Producto","resultado","vigente","carga","dfa"];
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
                    title: 'Analisis'
                }
		  ]
		
	});

	$("#tbl_RendimientoVlidadr_filter").hide();
	$("#tbl_RendimientoVlidadr_length").hide();

	
	$('#tbl_RendimientoVlidadr thead tr').clone(true).appendTo( '#tbl_RendimientoVlidadr thead' );
    $('#tbl_RendimientoVlidadr thead tr:eq(1) th').each( function (i) {
    	if($(this).text() != "" ){
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