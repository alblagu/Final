angular.module("busquedaLibros",["barraNavegacion","piePagina","prestamos","busqueda","catalogo"])
	.controller("Busqueda1", function ($scope, $http) {
		$scope.MAX_LIBROS_PANTALLA=2;
		$scope.contador=0;
		$scope.libros = [];
		$scope.libros2  = [];
		$scope.busqueda = getParameterByName("busqueda", window.location);

		if (!isNaN($scope.busqueda)) {
			//Busqueda por ISBN
			$http({
				method: 'GET',
				url: 'http://localhost:8080/TFG3/webresources/generic/libros/busqueda/' + $scope.busqueda
			}).then(function successCallback(response) {
				$scope.libros = response.data;
				$scope.libros2=[];
				$scope.contador=0;
				$scope.siguientes();
				
			},
				function errorCallback(response) {
					console.log(response);
				});
		} else { //BUSQUEDA por titulo
			$http({
				method: 'GET',
				url: 'http://localhost:8080/TFG3/webresources/generic/libros/busqueda2/' + $scope.busqueda
			}).then(function successCallback(response) {
				
				$scope.libros = response.data;
				$scope.libros2=[];
				$scope.contador=0;
				$scope.siguientes();
				
			},
				function errorCallback(response) {
					console.log(response);
				});

		}
		
		

		$scope.muestraEjemplares = function (indice) {
			window.location = "http://localhost:8080/TFG3/busquedaEjemplares.html?isbn=" + $scope.libros2[indice].isbn10;
		};

		$scope.siguientes = function (){
			if($scope.libros.length/$scope.MAX_LIBROS_PANTALLA>$scope.contador){
				$scope.contador++;
				if($scope.libros.length/$scope.MAX_LIBROS_PANTALLA<$scope.contador){
					$scope.contador--;
					$scope.libros2=[];
					for(i=0;i<$scope.libros.length-$scope.contador*$scope.MAX_LIBROS_PANTALLA;i++){
						$scope.libros2[i]=$scope.libros[$scope.contador*$scope.MAX_LIBROS_PANTALLA+i];	
					}
					
				}
				else{
					$scope.contador--;
					for(i=0;i<$scope.MAX_LIBROS_PANTALLA;i++){
						$scope.libros2[i]=$scope.libros[$scope.contador*$scope.MAX_LIBROS_PANTALLA+i];	
					}
				}
				$scope.contador++;
			}
			};
			
			
				
		$scope.anteriores=function(){
			if(1!==$scope.contador){
				$scope.contador--;
				$scope.contador--;
				for(i=0;i<$scope.MAX_LIBROS_PANTALLA;i++){
					$scope.libros2[i]=$scope.libros[$scope.contador*$scope.MAX_LIBROS_PANTALLA+i];	
				}
				$scope.contador++;
			}
			
		};
		
		$scope.resultado=function(){
			return ($scope.contador*$scope.MAX_LIBROS_PANTALLA>$scope.libros.length)? $scope.libros.length:$scope.contador*$scope.MAX_LIBROS_PANTALLA;
		};
	});

function getParameterByName(name, url) {
	if (!url) {
		url = window.location.href;
	}
	name = name.replace(/[\[\]]/g, "\\$&");
	var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
		results = regex.exec(url);
	if (!results)
		return null;
	if (!results[2])
		return '';
	return decodeURIComponent(results[2].replace(/\+/g, " "));
}

