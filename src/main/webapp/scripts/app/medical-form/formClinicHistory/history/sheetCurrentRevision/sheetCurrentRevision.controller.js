(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('SheetCurrentRevisionController', function ($scope,$stateParams, CurrentRevisionService,CatalogResource, ParseLinks) {
    	var onSaveFinished = function (result) {
            $scope.clear();
            $scope.currentRevisions.push(result);
        };
  	
        var onUpdateFinished = function (result) {
            $scope.clear();
            
        };
        $scope.currentRevisions = [];
        $scope.page = 1;
        $scope.loadAll = function() {
        	CurrentRevisionService.query({episode_id : $stateParams.episodeId, page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.currentRevisions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
        
        $scope.loadCatalogWeNe= function(){
        	
        	CatalogResource.query({tab_id:'cl_003_5_1', sort:"value"},function(result){
        		$scope.catalogWeNe = result;        	
        	});
        };        
        
        $scope.loadCatalogWeNe();
        
        
        $scope.loadCatalogOrgan = function(){
        	
        	CatalogResource.query({tab_id:'cl_003_5_2', sort:"value"},function(result){
        		$scope.catalogOrgan = result;        	
        	});
        };        
        
        $scope.loadCatalogOrgan();   

        $scope.delete = function (id) {
        	CurrentRevisionService.get({episode_id : $stateParams.episodeId, id: id}, function(result) {
                $scope.currentRevision = result;
                $('#deleteCurrentRevisionConfirmation').modal('show');
            });
        };
        $scope.confirmDelete = function (id) {
        	CurrentRevisionService.delete({episode_id : $stateParams.episodeId, id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCurrentRevisionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.currentRevision = {description: null, organ: null, weNe: null, id: null};
        };
        
        $scope.save = function () {
            if ($scope.currentRevision.id != null) {
            	CurrentRevisionService.update({episode_id:$stateParams.episodeId}, $scope.currentRevision, onUpdateFinished);
            } else {            	
            	CurrentRevisionService.save({episode_id:$stateParams.episodeId}, $scope.currentRevision, onSaveFinished);
            }
        };      
        
        $scope.loadCurrentRevision = function(currentRevision){        	       	
        	$scope.currentRevision = currentRevision;
        }
    });
}(window.angular));