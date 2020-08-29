'use strict';

angular.module('fimepedApp').controller('VitalSignDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'VitalSign', 'Episode',
        function($scope, $stateParams, $modalInstance, entity, VitalSign, Episode) {

        $scope.vitalSign = entity;
        $scope.episodes = Episode.query();
        $scope.load = function(id) {
            VitalSign.get({id : id}, function(result) {
                $scope.vitalSign = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:vitalSignUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.vitalSign.id != null) {
                VitalSign.update($scope.vitalSign, onSaveFinished);
            } else {
                VitalSign.save($scope.vitalSign, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
