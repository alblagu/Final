angular.module("index",[])	
	.controller("IdentificarseController", function ($scope,  $http) {
		$scope.dni = "";
		$scope.password = "";
		$scope.textoError = "";

		
		


		$scope.identificarse = function () {
			if ($scope.dni.length === 0 || $scope.password.length === 0)
				$scope.textoError = "No puede haber campos vacios";
			else {
				$http({
					method: 'GET',
					url: 'http://localhost:8080/Zinal/webresources/usuarios/usuario/' + $scope.dni+'/'+$scope.password+'/'+true
				}).then(function successCallback(response) {
					$scope.usuario=response.data;
					localStorage.setItem('usuario13uva','');
					sessionStorage.setItem('usuario13uva',(JSON.stringify($scope.usuario || {})));
						$("#password").each(function(){
					if (this.checked) {
					localStorage.setItem('usuario13uva', (JSON.stringify($scope.usuario || {})));
				}
				});
						
					
				
					window.location='http://localhost:8080/Zinal/inicio.html';
					
				},
					function errorCallback(response) {
						$scope.textoError = "Los datos introducidos no son correctos";
					});
			}
		};
		
		
		if(localStorage.getItem('usuario13uva')!==null&&localStorage.getItem('usuario13uva')!=="null13uva"&localStorage.getItem('usuario13uva')!==""){
			var usuario=JSON.parse(localStorage.getItem('usuario13uva'));
			$http({
					method: 'GET',
					url: 'http://localhost:8080/Zinal/webresources/usuarios/usuario/' + usuario.dni+'/'+usuario.password+'/'+false
				}).then(function successCallback(response) {
					$scope.usuario=response.data;
					
						sessionStorage.setItem('usuario13uva',(JSON.stringify($scope.usuario || {})));
						window.location='http://localhost:8080/Zinal/inicio.html';
					});
				
					
		}

	});

