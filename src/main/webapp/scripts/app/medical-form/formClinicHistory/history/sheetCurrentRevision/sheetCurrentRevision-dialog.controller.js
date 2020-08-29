(function(angular){
	'use strict';

angular.module('fimepedApp').controller('SheetCurrentRevisionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'currentRevision', 'CurrentRevisionService','Catalog', 
        function($scope, $stateParams, $modalInstance, currentRevision, CurrentRevisionService,Catalog) {

        $scope.currentRevision = currentRevision;       
        /*$scope.load = function(id) {
        	CurrentRevisionService.get({episode_id:$stateParams.episodeId, id : id}, function(result) {
                $scope.currentRevision = result;
            });
        };*/
        
        
        $scope.loadCatalogWeNe= function(){
        	
        	Catalog.get({tabla:'cl_003_5_1'},function(result){
        		$scope.catalogWeNe = result;        	
        	});
        };        
        
        $scope.loadCatalogWeNe();
        
        
        $scope.loadCatalogOrgan = function(){
        	
        	Catalog.get({tabla:'cl_003_5_2'},function(result){
        		$scope.catalogOrgan = result;        	
        	});
        };        
        
        $scope.loadCatalogOrgan();     


        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:currentRevisionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.currentRevision.id != null) {
            	CurrentRevisionService.update({episode_id:$stateParams.episodeId}, $scope.currentRevision, onSaveFinished);
            } else {
            	CurrentRevisionService.save({episode_id:$stateParams.episodeId}, $scope.currentRevision, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
}(window.angular));