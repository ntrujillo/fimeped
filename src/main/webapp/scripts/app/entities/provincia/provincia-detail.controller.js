(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('ProvinciaDetailController', function ($scope, $rootScope, $stateParams, entity, ProvinciaResource, CountryResource, CantonResource) {
        $scope.provincia = entity;
        $scope.load = function (id) {
            ProvinciaResource.get({id: id}, function(result) {
                $scope.provincia = result;
            });
        };
        $rootScope.$on('fimepedApp:provinciaUpdate', function(event, result) {
            $scope.provincia = result;
        });
    });
}(window.angular));