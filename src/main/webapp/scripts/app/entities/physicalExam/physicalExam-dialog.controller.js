'use strict';

angular.module('fimepedApp').controller('PhysicalExamDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PhysicalExam', 'Episode',
        function($scope, $stateParams, $modalInstance, entity, PhysicalExam, Episode) {

        $scope.physicalExam = entity;
        $scope.episodes = Episode.query();
        $scope.load = function(id) {
            PhysicalExam.get({id : id}, function(result) {
                $scope.physicalExam = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:physicalExamUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.physicalExam.id != null) {
                PhysicalExam.update($scope.physicalExam, onSaveFinished);
            } else {
                PhysicalExam.save($scope.physicalExam, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
