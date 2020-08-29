'use strict';

angular.module('fimepedApp')
    .controller('SheetPersonalHistoryDetailController', function ($scope, $rootScope, $stateParams, entity, PersonalHistoryService, ClinicHistory) {
        $scope.personalHistory = entity;
        $scope.load = function (id) {
            PersonalHistoryService.get({history_id: $stateParams.historyId,id: id}, function(result) {
                $scope.personalHistory = result;
            });
        };
        $rootScope.$on('fimepedApp:personalHistoryUpdate', function(event, result) {
            $scope.personalHistory = result;
        });
    });
