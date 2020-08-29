'use strict';

angular.module('fimepedApp').controller('DiagnosticDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Diagnostic', 'Episode',
        function($scope, $stateParams, $modalInstance, entity, Diagnostic, Episode) {

        $scope.diagnostic = entity;
        $scope.episodes = Episode.query();
        $scope.load = function(id) {
            Diagnostic.get({id : id}, function(result) {
                $scope.diagnostic = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:diagnosticUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.diagnostic.id != null) {
                Diagnostic.update($scope.diagnostic, onSaveFinished);
            } else {
                Diagnostic.save($scope.diagnostic, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
