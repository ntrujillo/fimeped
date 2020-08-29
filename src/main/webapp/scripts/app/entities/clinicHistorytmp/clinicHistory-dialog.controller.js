'use strict';

angular.module('fimepedApp').controller('ClinicHistoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'ClinicHistory', 'ClinicHistoryAddInf', 'Person', 'Episode', 'PersonalHistory', 'FamilyHistory',
        function($scope, $stateParams, $modalInstance, $q, entity, ClinicHistory, ClinicHistoryAddInf, Person, Episode, PersonalHistory, FamilyHistory) {

        $scope.clinicHistory = entity;
        $scope.clinic_add_infs = ClinicHistoryAddInf.query({filter: 'add_inf_clinic-is-null'});
        $q.all([$scope.add_inf_clinic.$promise, $scope.clinic_add_infs.$promise]).then(function() {
            if (!$scope.add_inf_clinic.clinic_add_inf.id) {
                return $q.reject();
            }
            return ClinicHistoryAddInf.get({id : $scope.add_inf_clinic.clinic_add_inf.id}).$promise;
        }).then(function(clinic_add_inf) {
            $scope.clinic_add_infs.push(clinic_add_inf);
        });
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
