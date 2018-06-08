angular.module('Compras')
.constant('URL_API','../api/v1/product')
.factory('comprasService',['$http','URL_API',function($http, URL_API) {
   return {
	   getProducts: function() {
		   return $http.get(URL_API+"/list");
	   },
	   getProductsFilter: function(part) {
		   return $http.get(URL_API+"/list/filter?q="+part);
	   },
	   save: function(product) {
		   return $http.post(URL_API+"/",product);
	   },
	   update: function(product) {
		   return $http.put(URL_API+"/",product);
	   },
	   remove: function(id) {
		   return $http.delete(URL_API+"/"+id);
	   },
	   tags: function() {
		   return $http.get(URL_API+"/tags");
	   }
   }
}]);
