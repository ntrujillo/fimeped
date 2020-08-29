'use strict';

angular.module('fimepedApp')
    .controller('PersonalHistoryDetailController', function ($scope, $rootScope, $stateParams, entity, PersonalHistory, ClinicHistory) {
        $scope.personalHistory = entity;
        $scope.load = function (id) {
            PersonalHistory.get({id: id}, function(result) {
                $scope.personalHistory = result;
            });
        };
        $rootScope.$on('fimepedApp:personalHistoryUpdate', function(event, result) {
            $scope.personalHistory = result;
        });
    });
