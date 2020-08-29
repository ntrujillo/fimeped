'use strict';

angular.module('fimepedApp').controller('PersonDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Person', 'ClinicHistory',
        function($scope, $stateParams, $modalInstance, entity, Person, ClinicHistory) {

        $scope.person = entity;
        $scope.clinichistorys = ClinicHistory.query();
        $scope.load = function(id) {
            Person.get({id : id}, function(result) {
                $scope.person = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:personUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.person.id != null) {
                Person.update($scope.person, onSaveFinished);
            } else {
                Person.save($scope.person, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
