angular.module("app")
.controller("CategoriaController",function($scope,$http){
		$scope.nuevaCategoria="";
		$scope.textoError="";
		$scope.textoError2="";
		$scope.nuevaCategoria2="";
	
		$http({
					method: 'GET',
					url: 'http://localhost:8080/Zinal/webresources/libros/categorias'
				}).then(function successCallback(response) {
					$scope.categorias=response.data;
					
					});
					
		$scope.crearCategoria=function(){
			if($scope.nuevaCategoria.length===0){
				$scope.textoError="campo categoria no puede ser vacio";
			}
			else{
			$http({
					method: 'POST',
					url: 'http://localhost:8080/Zinal/webresources/libros/categorias/'+$scope.nuevaCategoria
				}).then(function successCallback(response) {
					alert("categoria: "+$scope.nuevaCategoria+" fue añadida");
					location.reload();
				},
				function errorCallback(response) {
						$scope.textoError="Esa categoria ya existe";
					});
				}
		};
		
		$scope.deleteCategoria=function(indice){
			$http({
					method: 'DELETE',
					url: 'http://localhost:8080/Zinal/webresources/libros/categorias/'+$scope.categorias[indice]
				}).then(function successCallback(response) {
					alert($scope.categorias[indice]+": fue eliminada correctamente");
					location.reload();
					});
		};
		
			$scope.modificarCategoria=function(){
				$scope.textoError="";
				if($scope.nuevaCategoria2.length===0){
					$scope.textoError2="El campo no puede ser vacío";
				}
				else{
			$http({
					method: 'PUT',
					url: 'http://localhost:8080/Zinal/webresources/libros/categorias/'+$scope.categorias[$scope.indice]+'/'+$scope.nuevaCategoria2
				}).then(function successCallback(response) {
					alert($scope.categorias[$scope.indice]+": fue modificada correctamente");
					location.reload();
				},function errorCallback(response) {
						$scope.textoError2="Esa categoria ya existe";
					});
		}
	};
		
		
		$scope.asignarIndice=function(index){
			$scope.indice=index;
		};
		$scope.getCategoriaModificable=function(){
			return $scope.categorias[$scope.indice];
		};
		
		$scope.vaciarError=function(){
			$scope.textoError2="";
		};
	});

