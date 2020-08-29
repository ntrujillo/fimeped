'use strict';

angular.module('fimepedApp').controller('CurrentRevisionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CurrentRevision', 'Episode',
        function($scope, $stateParams, $modalInstance, entity, CurrentRevision, Episode) {

        $scope.currentRevision = entity;
        $scope.episodes = Episode.query();
        $scope.load = function(id) {
            CurrentRevision.get({id : id}, function(result) {
                $scope.currentRevision = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:currentRevisionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.currentRevision.id != null) {
                CurrentRevision.update($scope.currentRevision, onSaveFinished);
            } else {
                CurrentRevision.save($scope.currentRevision, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
