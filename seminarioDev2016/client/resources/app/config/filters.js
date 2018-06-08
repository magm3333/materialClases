
angular.module('Magm')
 .filter("sanitize", ['$sce', function($sce) {
	  return function(htmlCode){
		    return $sce.trustAsHtml(htmlCode);
	  }
  }]);
