angular.module("app")
.controller("Ejemplar2",function($scope,$http){
	$scope.isbn="";
	$scope.codigo="";
	$scope.localizacion="";
	$scope.error=false;
	$scope.mostrarManual=false;
	$scope.textoError="";
	
	$http({
		method: 'GET',
		url: 'http://localhost:8080/Zinal/webresources/libros/categorias'
			}).then(function successCallback(response) {
				$scope.categorias=response.data;
		});
	
	$scope.cancelar=function(){
		$scope.textoError="";
		$scope.avanzar=false;
	};


	$scope.generarManual=function(){
		window.location='http://localhost:8080/Zinal/nuevoEjemplarManual.html';
	};
	
	$scope.errores=function(){
		$scope.error=false;
		$scope.textoError="";
		if($scope.isbn.length===0){
			$scope.error=true;
			$scope.textoError="El isbn o el codigo no pueden ser campos vacios";
			}
		else{
			if($scope.isbn.length!==10&&$scope.isbn.length!==13){
				$scope.error=true;
				$scope.textoError="El isbn tiene que tener 10 o 13 digitos";
			}
			else{
				if(isNaN($scope.isbn)||$scope.isbn%1!==0||$scope.isbn[$scope.isbn.length-1]==='.'){
					$scope.error=true;
					$scope.textoError="El isbn tiene que tener unicamente numeros";
				}
		}
		}
		};

	
	$scope.getLibro=function(){
		$scope.errores();	
		if(!$scope.error){	
			$http({
  			method: 'GET',
  			url: 'http://localhost:8080/Zinal/webresources/libros/'+$scope.isbn
				}).then(function successCallback(response) {
				$scope.error=false;
				$scope.libro=response.data;
				$scope.avanzar=true;
				
 	 		}, 
	 			function errorCallback(response) {
					$scope.error=true;
					$scope.mostrarManual=true;
					$scope.textoError="No se ha encontrado ningun libro con ese ISBN";
  				});
			}
			};

	$scope.addEjemplar=function(){
		$scope.error=false;
		$scope.textoError="";
		console.log($scope.codigo.length===0);
		console.log($scope.localizacion.length===0);
		if($scope.codigo.length===0||$scope.localizacion.length===0){
			$scope.error=true;
			$scope.textoError="Los campos no pueden ser vacíos";
		}
		else{
			var categorias= [];    
			$('input[type=checkbox]').each(function(){
			if (this.checked) {
				 categorias.push($(this).attr("name"));
				 
			 }
			 }); 
			
			$scope.libro.categorias=categorias;
			$scope.libro.localizacion=$scope.localizacion;
			console.log($scope.libro);
			
			$http({
				headers: { 
        				'Accept': 'application/json',
        				'Content-Type': 'application/json' 
    },
  		 		method: 'POST',
  				url: 'http://localhost:8080/Zinal/webresources/ejemplares/'+$scope.codigo,
				data:JSON.stringify($scope.libro)
				}).then(function successCallback(response) {
					
					console.log(response);
					alert("Se ha añadido un ejemplar con el codigo "+$scope.codigo+" del libro con isbn "+$scope.isbn);
					$scope.isbn="";
					$scope.codigo="";
					$scope.avanzar=true;
					location.reload();	
 	 		}, 
	 			function errorCallback(response) {
    	 				console.log(response);
					$scope.error=true;
					$scope.textoError="Ya hay un ejemplar con el codigo introducido";
  				});
			}
			};	
	});
