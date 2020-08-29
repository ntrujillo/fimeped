'use strict';

angular.module('fimepedApp')
    .controller('SheetReasonDetailController', function ($scope, $rootScope, $stateParams, entity, ReasonService) {
        $scope.reason = entity;
        $scope.load = function (id) {
        	ReasonService.get({episode_id:1,id: id}, function(result) {
                $scope.reason = result;
            });
        };
        $rootScope.$on('fimepedApp:reasonUpdate', function(event, result) {
            $scope.reason = result;
        });
    });
