angular.module("app")
	.controller("Usuario1", function ($scope, $http) {
		$scope.nombre = "";
		$scope.apellidos = "";
		$scope.dni = "";
		$scope.password = "";
		$scope.telefono = "";
		$scope.email="";
		$scope.errorDNIrepetido = false;
		$scope.errorNombre = false;
		$scope.errorApellidos = false;
		$scope.errorDNI = false;
		$scope.errorPassword = false;
		$scope.errorTelefono = false;
		$scope.errorEmail = false;
		$scope.tipoUsuario=true;

		$scope.textoErrorDNIRepetido = "";

		$scope.errores = function () {
			$scope.errorDNIrepetido = false;
			$scope.errorNombre = false;
			$scope.errorApellidos = false;
			$scope.errorDNI = false;
			$scope.errorPassword = false;
			$scope.errorTelefono = false;
			$scope.errorEmail = false;
			
			if ($scope.nombre.length === 0) {
				$scope.errorNombre = true;
			}
			if ($scope.apellidos.length === 0) {
				$scope.errorApellidos = true;
			}
			if ($scope.dni.length === 0) {
				$scope.errorDNI = true;
			}
			if ($scope.password.length < 8) {
				$scope.errorPassword = true;
			}
			if ($scope.telefono.length !== 9 || isNaN($scope.telefono)||$scope.telefono%1!==0) {
				$scope.errorTelefono=true;
			}
			if($scope.email.length===0){
				$scope.errorEmail=true;
			}

			return ($scope.errorNombre||$scope.errorApellidos||$scope.errorDNI||$scope.errorPassword||$scope.errorTelefono||$scope.errorEmail)?false:true;
		};

		$scope.nuevoUsuario = function () {

			if ($scope.errores()) {

				console.log($scope.tipoUsuario);
				var usuario = {
					dni: $scope.dni,
					password: $scope.password,
					nombre: $scope.nombre,
					apellidos: $scope.apellidos,
					telefono: $scope.telefono,
					email : $scope.email,
					tipoUsuario: $scope.tipoUsuario
				};
				$http({
					headers: {
						'Accept': 'application/json',
						'Content-Type': 'application/json'
					},
					method: 'POST',
					url: 'http://localhost:8080/Zinal/webresources/usuarios',
					data: JSON.stringify(usuario)
				}).then(function successCallback(response) {
					alert("Usuario Añadido con contraseña: "+$scope.password);
					location.reload();
				},
					function errorCallback(response) {
						$scope.errorDNIRepetido = true;
						$scope.textoErrorDNIRepetido = "Ya hay un usuario con ese dni";
					});
			}
		};
		
		$scope.cambiaTipoUsuario=function(){
			$scope.tipoUsuario=!$scope.tipoUsuario;
		};
	});


