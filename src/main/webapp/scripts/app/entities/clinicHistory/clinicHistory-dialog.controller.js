'use strict';

angular.module('fimepedApp').controller('ClinicHistoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'ClinicHistory',  'Person', 'Episode', 'PersonalHistory', 'FamilyHistory',
        function($scope, $stateParams, $modalInstance, $q, entity, ClinicHistory,  Person, Episode, PersonalHistory, FamilyHistory) {

        $scope.clinicHistory = entity;    
        $scope.persons = Person.query();
        $scope.episodes = Episode.query();
        $scope.personalhistorys = PersonalHistory.query();
        $scope.familyhistorys = FamilyHistory.query();
        $scope.load = function(id) {
            ClinicHistory.get({id : id}, function(result) {
                $scope.clinicHistory = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:clinicHistoryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.clinicHistory.id != null) {
                ClinicHistory.update($scope.clinicHistory, onSaveFinished);
            } else {
                ClinicHistory.save($scope.clinicHistory, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
