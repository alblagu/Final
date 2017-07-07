angular.module("novedades",[])
.controller("NovedadesController",function($scope,$http){
	
	$http({
				method: 'GET',
				url: 'http://localhost:8080/Zinal/webresources/libros'
			}).then(function successCallback(response) {
				$scope.libros=response.data;	
				$scope.indice=$scope.libros.length-1;
				$scope.novedades();
			});

	$scope.novedades=function(){
		if($scope.libros.length!==0){
			$scope.libros2=[];		
			for(var i=0;i<3;i++){
				$scope.libros2[i]=$scope.libros[$scope.indice];
				$scope.indice--;
				if($scope.indice===-1){
					$scope.indice=$scope.libros.length-1;
					break;
				}
			}
	
	}
	};
	$scope.muestraEjemplares = function (indice) {
			window.location = "http://localhost:8080/Zinal/busquedaEjemplares.html?id=" + $scope.libros2[indice].id;
		};

	
})
.component("novedades",{
	templateUrl: "./componentes/novedades/novedades.html",
	controller: "NovedadesController"
});




