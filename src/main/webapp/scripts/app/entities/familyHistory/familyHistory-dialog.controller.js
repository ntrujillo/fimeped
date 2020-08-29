'use strict';

angular.module('fimepedApp').controller('FamilyHistoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'FamilyHistory', 'ClinicHistory',
        function($scope, $stateParams, $modalInstance, entity, FamilyHistory, ClinicHistory) {

        $scope.familyHistory = entity;
        $scope.clinichistorys = ClinicHistory.query();
        $scope.load = function(id) {
            FamilyHistory.get({id : id}, function(result) {
                $scope.familyHistory = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:familyHistoryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.familyHistory.id != null) {
                FamilyHistory.update($scope.familyHistory, onSaveFinished);
            } else {
                FamilyHistory.save($scope.familyHistory, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
