(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('CountryDetailController', function ($scope, $rootScope, $stateParams, entity, CountryResource, ProvinciaResource) {
        $scope.country = entity;
        $scope.load = function (id) {
            CountryResource.get({id: id}, function(result) {
                $scope.country = result;
            });
        };
        $rootScope.$on('fimepedApp:countryUpdate', function(event, result) {
            $scope.country = result;
        });
    });
}(window.angular));