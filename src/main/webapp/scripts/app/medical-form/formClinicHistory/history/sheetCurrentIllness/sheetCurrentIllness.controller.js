(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('SheetCurrentIllnessController', function ($scope, $stateParams, CurrentIllnessService, ParseLinks) {
    	var onSaveFinished = function (result) {
            $scope.clear();
            $scope.currentIllnesss.push(result);
        };
  	
        var onUpdateFinished = function (result) {
            $scope.clear();
            
        };
        $scope.currentIllnesss = [];
        $scope.page = 1;
        $scope.loadAll = function() {
        	CurrentIllnessService.query({episode_id : $stateParams.episodeId, page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.currentIllnesss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
        	CurrentIllnessService.get({episode_id : $stateParams.episodeId, id: id}, function(result) {
                $scope.currentIllness = result;
                $('#deleteCurrentIllnessConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
        	CurrentIllnessService.delete({episode_id : $stateParams.episodeId, id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCurrentIllnessConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.currentIllness = {description: null, id: null};
        };
        
        $scope.save = function () {
            if ($scope.currentIllness.id != null) {
            	CurrentIllnessService.update({episode_id:$stateParams.episodeId}, $scope.currentIllness, onUpdateFinished);
            } else {            	
            	CurrentIllnessService.save({episode_id:$stateParams.episodeId}, $scope.currentIllness, onSaveFinished);
            }
        };      
        
        $scope.loadCurrentIllness = function(currentIllness){        	       	
        	$scope.currentIllness = currentIllness;
        };
        
        
    });
}(window.angular));
