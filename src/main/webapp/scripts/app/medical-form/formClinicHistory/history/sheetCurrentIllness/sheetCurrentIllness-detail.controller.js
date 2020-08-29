'use strict';

angular.module('fimepedApp')
    .controller('SheetCurrentIllnessDetailController', function ($scope, $rootScope, $stateParams, entity, CurrentIllnessService, Episode) {
        $scope.currentIllness = entity;
        $scope.load = function (id) {
            CurrentIllnessService.get({episode_id:$stateParams.episodeId, id: id}, function(result) {
                $scope.currentIllness = result;
            });
        };
        $rootScope.$on('fimepedApp:currentIllnessUpdate', function(event, result) {
            $scope.currentIllness = result;
        });
    });
