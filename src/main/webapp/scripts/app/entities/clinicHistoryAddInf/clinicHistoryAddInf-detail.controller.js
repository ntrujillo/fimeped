'use strict';

angular.module('fimepedApp')
    .controller('ClinicHistoryAddInfDetailController', function ($scope, $rootScope, $stateParams, entity, ClinicHistoryAddInf, ClinicHistory) {
        $scope.clinicHistoryAddInf = entity;
        $scope.load = function (id) {
            ClinicHistoryAddInf.get({id: id}, function(result) {
                $scope.clinicHistoryAddInf = result;
            });
        };
        $rootScope.$on('fimepedApp:clinicHistoryAddInfUpdate', function(event, result) {
            $scope.clinicHistoryAddInf = result;
        });
    });
