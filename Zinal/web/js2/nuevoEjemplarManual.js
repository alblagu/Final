angular.module("app")
.controller("Ejemplar2",function($scope,$http){
	$scope.isbn10="";
	$scope.isbn13="";
	$scope.codigo="";
	$scope.titulo="";
	$scope.localizacion="";
	$scope.errorISBN10=false;
	$scope.errorISBN13=false;
	$scope.errorTitulo=false;
	$scope.errorCodigo=false;
	$scope.errorLocalizacion=false;
	$scope.errorCodigo2="";
	$scope.textoErrorISBN10="";
	$scope.textoErrorISBN13="";
	$scope.textoErrorCodigo="";
	$scope.autoresFinales=[];
	$scope.autor="";
	$scope.errorAutor="";
	
	$http({
  		method: 'GET',
  		url: 'http://localhost:8080/Zinal/webresources/libros/autores'
		}).then(function successCallback(response) {		
					$scope.autores=response.data;
					autores($scope.autores);
  				});
	$http({
		method: 'GET',
		url: 'http://localhost:8080/Zinal/webresources/libros/categorias'
			}).then(function successCallback(response) {
				$scope.categorias=response.data;
		});
	
	$scope.cancelar=function(){
		window.location='http://localhost:8080/Zinal/nuevoEjemplar.html';
	};

	
		
	$scope.anadeAutor=function(){
		$scope.errorAutor="";
		if($scope.autor.length===0){
			$scope.errorAutor="El campo no puede ser vacio";
		}else{
			var error=false;
			for(var i=0;i<$scope.autoresFinales.length;i++){
				if($scope.autor===$scope.autoresFinales[i]){
					error=true;
					break;
				}
			}
			if(error){
				$scope.errorAutor="Ese autor ya esta añadido";
			}
			else{
				
				var a=$scope.autor;
		$scope.autoresFinales.push(a);
		$scope.autor="";
			}
		}	};

	$scope.eliminaAutor=function(index){
			
		$scope.autoresFinales.splice(index,1);
	
	};

	function erroresISBN(){
		$scope.errorISBN10=false;
		$scope.errorISBN13=false;
		$scope.textoErrorISBN10="";
		$scope.textoErrorISBN13="";
		
		if($scope.isbn10.length===0&&$scope.isbn13.length===0){
			$scope.errorISBN13=true;
			$scope.textoErrorISBN13="Al menos uno de los dos isbn no puede ser vacío";
		}
		else {
			if($scope.isbn10.length!==0){
		
			
			if($scope.isbn10.length!==10){
				$scope.errorISBN10=true;
				$scope.textoErrorISBN10="El isbn tiene que tener 10 numeros ";
			}
			else{
				if(isNaN($scope.isbn10)||$scope.isbn10%1!==0||$scope.isbn10[$scope.isbn10.length-1]==='.'){
					$scope.errorISBN10=true;
					$scope.textoErrorISBN10="El isbn tiene que tener unicamente numeros";
				}
		}
		}
		
		if($scope.isbn13.length!==0){
		
			
			if($scope.isbn13.length!==13){
				$scope.errorISBN13=true;
				$scope.textoErrorISBN13="El isbn tiene que tener 13 numeros";
			}
			else{
				if(isNaN($scope.isbn13)||$scope.isbn13%1!==0||$scope.isbn13[$scope.isbn13.length-1]==='.'){
					$scope.errorISBN13=true;
					$scope.textoErrorISBN13="El isbn tiene que tener unicamente numeros";
				}
		}
		}
		}
	};



	$scope.addEjemplar=function(){
		$scope.errorTitulo=false;
		$scope.errorCodigo=false;
		$scope.errorLocalizacion=false;

		erroresISBN();	
		
		if($scope.titulo.length===0){
				$scope.errorTitulo=true;	
			}
		if($scope.codigo.length===0){
			$scope.errorCodigo=true;
		}
		if($scope.localizacion.length===0){
			$scope.errorLocalizacion=true;
		}
		
		if(!$scope.errorISBN10&&!$scope.errorISBN13&&!$scope.errorTitulo&&!$scope.errorCodigo&&!$scope.errorLocalizacion){	
			
			var categorias= [];    
			$('input[type=checkbox]').each(function(){
			if (this.checked) {
				 categorias.push($(this).attr("name"));
				 
			 }
			 }); 
			
			
			var libro={
				isbn10:$scope.isbn10,
				isbn13:$scope.isbn13,
				titulo:$scope.titulo,
				codigo:$scope.codigo,
				localizacion:$scope.localizacion,
				urlfoto:"imagenes/not_found.jpg",
				categorias:categorias,
				autores:$scope.autoresFinales
			};
			

			
			$http({
				headers: { 
        				'Accept': 'application/json',
        				'Content-Type': 'application/json' 
    },
  		 		method: 'POST',
  				url: 'http://localhost:8080/Zinal/webresources/ejemplares',
				data:libro
				}).then(function successCallback(response) {
					
					console.log(response);
					alert("Se ha añadido un ejemplar con el codigo "+$scope.codigo+" del libro con isbn "+$scope.isbn);
					$scope.isbn="";
					$scope.codigo="";
					$scope.parte=true;
					window.location="http://localhost:8080/Zinal/nuevoEjemplar.html";
 	 		}, 
				function errorCallback(response) {
    	 				console.log(response);
					$scope.errorCodigo2=true;
					$scope.textoErrorCodigo="Ya hay un ejemplar con el codigo introducido";
  				});
			}
			};	
		});


