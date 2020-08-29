'use strict';

angular.module('fimepedApp')
    .controller('FamilyHistoryDetailController', function ($scope, $rootScope, $stateParams, entity, FamilyHistory, ClinicHistory) {
        $scope.familyHistory = entity;
        $scope.load = function (id) {
            FamilyHistory.get({id: id}, function(result) {
                $scope.familyHistory = result;
            });
        };
        $rootScope.$on('fimepedApp:familyHistoryUpdate', function(event, result) {
            $scope.familyHistory = result;
        });
    });
