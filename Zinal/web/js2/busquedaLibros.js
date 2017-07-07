angular.module("app")
	.controller("BusquedaLibrosController", function ($scope, $http) {
		$scope.MAX_LIBROS_PANTALLA=12;
		$scope.contador=0;
		$scope.libros = [];
		$scope.libros2  = [];
		$scope.busqueda = getParameterByName("busqueda", window.location);
		$scope.vista=true;
		
		$scope.cambiaVista=function(){
			$scope.vista=!$scope.vista;
		};

		console.log($scope.busqueda);
			
			$http({
					headers: {
						'Accept': 'application/json',
						'Content-Type': 'application/json'
					},	
				method: 'GET',
				url: 'http://localhost:8080/Zinal/webresources/libros/busqueda/' + $scope.busqueda
			}).then(function successCallback(response) {
				
				$scope.libros = response.data;
				console.log($scope.libros);
				$scope.libros2=[];
				$scope.contador=0;
				$scope.siguientes();
				
			});
				

			

		
		
		

		$scope.muestraEjemplares = function (indice) {
			window.location = "http://localhost:8080/Zinal/busquedaEjemplares.html?id=" + $scope.libros2[indice].id;
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

