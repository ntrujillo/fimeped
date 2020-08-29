'use strict';

angular.module('fimepedApp')
    .controller('PersonDetailController', function ($scope, $rootScope, $stateParams, entity, Person, ClinicHistory) {
        $scope.person = entity;
        $scope.load = function (id) {
            Person.get({id: id}, function(result) {
                $scope.person = result;                
            });
        };
        $rootScope.$on('fimepedApp:personUpdate', function(event, result) {
            $scope.person = result;
        });
    });
