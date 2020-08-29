'use strict';

angular.module('fimepedApp').controller('CantonDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CantonResource', 'ProvinciaResource',
        function($scope, $stateParams, $modalInstance, entity, CantonResource, ProvinciaResource) {

        $scope.canton = entity;
        $scope.provincias = ProvinciaResource.query();
        $scope.load = function(id) {
            CantonResource.get({id : id}, function(result) {
                $scope.canton = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:cantonUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.canton.id != null) {
                CantonResource.update($scope.canton, onSaveFinished);
            } else {
                CantonResource.save($scope.canton, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
