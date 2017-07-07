angular.module("busqueda",[])
.controller("Busqueda",function($scope,$http){
	$scope.cadena="";
	$scope.filtros=false;
	$scope.tipoBusqueda='titulo';
	
	
	$http({
		method: 'GET',
		url: 'http://localhost:8080/Zinal/webresources/libros/categorias'
			}).then(function successCallback(response) {
				$scope.categorias=response.data;
		});
				
	
	
	$scope.mostrarFiltros=function(){
		$scope.filtros=!$scope.filtros;
	};

	
	$scope.realizarBusqueda=function(){
			
			var busqueda={
				cadena:$scope.cadena,
				filtros:false
			};
			if($scope.filtros){
		
		
			var categorias= [];    
			$('input[type=checkbox]').each(function(){
			if (this.checked) {
				 categorias.push($(this).attr("name"));
				 
			 }
			 }); 
		
		
			 busqueda={
				cadena:$scope.cadena,
				categorias:categorias,
				tipoBusqueda:$scope.tipoBusqueda,
				filtros:true
			};
			
		}
			
			window.location='http://localhost:8080/Zinal/busquedaLibros.html?busqueda='+JSON.stringify(busqueda);
		};
		
		$scope.titulo=function(){
			$scope.tipoBusqueda='titulo';
		};
		$scope.isbn=function(){
			$scope.tipoBusqueda='isbn';
		};
		$scope.autor=function(){
			$scope.tipoBusqueda='autor';
		};

})
.component("busqueda",{
	templateUrl: "./componentes/busquedas/busquedas.html",
	controller: "Busqueda",
	bindings: {
    		num: "@"
 	 }
});


