'use strict';

angular.module('fimepedApp')
    .controller('EvolutionPrescriptionDetailController', function ($scope, $rootScope, $stateParams, entity, EvolutionPrescription, Episode) {
        $scope.evolutionPrescription = entity;
        $scope.load = function (id) {
            EvolutionPrescription.get({id: id}, function(result) {
                $scope.evolutionPrescription = result;
            });
        };
        $rootScope.$on('fimepedApp:evolutionPrescriptionUpdate', function(event, result) {
            $scope.evolutionPrescription = result;
        });
    });
