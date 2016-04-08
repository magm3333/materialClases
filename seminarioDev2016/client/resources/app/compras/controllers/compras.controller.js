angular.module('Compras')
.controller('ComprasController', ['$rootScope', '$scope','$log','comprasService', 'uiGridConstants', ComprasController]);



function ComprasController($rootScope, $scope, $log, comprasService, uiGridConstants) {
	
	 $scope.title="Compras";
	 $scope.newProduct={description:"",tags:[]};
	 $scope.gridOptions = {};
	 $scope.gridOptions.data=[];
	 $scope.findPart="";
		 
	 $scope.reFill=function() {
		 if($scope.findPart.length>1) {
			 comprasService.getProductsFilter($scope.findPart).then(
					  function(resp){  $scope.gridOptions.data=resp.data;  },
					  function(respErr){  $log.log(respErr);	  }
			  );
		 }else{
			 comprasService.getProducts().then(
					  function(resp){  $scope.gridOptions.data=resp.data;  },
					  function(respErr){  $log.log(respErr);	  }
			  );
		 }
	 }
	 
	 $scope.save=function() {
		 if ($scope.newProduct.description.length>0) {
			 var t=[];
			 $scope.newProduct.tags.forEach(function(item) { 
				 t.push(item.text);
			 });
			 $scope.newProduct.tags=t;
			 comprasService.save($scope.newProduct).then(
					  function(resp){
						  $log.log(resp.data);
						  $scope.newProduct={description:"",tags:[]};;
						  $scope.gridOptions.data.push(resp.data);
					  },
					  function(respErr){  $log.log(respErr);	  }
			  );
		 }
	 };
	 
	 $scope.remove=function() {
		 if($scope.selectedItem) {
			 comprasService.remove($scope.selectedItem.entity.id).then(
					  function(resp){
						  $log.log(resp.data);
						  $scope.gridOptions.data.splice($scope.gridOptions.data.indexOf($scope.selectedItem.entity), 1);
						  $scope.selectedItem=null;
					  },
					  function(respErr){  $log.log(respErr);	  }
			  );
		 }
	 };
	 
	 $scope.reFill();
	 
	 
	 $scope.gridOptions.columnDefs = [
	        { name: 'id', enableCellEdit: false, width: '10%' },
	        { name: 'description', enableCellEdit: true, displayName: 'Description', width: '90%' }
	 ];
	 
	  $scope.gridOptions = {
			    enableRowSelection: true,
			    enableFullRowSelection: true,
			    enableSelectAll: false,
			    selectionRowHeaderWidth: 35,
			    multiSelect: false,
			    rowHeight: 35,
			    showGridFooter:true
			  };
	  
	  
	 $scope.selectedItem=null;
	 $scope.gridOptions.onRegisterApi = function(gridApi){
         $scope.gridApi = gridApi;
         gridApi.selection.on.rowSelectionChanged($scope,function(row){
        	if(row.isSelected) {
        		$scope.selectedItem=row;
        	} else {
        		$scope.selectedItem=null;
        	}
           
           });
         gridApi.edit.on.afterCellEdit($scope,function(rowEntity, colDef, newValue, oldValue){
        	 $log.log(rowEntity);
        	 if(newValue != oldValue ) {
	        	 comprasService.update(rowEntity).then(
						  function(resp){
							  $log.log(resp.data);
						  },
						  function(respErr){
							  $log.log(respErr);
						  }
				  );
        	 }
           $scope.$apply();
         });
       };
       
       $scope.tags=[];
       
       $scope.fetchTags=function() {
    	   comprasService.tags().then(
					  function(resp){
						  $scope.tags=resp.data;
					  },
					  function(respErr){
						  $log.log(respErr);
					  }
			  );
       }
       $scope.fetchTags();
       $scope.filterTags=function(query) {
    	   var out = [];
    	   $scope.tags.forEach(function(item) {
    		   if(item.toLowerCase().indexOf(query.toLowerCase()) !=-1 && $scope.newProduct.tags.indexOf(item)===-1 ) {
    			 out.push(item);  
    		   }
    	   });
    	   return out;
       };
}
