angular.module("app")
	.controller("UsuarioPerfilController", function ($scope, $http) {
	
	$scope.dni= getParameterByName("dni", window.location);
	$scope.usuario;
	
	$scope.nombre="";
	$scope.dni2="";
	$scope.telefono="";
	$scope.textoError="";
		

	$scope.contrasenaAntigua="";
	$scope.contrasenaNueva="";
	$scope.contrasenaNueva2="";
	$scope.textoError1="";
	$scope.textoError2="";
	$scope.textoError3="";
	

	$http({
		method: 'GET',
		url: 'http://localhost:8080/Zinal/webresources/usuarios/usuario/' + $scope.dni
		}).then(function successCallback(response) {
			$scope.usuario=response.data;
			},
			function errorCallback(response) {
					console.log(response);
				});

	$scope.cambiaNombre=function(){
		$scope.textoError="";
		if($scope.nombre.length===0){
			$scope.textoError="El campo no puede ser vacio";
		}	
		else{
			if($scope.nombre===$scope.usuario.nombre){
				$scope.textoError="El campo es igual al que se quiere cambiar"
			}
			else{
							$http({
				method: 'PUT',
				url: 'http://localhost:8080/Zinal/webresources/usuarios/usuarios/nombre/' +$scope.usuario.dni+'/' + $scope.nombre
		}).then(function successCallback(response) {
			alert("Nuevo nombre: "+$scope.nombre);
			location.reload();
			});
			}
		}
	};

	$scope.cambiaDNI=function(){
		$scope.textoError="";
		if($scope.dni2.length===0){
			$scope.textoError="El campo no puede ser vacio";
		}	
		else{
			if($scope.dni2===$scope.usuario.dni){
				$scope.textoError="El campo es igual al que se quiere cambiar"
			}
			else{
				$http({
				method: 'PUT',
				url: 'http://localhost:8080/Zinal/webresources/usuarios/usuarios/dni/' + $scope.usuario.dni+'/' +$scope.dni2
		}).then(function successCallback(response) {
			alert("Nuevo DNI: "+$scope.dni2);
			window.location="http://localhost:8080/Zinal/usuarioPerfil.html?dni="+$scope.dni2;
			});
			}
		}
	};
	
	$scope.cambiaTelefono=function(){
		$scope.textoError="";
		if($scope.telefono.length!==9){
			$scope.textoError="El campo debe tener 9 numeros";
		}	
		else{
			if(isNaN(($scope.telefono))||($scope.telefono%1!==0) || ($scope.telefono[8]==='.')){
				$scope.textoError="El campo debe tener 9 numeros ";
			}
			else{
				if($scope.telefono===$scope.usuario.telefono){
				$scope.textoError="El campo es igual al que se quiere cambiar"
			}
			else{
							$http({
				method: 'PUT',
				url: 'http://localhost:8080/Zinal/webresources/usuarios/usuarios/telefono/'+$scope.usuario.dni+'/' + $scope.telefono
		}).then(function successCallback(response) {
			alert("Nuevo telefono: "+$scope.telefono);
			location.reload();
			
			});
			}
		}
	}
	};
	
	$scope.errorVacio=function(){
		$scope.textoError="";
	};
	
	

	$scope.nuevaContrasena=function(){
		$scope.textoError1="";
		$scope.textoError2="";
		$scope.textoError3="";	

		if($scope.contrasenaAntigua.length===0){
			$scope.textoError1="El campo no puede ser vacio";
		}
		
		if($scope.contrasenaNueva.length<8){
			$scope.textoError2="La contraseña debe tener al menos 8 caracteres";
		}
		if($scope.contrasenaNueva2.length<8){
			$scope.textoError3="La contraseña debe tener al menos 8 caracteres";
		}
		
		if($scope.textoError1.length===0&&$scope.textoError2.length===0&&$scope.textoError3.length===0){
			if($scope.contrasenaNueva!==$scope.contrasenaNueva2){
				$scope.textoError2="las contraseñas tienen que ser iguales";
				$scope.textoError3="las contraseñas tienen que ser iguales";	
			}
			else{
			$http({
				method: 'PUT',
				url: 'http://localhost:8080/Zinal/webresources/usuarios/usuarios/password/'+$scope.usuario.dni+'/' + $scope.contrasenaAntigua +'/'+$scope.contrasenaNueva
		}).then(function successCallback(response) {
			alert("Contraseña cambiada correctamente");
			location.reload();
		},
		function errorCallback(response) {
					$scope.textoError1="La contraseña no corresponde con la que tiene actualmente";
			});
		}
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
	if (!results)
		return null;
	if (!results[2])
		return '';
	return decodeURIComponent(results[2].replace(/\+/g, " "));
};


