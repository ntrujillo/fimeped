'use strict';

angular.module('fimepedApp')
    .controller('ReasonDetailController', function ($scope, $rootScope, $stateParams, entity, Reason, Episode) {
        $scope.reason = entity;
        $scope.load = function (id) {
            Reason.get({id: id}, function(result) {
                $scope.reason = result;
            });
        };
        $rootScope.$on('fimepedApp:reasonUpdate', function(event, result) {
            $scope.reason = result;
        });
    });
