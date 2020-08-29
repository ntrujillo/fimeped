'use strict';

angular.module('fimepedApp').controller('EvolutionPrescriptionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'EvolutionPrescription', 'Episode',
        function($scope, $stateParams, $modalInstance, entity, EvolutionPrescription, Episode) {

        $scope.evolutionPrescription = entity;
        $scope.episodes = Episode.query();
        $scope.load = function(id) {
            EvolutionPrescription.get({id : id}, function(result) {
                $scope.evolutionPrescription = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:evolutionPrescriptionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.evolutionPrescription.id != null) {
                EvolutionPrescription.update($scope.evolutionPrescription, onSaveFinished);
            } else {
                EvolutionPrescription.save($scope.evolutionPrescription, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
