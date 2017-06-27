angular.module("barraNavegacion",[])
.controller("BarraNavegacion",function($scope,$http){
	

	$scope.logout=function(){
		localStorage.setItem('usuario13uva', "null13uva");
		sessionStorage.setItem('usuario13uva',"null13uva");
		console.log(localStorage.getItem('usuario13uva'));
		window.location='http://localhost:8080/TFG3/index.html';
	};
	
	
		function ruta(){
			if(!$scope.usuario.administrador){
			var url = window.location.href;
			if( url.match(/nuevoEjemplar.html/g)||url.match(/nuevaCategoria.html/g)||url.match(/usuarios.html/g)||url.match(/nuevoUsuario.html/g)||url.match(/nuevoEjemplar.html/g)||url.match(/nuevoEjemplarManual.html/g)||url.match(/nuevoPrestamo.html/g)||url.match(/finalizarPrestamo.html/g)){
				window.location=("http://localhost:8080/TFG3/inicio.html");
			}
		}
		};
	
	if(sessionStorage.getItem('usuario13uva')!==null&&localStorage.getItem('usuario13uva')!=="null13uva"){
			var usuario=JSON.parse(sessionStorage.getItem('usuario13uva'));
			$http({
					method: 'GET',
					url: 'http://localhost:8080/TFG3/webresources/generic/usuario/' + usuario.dni+'/'+usuario.password+'/'+false
				}).then(function successCallback(response) {
					$scope.usuario=response.data;	
					ruta();
				},
				function errorCallback(response) {
					localStorage.setItem('usuario13uva',"null13uva");
					sessionStorage.setItem('usuario13uva',"null13uva");
					window.location='http://localhost:8080/TFG3/';
					});
				
					
		}
		else{
			window.location='http://localhost:8080/TFG3/';
		}
	
	
		
		
})
.component("barraNavegacion",{
	templateUrl: "./componentes/barra-navegacion/barraNavegacion.html",
	controller: "BarraNavegacion",
	bindings: {
    		num: "@"
 	 }
});

