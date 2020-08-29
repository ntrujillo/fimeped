'use strict';

angular.module('fimepedApp').controller('CurrentIllnessDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CurrentIllness', 'Episode',
        function($scope, $stateParams, $modalInstance, entity, CurrentIllness, Episode) {

        $scope.currentIllness = entity;
        $scope.episodes = Episode.query();
        $scope.load = function(id) {
            CurrentIllness.get({id : id}, function(result) {
                $scope.currentIllness = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:currentIllnessUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.currentIllness.id != null) {
                CurrentIllness.update($scope.currentIllness, onSaveFinished);
            } else {
                CurrentIllness.save($scope.currentIllness, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
