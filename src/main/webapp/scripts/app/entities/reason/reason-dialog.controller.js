'use strict';

angular.module('fimepedApp').controller('ReasonDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Reason', 'Episode',
        function($scope, $stateParams, $modalInstance, entity, Reason, Episode) {

        $scope.reason = entity;
        $scope.episodes = Episode.query();
        $scope.load = function(id) {
            Reason.get({id : id}, function(result) {
                $scope.reason = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:reasonUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.reason.id != null) {
                Reason.update($scope.reason, onSaveFinished);
            } else {
                Reason.save($scope.reason, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
