angular.module('Magm',['ngRoute','ngSanitize','Compras'])
.run(['$rootScope','$location', 
    function($rootScope,$location) {
	    $rootScope.toDate = function(d) {return new Date(d)};
	    $rootScope.relocate=function(loc) {
	  	  $location.path(loc);
    }  
}]);


angular.element(document).ready(function() {
	console.log("Iniciando app.");
	angular.bootstrap(document,['Magm']);
})
