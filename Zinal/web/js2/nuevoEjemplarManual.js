angular.module("nuevoEjemplarManual",["barraNavegacion","piePagina","prestamos"])
.controller("Ejemplar2",function($scope,$http){
	$scope.isbn10="";
	$scope.isbn13="";
	$scope.codigo="";
	$scope.titulo="";
	$scope.errorISBN=false;
	$scope.errorISBN=false;
	$scope.errorTitulo=false;
	$scope.errorCodigo=false;
	$scope.errorCodigo2="";
	$scope.textoErrorISBN10="";
	$scope.textoErrorISBN13="";
	$scope.textoErrorCodigo="";
	$scope.autoresFinales=[];
	$scope.autor="";
	$scope.errorAutor="";
	
	$http({
  		method: 'GET',
  		url: 'http://localhost:8080/TFG3/webresources/generic/autores'
		}).then(function successCallback(response) {		
					$scope.autores=response.data;
					console.log($scope.autores.length);
					autores($scope.autores);
  				});
	$http({
		method: 'GET',
		url: 'http://localhost:8080/TFG3/webresources/generic/categorias'
			}).then(function successCallback(response) {
				$scope.categorias=response.data;
		});
	
	$scope.cancelar=function(){
		window.location='http://localhost:8080/TFG3/nuevoEjemplar.html';
	};

	$scope.erroresISBN10=function(){
		$scope.errorISBN10=false;
		$scope.textoErrorISBN10="";
		if($scope.isbn10.length===0){
			$scope.errorISBN10=true;
			$scope.textoErrorISBN10="El isbn10 no puede ser campos vacios";
			}
		else{
			if($scope.isbn10.length!==10){
				$scope.errorISBN10=true;
				$scope.textoErrorISBN10="El isbn tiene que tener 10 digitos";
			}
			else{
				if((isNaN($scope.isbn10))||($scope.isbn10%1!==0)){
					$scope.errorISBN10=true;
					$scope.textoError="El isbn10 tiene que tener solo numeros";
					
				}
		}
		}
		};

	$scope.erroresISBN13=function(){
		$scope.errorISBN13=false;
		$scope.textoErrorISBN13="";
		if($scope.isbn13.length===0){
			$scope.errorISBN13=true;
			$scope.textoErrorISBN13="El isbn13 no puede ser campos vacios";
			}
		else{
			if($scope.isbn13.length!==13){
				$scope.errorISBN13=true;
				$scope.textoErrorISBN13="El isbn tiene que tener 13 digitos";
			}
			else{
				if(isNaN($scope.isbn13)||$scope.isbn13%1!==0){
					$scope.errorISBN13=true;
					$scope.textoError="El isbn13 tiene que tener solo numeros";
				}
		}
		}
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


	$scope.addEjemplar=function(){
		$scope.errorTitulo=false;
		$scope.errorCodigo=false;

		$scope.erroresISBN10();	
		$scope.erroresISBN13();
		if($scope.titulo.length===0){
				$scope.errorTitulo=true;	
			}
		if($scope.codigo.length===0){
			$scope.errorCodigo=true;
		}
		if(!$scope.errorISBN10&&!$scope.errorISBN13&&!$scope.errorTitulo&&!$scope.errorCodigo){	
			
			var categorias= [];    
			$('input[type=checkbox]').each(function(){
			if (this.checked) {
				 categorias.push($(this).attr("name"));
				 
			 }
			 }); 
			
			
			var libro={
				"isbn10":$scope.isbn10,
				"isbn13":$scope.isbn13,
				"titulo":$scope.titulo,
				"codigo":$scope.codigo,
				"categorias":categorias,
				"autores":$scope.autoresFinales
			};
			

			
			$http({
  		 		method: 'POST',
  				url: 'http://localhost:8080/TFG3/webresources/generic/ejemplares/',
				data:libro
				}).then(function successCallback(response) {
					
					console.log(response);
					alert("Se ha añadido un ejemplar con el codigo "+$scope.codigo+" del libro con isbn "+$scope.isbn);
					$scope.isbn="";
					$scope.codigo="";
					$scope.parte=true;
					window.location="http://localhost:8080/TFG3/";
 	 		}, 
				function errorCallback(response) {
    	 				console.log(response);
					$scope.errorCodigo2=true;
					$scope.textoErrorCodigo="Ya hay un ejemplar con el codigo introducido";
  				});
			}
			};	
		});


