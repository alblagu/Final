angular.module("catalogo",[])
.controller("Catalogo",function($scope,$http){
	
	$http({
				method: 'GET',
				url: 'http://localhost:8080/TFG3/webresources/generic/libros'
			}).then(function successCallback(response) {
				$scope.libros=response.data;	
				$scope.librosAleatorios();
			});

	$scope.librosAleatorios=function(){
		if($scope.libros.length!==0){
			var aleatoria=getRandomInt(0,$scope.libros.length);
			$scope.libros2=[];		
			for(var i=0;i<3;i++){
				$scope.libros2[i]=$scope.libros[aleatoria];
				aleatoria++;
				if(aleatoria===$scope.libros.length){
					aleatoria=0;
				}
			}
	
	}
	};
	$scope.muestraEjemplares = function (indice) {
			window.location = "http://localhost:8080/TFG3/busquedaEjemplares.html?isbn=" + $scope.libros2[indice].isbn10;
		};

	
})
.component("catalogo",{
	templateUrl: "./componentes/catalogo/catalogo.html",
	controller: "Catalogo"
});
function getRandomInt(min, max) {
  return Math.floor(Math.random() * (max - min)) + min;
}



