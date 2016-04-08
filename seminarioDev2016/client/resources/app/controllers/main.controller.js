angular.module('Magm')
.controller('MainController', ['$rootScope', '$scope', MainController]);

function MainController($rootScope, $scope) {
	$scope.title="Profit Foster Demo";
}
