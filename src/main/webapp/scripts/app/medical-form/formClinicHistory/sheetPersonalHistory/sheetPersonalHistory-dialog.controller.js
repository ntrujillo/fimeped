(function(angular){
'use strict';

angular.module('fimepedApp').controller('SheetPersonalHistoryDialogController',
    ['$scope','$stateParams',  '$modalInstance', 'personalHistory', 'PersonalHistoryService', 'CatalogResource',
        function($scope, $stateParams, $modalInstance,  personalHistory, PersonalHistoryService, CatalogResource) {

        $scope.personalHistory = personalHistory;       
  
        /*$scope.load = function(id) {
        	PersonalHistoryService.get({history_id: $stateParams.historyId, id : id}, function(result) {
                $scope.personalHistory = result;
            });
        }; */      
        
        $scope.loadCatalogIllness = function(){
        	
        	CatalogResource.query({tab_id:'cl_003_2',sort:"value"},function(result){
        		$scope.catalogIllness = result;        	
        	});
        };        
        
        $scope.loadCatalogIllness();

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:personalHistoryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.personalHistory.id != null) {
            	PersonalHistoryService.update({history_id: $stateParams.historyId}, $scope.personalHistory, onSaveFinished);
            } else {            
            	PersonalHistoryService.save({history_id: $stateParams.historyId}, $scope.personalHistory, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
}(window.angular));