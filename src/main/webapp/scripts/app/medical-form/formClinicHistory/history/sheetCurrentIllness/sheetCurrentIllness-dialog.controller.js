'use strict';

angular.module('fimepedApp').controller('SheetCurrentIllnessDialogController',
    ['$scope', '$stateParams', '$modalInstance',  'currentIllness', 'CurrentIllnessService', 
        function($scope, $stateParams, $modalInstance,  currentIllness, CurrentIllnessService) {

        $scope.currentIllness = currentIllness;
   
       /* $scope.load = function(id) {
        	CurrentIllnessService.get({episode_id : $stateParams.episodeId, id : id}, function(result) {
                $scope.currentIllness = result;
            });
        };*/

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:currentIllnessUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.currentIllness.id != null) {
            	CurrentIllnessService.update({episode_id : $stateParams.episodeId}, $scope.currentIllness, onSaveFinished);
            } else {
            	CurrentIllnessService.save({episode_id : $stateParams.episodeId}, $scope.currentIllness, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
