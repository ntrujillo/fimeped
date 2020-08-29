'use strict';

angular.module('fimepedApp').controller('EpisodeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Episode', 'User', 'ClinicHistory', 'Reason', 'CurrentIllness', 'CurrentRevision', 'VitalSign', 'PhysicalExam', 'Diagnostic', 'Plan', 'EvolutionPrescription',
        function($scope, $stateParams, $modalInstance, entity, Episode, User, ClinicHistory, Reason, CurrentIllness, CurrentRevision, VitalSign, PhysicalExam, Diagnostic, Plan, EvolutionPrescription) {

        $scope.episode = entity;
        $scope.users = User.query();
        $scope.clinichistorys = ClinicHistory.query();
        $scope.reasons = Reason.query();
        $scope.currentillnesss = CurrentIllness.query();
        $scope.currentrevisions = CurrentRevision.query();
        $scope.vitalsigns = VitalSign.query();
        $scope.physicalexams = PhysicalExam.query();
        $scope.diagnostics = Diagnostic.query();
        $scope.plans = Plan.query();
        $scope.evolutionprescriptions = EvolutionPrescription.query();
        $scope.load = function(id) {
            Episode.get({id : id}, function(result) {
                $scope.episode = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:episodeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.episode.id != null) {
                Episode.update($scope.episode, onSaveFinished);
            } else {
                Episode.save($scope.episode, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
