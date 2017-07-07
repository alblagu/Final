angular.module("app")
.controller("BusquedaEjemplaresController",function($scope,$http){
	$scope.ejemplares=[];
	$scope.id=getParameterByName("id",window.location);
		$http({
			method: 'GET',
			url: 'http://localhost:8080/Zinal/webresources/ejemplares/libro/'+$scope.id
		}).then(function successCallback(response) {
			$scope.ejemplares=response.data;
  		});
		
		$http({
			method: 'GET',
			url: 'http://localhost:8080/Zinal/webresources/libros/libro/'+$scope.id
		}).then(function successCallback(response) {
			$scope.libro=response.data;
  		});
		
		
		$scope.getUsuario=function(indice){
			$scope.ejemplar=$scope.ejemplares[indice];
			console.log($scope.ejemplar);
			if(!$scope.ejemplar.disponible){
				$http({
			method: 'GET',
			url: 'http://localhost:8080/Zinal/webresources/usuarios/ejemplar/'+$scope.ejemplar.codigo
		}).then(function successCallback(response) {
			$scope.usuario=response.data;
  		});
	}
		};
});

	function getParameterByName(name, url) {
    		if (!url) {
      			url = window.location.href;
    		}
    		name = name.replace(/[\[\]]/g, "\\$&");
    		var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        	results = regex.exec(url);
    		if (!results) return null;
    		if (!results[2]) return '';
    		return decodeURIComponent(results[2].replace(/\+/g, " "));
}

