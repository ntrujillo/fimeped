(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('SheetPhysicalExamController', function ($scope,$stateParams, PhysicalExamService, CatalogResource, ParseLinks) {
    	var onSaveFinished = function (result) {
            $scope.clear();
            $scope.physicalExams.push(result);
        };
  	
        var onUpdateFinished = function (result) {
            $scope.clear();
            
        };
    	
        $scope.physicalExams = [];
        $scope.page = 1;
        $scope.loadAll = function() {
        	PhysicalExamService.query({episode_id : $stateParams.episodeId, page: $scope.page, per_page: 5}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.physicalExams = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
        
        
        $scope.loadCatalogWeNe= function(){
        	
        	CatalogResource.query({tab_id:'cl_003_5_1',sort:"value"},function(result){
        		$scope.catalogWeNe = result;        	
        	});
        };        
        
        $scope.loadCatalogWeNe();
        
        
        $scope.loadCatalogBodyPart = function(){
        	
        	CatalogResource.query({tab_id:'cl_003_7',sort:"value"},function(result){
        		$scope.catalogBodyPart = result;        	
        	});
        };        
        
        $scope.loadCatalogBodyPart(); 

        $scope.delete = function (id) {
        	PhysicalExamService.get({episode_id : $stateParams.episodeId, id: id}, function(result) {
                $scope.physicalExam = result;
                $('#deletePhysicalExamConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
        	PhysicalExamService.delete({episode_id : $stateParams.episodeId, id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePhysicalExamConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.physicalExam = {description: null, bodyPart: null, weNe: null, id: null};
        };
        
        
        $scope.save = function () {
            if ($scope.physicalExam.id != null) {
            	PhysicalExamService.update({episode_id:$stateParams.episodeId}, $scope.physicalExam, onUpdateFinished);
            } else {            	
            	PhysicalExamService.save({episode_id:$stateParams.episodeId}, $scope.physicalExam, onSaveFinished);
            }
        };      
        
        $scope.loadPhysicalExam = function(physicalExam){        	       	
        	$scope.physicalExam = physicalExam;
        };
        
        
    });
}(window.angular));