(function(angular){'use strict';

angular.module('fimepedApp').controller('ProvinciaDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ProvinciaResource', 'CountryResource', 'CantonResource',
        function($scope, $stateParams, $modalInstance, entity, ProvinciaResource, CountryResource, CantonResource) {

        $scope.provincia = entity;
        $scope.countrys = CountryResource.query();
        $scope.cantons = CantonResource.query();
        $scope.load = function(id) {
            ProvinciaResource.get({id : id}, function(result) {
                $scope.provincia = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:provinciaUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.provincia.id != null) {
                ProvinciaResource.update($scope.provincia, onSaveFinished);
            } else {
                ProvinciaResource.save($scope.provincia, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
}(window.angular));