(function(){
	'use strict';

angular.module('fimepedApp')
    .controller('SheetFamilyHistoryDetailController', function ($scope, $rootScope, $stateParams, entity, FamilyHistoryService, ClinicHistory) {
        $scope.familyHistory = entity;
        $scope.load = function (id) {
            FamilyHistoryService.get({history_id: $stateParams.historyId, id: id}, function(result) {
                $scope.familyHistory = result;
            });
        };
        $rootScope.$on('fimepedApp:familyHistoryUpdate', function(event, result) {
            $scope.familyHistory = result;
        });
    });
}(window.angular));