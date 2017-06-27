angular.module("busquedaEjemplares",["barraNavegacion","piePagina","prestamos","catalogo","busqueda"])
.controller("Busqueda1",function($scope,$http){
	$scope.ejemplares=[];
	$scope.isbn=getParameterByName("isbn",window.location);
		$http({
			method: 'GET',
			url: 'http://localhost:8080/TFG3/webresources/generic/ejemplares/'+$scope.isbn
		}).then(function successCallback(response) {
			$scope.ejemplares=response.data;
  		});
		
		$http({
			method: 'GET',
			url: 'http://localhost:8080/TFG3/webresources/generic/libro2/'+$scope.isbn
		}).then(function successCallback(response) {
			$scope.libro=response.data;
  		});
});

	function getParameterByName(name, url) {
    		if (!url) {
      			url = window.location.href;
    		}
    		name = name.replace(/[\[\]]/g, "\\$&");
    		var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        	results = regex.exec(url);
    		if (!results) return null;
    		if (!results[2]) return '';
    		return decodeURIComponent(results[2].replace(/\+/g, " "));
}

