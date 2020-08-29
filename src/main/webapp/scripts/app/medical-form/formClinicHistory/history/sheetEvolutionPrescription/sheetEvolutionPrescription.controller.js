(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('SheetEvolutionPrescriptionController', function ($scope, $stateParams, EvolutionPrescriptionService, ParseLinks) {
    	var onSaveFinished = function (result) {
            $scope.clear();
            $scope.evolutionPrescriptions.push(result);
        };
  	
        var onUpdateFinished = function (result) {
            $scope.clear();
            
        };
        $scope.evolutionPrescriptions = [];
        $scope.page = 1;
        $scope.loadAll = function() {
        	EvolutionPrescriptionService.query({episode_id:$stateParams.episodeId, page: $scope.page, per_page: 5}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.evolutionPrescriptions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
        	EvolutionPrescriptionService.get({episode_id:$stateParams.episodeId, id: id}, function(result) {
                $scope.evolutionPrescription = result;
                $('#deleteEvolutionPrescriptionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
        	EvolutionPrescriptionService.delete({episode_id:$stateParams.episodeId, id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEvolutionPrescriptionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.evolutionPrescription = {evolution: null, prescription: null, medicines: null, id: null};
        };
        
        
        $scope.save = function () {
            if ($scope.evolutionPrescription.id != null) {
            	EvolutionPrescriptionService.update({episode_id:$stateParams.episodeId}, $scope.evolutionPrescription, onSaveFinished);
            } else {
            	EvolutionPrescriptionService.save({episode_id:$stateParams.episodeId}, $scope.evolutionPrescription, onSaveFinished);
            }
        };
        
        $scope.loadEvolutionPrescription = function(evolutionPrescription){
        	 $scope.evolutionPrescription = evolutionPrescription;
        };

    });
}(window.angular));
