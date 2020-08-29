(function(angular){
		'use strict';

angular.module('fimepedApp')
    .controller('SheetPersonalHistoryController', function ($scope,$stateParams, PersonalHistoryService,DateUtils, CatalogResource, ParseLinks) {
    	
    	var onSaveFinished = function (result) {
            $scope.clear();
            $scope.personalHistorys.push(result);
        };
  	
        var onUpdateFinished = function (result) {
            $scope.clear();
            
        };
        
        $scope.personalHistorys = [];
        $scope.catalogIllness=[];       
        $scope.page = 1;
        
        $scope.loadAll = function() {
        	PersonalHistoryService.query({history_id: $stateParams.historyId, page: $scope.page, per_page: 5}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.personalHistorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();         
        
        $scope.loadCatalogIllness = function(){
        	
        	CatalogResource.query({tab_id:'cl_003_2', sort:'value'},function(result){
        		$scope.catalogIllness = result;        	
        	});
        };
        
        
        $scope.loadCatalogIllness();

        $scope.delete = function (id) {
        	PersonalHistoryService.get({history_id: $stateParams.historyId, id: id}, function(result) {
                $scope.personalHistory = result;
                $('#deletePersonalHistoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
        	PersonalHistoryService.delete({history_id: $stateParams.historyId, id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePersonalHistoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.personalHistory = {date: null, description: null, illness: null, id: null};
        };
        
        $scope.save = function () {
            if ($scope.personalHistory.id != null) {
            	PersonalHistoryService.update({history_id: $stateParams.historyId}, $scope.personalHistory, onUpdateFinished);
            } else {            
            	PersonalHistoryService.save({history_id: $stateParams.historyId}, $scope.personalHistory, onSaveFinished);
            }
        };
        
        $scope.loadPersonalHistory = function(personalHistory){
        	if(personalHistory.date && !(personalHistory.date instanceof Date)) {
        		personalHistory.date = DateUtils.convertLocaleDateFromServer(personalHistory.date);
        	}
        	$scope.personalHistory = personalHistory;
        	
        };
    });
}(window.angular));
