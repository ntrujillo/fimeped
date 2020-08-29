(function(angular){
	'use strict';

angular.module('fimepedApp').controller('SheetPlanDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'plan', 'PlanService',
        function($scope, $stateParams, $modalInstance, plan, PlanService) {

        $scope.plan = plan;
        $scope.load = function(id) {
            Plan.get({episode_id:$stateParams.episodeId, id : id}, function(result) {
                $scope.plan = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:planUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.plan.id != null) {
                PlanService.update({episode_id:$stateParams.episodeId}, $scope.plan, onSaveFinished);
            } else {
                PlanService.save({episode_id:$stateParams.episodeId}, $scope.plan, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
}(window.angular));