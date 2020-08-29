(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('SheetEvolutionPrescriptionDetailController', function ($scope, $rootScope, $stateParams, entity, EvolutionPrescriptionService) {
        $scope.evolutionPrescription = entity;
        $scope.load = function (id) {
        	EvolutionPrescriptionService.get({episode_id:$stateParams.episodeId, id: id}, function(result) {
                $scope.evolutionPrescription = result;
            });
        };
        $rootScope.$on('fimepedApp:evolutionPrescriptionUpdate', function(event, result) {
            $scope.evolutionPrescription = result;
        });
    });
}(window.angular));