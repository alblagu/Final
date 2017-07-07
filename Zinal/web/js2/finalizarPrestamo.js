angular.module("app")
	.controller("FinalizarPrestamoController", function ($scope, $http) {
		$scope.dni = "";
		$scope.codigo = "";
		$scope.textoError = "";
		$scope.avanzar=false;
		

	
		$scope.cancelar=function(){
			$scope.avanzar=false;
		};
		$scope.obtenerPrestamo = function () {
			$scope.textoError="";
			if ($scope.dni.length === 0 || $scope.codigo.length === 0){
				$scope.textoError = "Los campos no pueden estar vacios";
			}
			else {
				$http({
					method: 'GET',
					url: 'http://localhost:8080/Zinal/webresources/ejemplares/prestamo/' + $scope.codigo+'/'+$scope.dni
				}).then(function successCallback(response) {
					$scope.prestamo=response.data;
					$scope.avanzar=true;
					},
						function errorCallback(response) {
						$scope.textoError = "No hay ningun prestamo en activo con esos datos";
						});
					}		
			};
		
		

	

		
		$scope.finalizar = function () {
			$http({
					method: 'POST',
					url: 'http://localhost:8080/Zinal/webresources/ejemplares/prestamos/' + $scope.codigo
				}).then(function successCallback(response) {
					alert("prestamo finalizado");
					$scope.dni = "";
					$scope.codigo = "";
				$scope.textoError = "";
					$scope.avanzar=false;
					});
		};

	});
