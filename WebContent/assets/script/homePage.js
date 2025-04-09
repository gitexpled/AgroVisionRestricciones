
var ChartsAmcharts = function() {
	
	/*

  	    
	var populateForm = function()
	{
		$.ajax({
			url : "/AgroVisionRestricciones/"+"json/temporadaZona/getAllOutFilter",
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
			
				$('.btn_especie').append(options);
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
			
				$('.btn_especie').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
		
		$.ajax({
			url : "/AgroVisionRestricciones/"+"json/mercado/getAllOutFilter",
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
				options += "<option value='-1'>Todos</option>";
				$(data).each(function(key, val){
					options += "<option value='"+val.idMercado+"'>"+val.mercado+"</option>";
				})
			
				$('.btn_mercado').append(options);
			},
			error : function(jqXHR, textStatus,
					errorThrown) {
			}
		});
		
	
	}
	var initproductorZona = function() {
        var chart = AmCharts.makeChart("chart_productorZona", {
            "type": "serial",
            "theme": "light",
            "pathToImages": App.getGlobalPluginsPath() + "amcharts/amcharts/images/",
            "autoMargins": false,
            "marginLeft": 60,
            
            "marginRight": 70,
            "marginTop": 10,
            "marginBottom": 26,
            

            "fontFamily": 'Open Sans',            
            "color":    '#888',
            "titles": [{
                "text": "ANÁLISIS DE RESIDUOS POR ZONA"
              }],
            
            "dataLoader": {
                "url": "/AgroVisionRestricciones/"+"json/homePage/productorZona/1/2",
              },
              "valueAxes": [{
            	    "axisAlpha": 0,
            	    "position": "left",
            	    "title": "% de análisis realizados"
            	  }],
            
            "startDuration": 1,
            "graphs": [{
                "alphaField": "alpha",
                "balloonText": "<span style='font-size:13px;'> [[category]]: <b>[[value]]%</b> [[additional]]</span>",
                "dashLengthField": "dashLengthColumn",
                "fillAlphas": 1,
                "title": "title",
                "type": "column",
                "valueField": "valor",
                "labelText": "[[value]]%",
               
            }],
            "categoryField": "nombre",
            "categoryAxis": {
            	
                "gridPosition": "start",
                "axisAlpha": 0,
                "tickLength": 0
                
            },
            "export": {
            	"title":"Hola mundo",
                "enabled": true,
                "menu": []
              },
              "listeners": [{
            	    "event": "clickGraphItem",
            	    "method": function(event) {
            	  
            	      window.location.replace("/AgroVisionRestricciones/webApp/page2/informe.zona?key="+event.item.category+"&especie="+$( "#productorZona").val());
            	    }
            	  }]
            

        });
        $( "#productorZona").change(function() {
        	//alert($( "#productorMercado").val());
        	//chart.dataLoader({url:  "/AgroVisionRestricciones/"+"json/homePage/productorZona/"+$( "#productorMercado").val()});
        	AmCharts.loadFile("/AgroVisionRestricciones/"+"json/homePage/productorZona/"+$( "#productorZona").val()+"/2", {}, function(data) {
        	    chart.dataProvider = AmCharts.parseJSON(data);
        	    chart.validateData();
        	  });
        });
        $( "#productorZonaM").change(function() {
        	//alert($( "#productorMercado").val());
        	//chart.dataLoader({url:  "/AgroVisionRestricciones/"+"json/homePage/productorZona/"+$( "#productorMercado").val()});
        	AmCharts.loadFile("/AgroVisionRestricciones/"+"json/homePage/productorZona/"+$( "#productorZona").val()+"/1", {}, function(data) {
        	    chart.dataProvider = AmCharts.parseJSON(data);
        	    chart.validateData();
        	  });
        });
        
       

        $('#chart_pz').closest('.portlet').find('.fullscreen').click(function() {
            chart.invalidateSize();
        });
        
    }
	
    var initproductorMercado = function() {
        var chart = AmCharts.makeChart("chart_productorMercado", {
            "type": "serial",
            "theme": "light",
            "pathToImages": App.getGlobalPluginsPath() + "amcharts/amcharts/images/",
            "autoMargins": false,
            "marginLeft": 60,
            
            "marginRight": 70,
            "marginTop": 10,
            "marginBottom": 26,
            

            "fontFamily": 'Open Sans',            
            "color":    '#888',
            "titles": [{
                "text": "RESTRICCIÓN PRODUCTORES POR MERCADO"
              }],
            
            "dataLoader": {
                "url": "/AgroVisionRestricciones/"+"json/homePage/productorMercado/1",
              },
            "valueAxes": [{
            	"integersOnly": true,
                "axisAlpha": 0,
                "position": "left",
                "title": "Nº productores restringidos"
                	
            }],
            "startDuration": 1,
            "graphs": [{
                "alphaField": "alpha",
                "balloonText": "<span style='font-size:13px;'>[[category]]:<b>[[value]]</b> [[additional]]</span>",
                "dashLengthField": "dashLengthColumn",
                "fillAlphas": 1,
                "title": "valor",
                "type": "column",
                "valueField": "valor",
                "labelText": "[[value]]",
            }],
            "categoryField": "nombre",
            "categoryAxis": {
            	
                "gridPosition": "start",
                "axisAlpha": 0,
                "tickLength": 0
                
            },
            "export": {
            	"title":"Hola mundo",
                "enabled": true,
                "menu": []
              },
              "listeners": [{
          	    "event": "clickGraphItem",
          	    "method": function(event) {
          	  
          	      window.location.replace("/AgroVisionRestricciones/webApp/page2/informe.mercado?key="+event.item.category+"&especie="+$( "#productorMercado").val());
          	    }
          	  }]
            
        });
        $( "#productorMercado").change(function() {
        	//alert($( "#productorMercado").val());
        	//chart.dataLoader({url:  "/AgroVisionRestricciones/"+"json/homePage/productorZona/"+$( "#productorMercado").val()});
        	AmCharts.loadFile("/AgroVisionRestricciones/"+"json/homePage/productorMercado/"+$( "#productorMercado").val(), {}, function(data) {
        	    chart.dataProvider = AmCharts.parseJSON(data);
        	    chart.validateData();
        	  });
        });
     
        
       

        $('#chart_pm').closest('.portlet').find('.fullscreen').click(function() {
            chart.invalidateSize();
        });
        
    }
        
        var inittop10Producto = function() {
            var chart = AmCharts.makeChart("chart_top10", {
                "type": "serial",
                "theme": "light",
                "pathToImages": App.getGlobalPluginsPath() + "amcharts/amcharts/images/",
                "autoMargins": false,
                "marginLeft": 60,
                
                "marginRight": 70,
                "marginTop": 10,
                "marginBottom": 26,
                

                "fontFamily": 'Open Sans',            
                "color":    '#888',
                "titles": [{
                    "text": "INGR. ACTIVO DETECTADO MÁS FRECUENTE"
                  }],
                
                "dataLoader": {
                    "url": "/AgroVisionRestricciones/"+"json/homePage/top10Producto/1",
                  },
                "valueAxes": [{
                	"integersOnly": true,
                    "axisAlpha": 0,
                    "position": "left",
                    "title": "Nº de informes"
                }],
                "startDuration": 1,
                "graphs": [{
                    "alphaField": "alpha",
                    "balloonText": "<span style='font-size:13px;'>[[category]]: <b>[[value]]</b> [[additional]]</span>",
                    "dashLengthField": "dashLengthColumn",
                    "fillAlphas": 1,
                    "title": "valor",
                    "type": "column",
                    "valueField": "valor",
                    "labelText": "[[value]]",
                }],
                "categoryField": "nombre",
                "categoryAxis": {
                	
                    "gridPosition": "start",
                    "axisAlpha": 0,
                    "tickLength": 0
                    
                },
                "export": {
                	"title":"Hola mundo",
                    "enabled": true,
                    "menu": []
                  },
                  "listeners": [{
              	    "event": "clickGraphItem",
              	    "method": function(event) {
              	  
              	      window.location.replace("/AgroVisionRestricciones/webApp/page2/informe.top10?key="+event.item.category+"&especie="+$( "#top10").val());
              	    }
              	  }]
            });
            $( "#top10").change(function() {
            	//alert($( "#productorMercado").val());
            	//chart.dataLoader({url:  "/AgroVisionRestricciones/"+"json/homePage/productorZona/"+$( "#productorMercado").val()});
            	AmCharts.loadFile("/AgroVisionRestricciones/"+"json/homePage/top10Producto/"+$( "#top10").val(), {}, function(data) {
            	    chart.dataProvider = AmCharts.parseJSON(data);
            	    chart.validateData();
            	  });
            });
         
            
           

            $('#chart_top10').closest('.portlet').find('.fullscreen').click(function() {
                chart.invalidateSize();
            });
        
        }
        
        var initProductor5 = function() {
            var chart = AmCharts.makeChart("chart_p5", {
                "type": "serial",
                "theme": "light",
                "pathToImages": App.getGlobalPluginsPath() + "amcharts/amcharts/images/",
                "autoMargins": false,
                "marginLeft": 60,
                
                "marginRight": 70,
                "marginTop": 10,
                "marginBottom": 26,
                

                "fontFamily": 'Open Sans',            
                "color":    '#888',
                "titles": [{
                    "text": "RESTRICCIÓN DE PRODUCTORES CON MÁS DE 5 MOLÉCULAS"
                  }],
                
                "dataLoader": {
                    "url": "/AgroVisionRestricciones/"+"json/homePage/getProductor5/1",
                  },
                "valueAxes": [{
                	"integersOnly": true,
                    "axisAlpha": 0,
                    "position": "left",
                    "title": "Nº de moléculas por informe"
                }],
                "startDuration": 1,
                "graphs": [{
                    "alphaField": "alpha",
                    "balloonText": "<span style='font-size:13px;'>[[title]] en [[category]]:<b>[[value]]</b> [[additional]]</span>",
                    "dashLengthField": "dashLengthColumn",
                    "fillAlphas": 1,
                    "title": "title",
                    "type": "column",
                    "valueField": "valor",
                    "labelText": "[[value]]",
                    "rotation": 90,
                   
                }],
                "categoryField": "nombre",
                "categoryAxis": {
                	
                    "gridPosition": "start",
                    "axisAlpha": 0,
                    "tickLength": 0,
                   "maxSeries":50,
                    "labelRotation":90,
                   
                    "autoWrap":"true",
                    "equalSpacing": true,
                    "maxSeries": 0,
                    "autoGridCount": false,
                    "equalSpacing": true,
                    "gridCount": 1000,
                    
                },
                "export": {
                	"title":"Hola mundo",
                    "enabled": true,
                    "exportTitles": true,
                    "menu": []
                  },
                  "listeners": [{
              	    "event": "clickGraphItem",
              	    "method": function(event) {
              	    	console.log(event)
              	      window.location.replace("/AgroVisionRestricciones/webApp/page3/informe.top5?key="+event.item.category+"&muestra="+event.item.dataContext.title+"&especie="+$( "#prodcuto5").val());
              	    }
              	  }]
            });
            $( "#prodcuto5").change(function() {
            	//alert($( "#productorMercado").val());
            	//chart.dataLoader({url:  "/AgroVisionRestricciones/"+"json/homePage/productorZona/"+$( "#productorMercado").val()});
            	AmCharts.loadFile("/AgroVisionRestricciones/"+"json/homePage/getProductor5/"+$( "#prodcuto5").val(), {}, function(data) {
            	    chart.dataProvider = AmCharts.parseJSON(data);
            	    chart.validateData();
            	  });
            });
         
            
           

            $('#chart_p5').closest('.portlet').find('.fullscreen').click(function() {
                chart.invalidateSize();
            });
        
        }
    
       

return {
    //main function to initiate the module

    init: function() {
    	populateForm();
    	initproductorMercado();
    	initproductorZona();
    	inittop10Producto();
    	initProductor5();
    	

    }

};

}();

jQuery(document).ready(function() {  
ChartsAmcharts.init(); 
});




function exportCharts() {
	  // iterate through all of the charts and prepare their images for export
	  var images = [];
	  var pending = AmCharts.charts.length;
	  for ( var i = 0; i < AmCharts.charts.length; i++ ) {
	    var chart = AmCharts.charts[ i ];
	    chart.export.capture( {}, function() {
	      this.toJPG( {
	        multiplier: 2
	      }, function( data ) {
	        images.push( {
	          "image": data,
	          "fit": [ 523.28, 769.89 ]
	        } );
	        pending--;
	        if ( pending === 0 ) {
	          // all done - construct PDF
	          chart.export.toPDF( {
	            content: images
	          }, function( data ) {
	            this.download( data, "application/pdf", "graficos.pdf" );
	          } );
	        }
	      } );
	    } );
	  }
}

function exportCharts(idGrafico) {
	  // iterate through all of the charts and prepare their images for export

	  var images = [];
	  var pending = AmCharts.charts.length;
	
	    var chart;
	    var allCharts = AmCharts.charts;
	    for (var i = 0; i < allCharts.length; i++) {
	    
	        if (idGrafico == allCharts[i].div.id) {
	        	
	        	chart =allCharts[i];
	        }
	    }
	    chart.export.capture( {}, function() {
	      this.toJPG( {
	        multiplier: 2
	      }, function( data ) {
	        images.push( {
	 
	          "image": data,
	          "fit": [ 523.28, 769.89 ]
	        } );
	     
	          // all done - construct PDF
	          chart.export.toJPG( {
	            content: images
	          }, function( data ) {
	            this.download( data, "application/png", "graficos_"+idGrafico+".png" );
	          } );
	      
	      } );
	    } );
	 */
}






