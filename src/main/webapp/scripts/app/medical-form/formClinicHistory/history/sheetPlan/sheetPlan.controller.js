(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('SheetPlanController', function ($scope, $stateParams, PlanService, ParseLinks) {
    	var onSaveFinished = function (result) {
            $scope.clear();
            $scope.plans.push(result);
        };
  	
        var onUpdateFinished = function (result) {
            $scope.clear();
            
        };
        $scope.plans = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            PlanService.query({episode_id:$stateParams.episodeId, page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.plans = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PlanService.get({episode_id:$stateParams.episodeId, id: id}, function(result) {
                $scope.plan = result;
                $('#deletePlanConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PlanService.delete({episode_id:$stateParams.episodeId, id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePlanConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.plan = {description: null, id: null};
        };
        $scope.save = function () {
            if ($scope.plan.id != null) {
            	PlanService.update({episode_id:$stateParams.episodeId}, $scope.plan, onUpdateFinished);
            } else {            	
            	PlanService.save({episode_id:$stateParams.episodeId}, $scope.plan, onSaveFinished);
            }
        };      
        
        $scope.loadPlan = function(plan){        	       	
        	$scope.plan = plan;
        };
    });
}(window.angular));