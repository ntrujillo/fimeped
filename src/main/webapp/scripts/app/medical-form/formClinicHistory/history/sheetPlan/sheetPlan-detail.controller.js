(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('SheetPlanDetailController', function ($scope, $rootScope, $stateParams, entity, PlanService) {
        $scope.plan = entity;
        $scope.load = function (id) {
            PlanService.get({episode_id:$stateParams.episodeId, id: id}, function(result) {
                $scope.plan = result;
            });
        };
        $rootScope.$on('fimepedApp:planUpdate', function(event, result) {
            $scope.plan = result;
        });
    });
}(window.angular));