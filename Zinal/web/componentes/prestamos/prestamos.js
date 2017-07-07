angular.module("prestamos",[])
	.controller("prestamosController", function ($scope, $http ) {
		$scope.usuario=JSON.parse(sessionStorage.getItem('usuario13uva'));
		$scope.MAX_PRESTAMOS_PANTALLA=2;
	
		var usu = "";
		if($scope.usuario.tipoUsuario!=='ADMIN'){
			usu='/'+$scope.usuario.dni;
		}
	
		$scope.prestamos = [];
		$http ({
			method: 'GET',
			url: 'http://localhost:8080/Zinal/webresources/ejemplares/prestamosUsu'+usu
		}).then(function successCallback(response) {
			$scope.prestamos = response.data;
			$scope.prestamos2=[];
			$scope.contador=0;
			$scope.siguientes();
			console.log($scope.prestamos);
		},
			function errorCallback(response) {
			});

	
	
	
	
	$scope.siguientes = function (){
			if($scope.prestamos.length/$scope.MAX_PRESTAMOS_PANTALLA>$scope.contador){
				$scope.contador++;
				if($scope.prestamos.length/$scope.MAX_PRESTAMOS_PANTALLA<$scope.contador){
					$scope.contador--;
					$scope.prestamos2=[];
					for(i=0;i<$scope.prestamos.length-$scope.contador*$scope.MAX_PRESTAMOS_PANTALLA;i++){
						$scope.prestamos2[i]=$scope.prestamos[$scope.contador*$scope.MAX_PRESTAMOS_PANTALLA+i];	
					}
					
				}
				else{
					$scope.contador--;
					for(i=0;i<$scope.MAX_PRESTAMOS_PANTALLA;i++){
						$scope.prestamos2[i]=$scope.prestamos[$scope.contador*$scope.MAX_PRESTAMOS_PANTALLA+i];	
					}
				}
				$scope.contador++;
			}
			};
			
			
				
		$scope.anteriores=function(){
			if(1!==$scope.contador){
				$scope.contador--;
				$scope.contador--;
				for(i=0;i<$scope.MAX_PRESTAMOS_PANTALLA;i++){
					$scope.prestamos2[i]=$scope.prestamos[$scope.contador*$scope.MAX_PRESTAMOS_PANTALLA+i];	
				}
				$scope.contador++;
			}
			
		};
		
		$scope.resultado=function(){
			return ($scope.contador*$scope.MAX_PRESTAMOS_PANTALLA>$scope.prestamos.length)? $scope.prestamos.length:$scope.contador*$scope.MAX_PRESTAMOS_PANTALLA;
		};
})
	.component("prestamos", {
		templateUrl: "./componentes/prestamos/prestamos.html",
		controller: "prestamosController"
	});

