'use strict';

angular.module('fimepedApp').controller('ClinicHistoryAddInfDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ClinicHistoryAddInf', 'ClinicHistory',
        function($scope, $stateParams, $modalInstance, entity, ClinicHistoryAddInf, ClinicHistory) {

        $scope.clinicHistoryAddInf = entity;
        $scope.clinichistorys = ClinicHistory.query();
        $scope.load = function(id) {
            ClinicHistoryAddInf.get({id : id}, function(result) {
                $scope.clinicHistoryAddInf = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:clinicHistoryAddInfUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.clinicHistoryAddInf.id != null) {
                ClinicHistoryAddInf.update($scope.clinicHistoryAddInf, onSaveFinished);
            } else {
                ClinicHistoryAddInf.save($scope.clinicHistoryAddInf, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
