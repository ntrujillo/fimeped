'use strict';

angular.module('fimepedApp')
    .controller('CantonDetailController', function ($scope, $rootScope, $stateParams, entity, CantonResource, ProvinciaResource) {
        $scope.canton = entity;
        $scope.load = function (id) {
            CantonResource.get({id: id}, function(result) {
                $scope.canton = result;
            });
        };
        $rootScope.$on('fimepedApp:cantonUpdate', function(event, result) {
            $scope.canton = result;
        });
    });
