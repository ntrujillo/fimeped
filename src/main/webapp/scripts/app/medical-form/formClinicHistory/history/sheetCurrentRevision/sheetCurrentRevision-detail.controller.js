'use strict';

angular.module('fimepedApp')
    .controller('SheetCurrentRevisionDetailController', function ($scope, $rootScope, $stateParams, entity, CurrentRevisionService) {
        $scope.currentRevision = entity;
        $scope.load = function (id) {
            CurrentRevisionService.get({episode_id:$stateParams.episodeId, id: id}, function(result) {
                $scope.currentRevision = result;
            });
        };
        $rootScope.$on('fimepedApp:currentRevisionUpdate', function(event, result) {
            $scope.currentRevision = result;
        });
    });
