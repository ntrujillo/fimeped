'use strict';

angular.module('fimepedApp').controller('PlanDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Plan', 'Episode',
        function($scope, $stateParams, $modalInstance, entity, Plan, Episode) {

        $scope.plan = entity;
        $scope.episodes = Episode.query();
        $scope.load = function(id) {
            Plan.get({id : id}, function(result) {
                $scope.plan = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:planUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.plan.id != null) {
                Plan.update($scope.plan, onSaveFinished);
            } else {
                Plan.save($scope.plan, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
