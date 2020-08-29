(function(angular){
	'use strict';

angular.module('fimepedApp').controller('SheetEvolutionPrescriptionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'EvolutionPrescriptionService',
        function($scope, $stateParams, $modalInstance, entity, EvolutionPrescriptionService) {

        $scope.evolutionPrescription = entity;  
       /* $scope.load = function(id) {
        	EvolutionPrescriptionService.get({episode_id:$stateParams.episodeId, id : id}, function(result) {
                $scope.evolutionPrescription = result;
            });
        };*/

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:evolutionPrescriptionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.evolutionPrescription.id != null) {
            	EvolutionPrescriptionService.update({episode_id:$stateParams.episodeId}, $scope.evolutionPrescription, onSaveFinished);
            } else {
            	EvolutionPrescriptionService.save({episode_id:$stateParams.episodeId}, $scope.evolutionPrescription, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
}(window.angular));