(function(angular){
	'use strict';

angular.module('fimepedApp').controller('CountryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CountryResource', 'ProvinciaResource',
        function($scope, $stateParams, $modalInstance, entity, CountryResource, ProvinciaResource) {

        $scope.country = entity;
        $scope.provincias = ProvinciaResource.query();
        $scope.load = function(id) {
            CountryResource.get({id : id}, function(result) {
                $scope.country = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:countryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.country.id != null) {
                CountryResource.update($scope.country, onSaveFinished);
            } else {
                CountryResource.save($scope.country, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
}(window.angular));