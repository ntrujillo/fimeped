(function(angular){
	'use strict';

angular.module('fimepedApp').controller('SheetPhysicalExamDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'physicalExam', 'PhysicalExamService','Catalog',
        function($scope, $stateParams, $modalInstance, physicalExam, PhysicalExamService,Catalog) {

        $scope.physicalExam = physicalExam;
        
        /*$scope.load = function(id) {
        	PhysicalExamService.get({episode_id:$stateParams.episodeId, id : id}, function(result) {
                $scope.physicalExam = result;
            });
        };*/
        
        $scope.loadCatalogWeNe= function(){
        	
        	Catalog.get({tabla:'cl_003_5_1'},function(result){
        		$scope.catalogWeNe = result;        	
        	});
        };        
        
        $scope.loadCatalogWeNe();
        
        
        $scope.loadCatalogBodyPart = function(){
        	
        	Catalog.get({tabla:'cl_003_7'},function(result){
        		$scope.catalogBodyPart = result;        	
        	});
        };        
        
        $scope.loadCatalogBodyPart(); 

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:physicalExamUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.physicalExam.id != null) {
            	PhysicalExamService.update({episode_id:$stateParams.episodeId}, $scope.physicalExam, onSaveFinished);
            } else {
            	PhysicalExamService.save({episode_id:$stateParams.episodeId}, $scope.physicalExam, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
}(window.angular));