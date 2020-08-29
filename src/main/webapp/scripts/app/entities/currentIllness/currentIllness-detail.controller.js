'use strict';

angular.module('fimepedApp')
    .controller('CurrentIllnessDetailController', function ($scope, $rootScope, $stateParams, entity, CurrentIllness, Episode) {
        $scope.currentIllness = entity;
        $scope.load = function (id) {
            CurrentIllness.get({id: id}, function(result) {
                $scope.currentIllness = result;
            });
        };
        $rootScope.$on('fimepedApp:currentIllnessUpdate', function(event, result) {
            $scope.currentIllness = result;
        });
    });
