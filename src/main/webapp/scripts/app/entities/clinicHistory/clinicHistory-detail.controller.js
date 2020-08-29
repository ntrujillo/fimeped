(function(angular){
'use strict';

angular.module('fimepedApp')
    .controller('ClinicHistoryDetailController', function ($scope, $rootScope, $stateParams, entity, ClinicHistory, ClinicHistoryAddInf, Person, Episode, PersonalHistory, FamilyHistory) {
        $scope.clinicHistory = entity;
        $scope.load = function (id) {
            ClinicHistory.get({id: id}, function(result) {
                $scope.clinicHistory = result;
            });
        };
        $rootScope.$on('fimepedApp:clinicHistoryUpdate', function(event, result) {
            $scope.clinicHistory = result;
        });
    });
}(window.angular))