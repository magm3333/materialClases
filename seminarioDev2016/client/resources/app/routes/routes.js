
angular.module('Magm')
.config(function($routeProvider,$locationProvider) {
	
	console.log("Iniciando configuración de rutas.");
	$locationProvider.hashPrefix('!');
	
    $routeProvider
      .when('/', {
        templateUrl: 'app/views/main.html',
        controller: 'MainController'
      })
      .when('/compras', {
        templateUrl: 'app/compras/views/compras.html',
        controller: 'ComprasController'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
