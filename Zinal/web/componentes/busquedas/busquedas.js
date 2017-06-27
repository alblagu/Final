angular.module("busqueda",[])
.controller("Busqueda",function($scope,$http){
	$scope.busqueda="";
	$scope.filtros=false;
	
	
	$http({
		method: 'GET',
		url: 'http://localhost:8080/TFG3/webresources/generic/categorias'
			}).then(function successCallback(response) {
				$scope.categorias=response.data;
		});
				
	
	
	$scope.mostrarFiltros=function(){
		$scope.filtros=!$scope.filtros;
	};

	
	$scope.realizarBusqueda=function(){
		if($scope.busqueda.length!==0){
			window.location='http://localhost:8080/TFG3/busquedaLibros.html?busqueda='+$scope.busqueda;
		}		
	};

})
.component("busqueda",{
	templateUrl: "./componentes/busquedas/busquedas.html",
	controller: "Busqueda",
	bindings: {
    		num: "@"
 	 }
});


