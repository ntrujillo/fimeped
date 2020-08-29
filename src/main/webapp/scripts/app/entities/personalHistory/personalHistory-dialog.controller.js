'use strict';

angular.module('fimepedApp').controller('PersonalHistoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PersonalHistory', 'ClinicHistory',
        function($scope, $stateParams, $modalInstance, entity, PersonalHistory, ClinicHistory) {

        $scope.personalHistory = entity;
        $scope.clinichistorys = ClinicHistory.query();
        $scope.load = function(id) {
            PersonalHistory.get({id : id}, function(result) {
                $scope.personalHistory = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:personalHistoryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.personalHistory.id != null) {
                PersonalHistory.update($scope.personalHistory, onSaveFinished);
            } else {
                PersonalHistory.save($scope.personalHistory, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
