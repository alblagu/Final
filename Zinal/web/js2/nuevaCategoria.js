angular.module("nuevaCategoria",["barraNavegacion","busqueda","piePagina","prestamos","catalogo"])
.controller("CategoriaController",function($scope,$http){
		$scope.nuevaCategoria="";
		$scope.textoError="";
		$scope.nuevaCategoria2="";
	
		$http({
					method: 'GET',
					url: 'http://localhost:8080/TFG3/webresources/generic/categorias'
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
					url: 'http://localhost:8080/TFG3/webresources/generic/categorias/'+$scope.nuevaCategoria
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
					url: 'http://localhost:8080/TFG3/webresources/generic/categorias/'+$scope.categorias[indice].nombre
				}).then(function successCallback(response) {
					alert($scope.categorias[indice].nombre+": fue eliminada correctamente");
					location.reload();
					});
		};
		
			$scope.modificarCategoria=function(){
				$scope.textoError="";
				if($scope.nuevaCategoria2.length===0){
					$scope.textoError="El campo no puede ser vacío";
				}
				else{
			$http({
					method: 'PUT',
					url: 'http://localhost:8080/TFG3/webresources/generic/categorias/'+$scope.categorias[$scope.indice].nombre+'/'+$scope.nuevaCategoria2
				}).then(function successCallback(response) {
					console.log("ola");
					alert($scope.categorias[$scope.indice].nombre+": fue modificada correctamente");
					location.reload();
				},function errorCallback(response) {
						$scope.textoError="Esa categoria ya existe";
					});
		}
	};
		
		
		$scope.asignarIndice=function(index){
			$scope.indice=index;
		};
		$scope.getCategoriaModificable=function(){
			return $scope.categorias[$scope.indice].nombre;
		};
	});

