'use strict';

angular.module('fimepedApp')
    .controller('PlanDetailController', function ($scope, $rootScope, $stateParams, entity, Plan, Episode) {
        $scope.plan = entity;
        $scope.load = function (id) {
            Plan.get({id: id}, function(result) {
                $scope.plan = result;
            });
        };
        $rootScope.$on('fimepedApp:planUpdate', function(event, result) {
            $scope.plan = result;
        });
    });
