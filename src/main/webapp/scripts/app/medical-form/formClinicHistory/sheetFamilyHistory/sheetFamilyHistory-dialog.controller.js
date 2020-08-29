(function(angular){
'use strict';

angular.module('fimepedApp').controller('SheetFamilyHistoryDialogController',
    ['$scope','$stateParams',  '$modalInstance','familyHistory', 'FamilyHistoryService', 'CatalogResource',
        function($scope, $stateParams, $modalInstance, familyHistory, FamilyHistoryService, CatalogResource) {

        $scope.familyHistory = familyHistory;
       
        $scope.load = function(id) {
            FamilyHistoryService.get({history_id: $stateParams.historyId, id : id}, function(result) {
                $scope.familyHistory = result;
            });
        };               
        $scope.loadCatalogIllness = function(){
        	
        	CatalogResource.query({tab_id:'cl_003_3',sort:"value"},function(result){
        		$scope.catalogIllness = result;        	
        	});
        };       
        $scope.loadCatalogIllness();
        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:familyHistoryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.familyHistory.id != null) {
                FamilyHistoryService.update({history_id: $stateParams.historyId}, $scope.familyHistory, onSaveFinished);
            } else {            	
                FamilyHistoryService.save({history_id: $stateParams.historyId}, $scope.familyHistory, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
}(window.angular));