(function(angular){
		'use strict';

angular.module('fimepedApp')
    .controller('SheetFamilyHistoryController', function ($scope,$stateParams, FamilyHistoryService,DateUtils, CatalogResource, ParseLinks) {
    	
    	var onSaveFinished = function (result) {
            $scope.clear();
            $scope.familyHistorys.push(result);
        };
  	
        var onUpdateFinished = function (result) {
            $scope.clear();
            
        };
        $scope.familyHistorys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
        	FamilyHistoryService.query({history_id: $stateParams.historyId, page: $scope.page, per_page: 5}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.familyHistorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
        
        
        $scope.loadCatalogIllness = function(){
        	
        	CatalogResource.query({tab_id:'cl_003_2'},function(result){
        		$scope.catalogIllness = result;        	
        	});
        };
        
        
        $scope.loadCatalogIllness();
        
        $scope.delete = function (id) {
        	FamilyHistoryService.get({history_id: $stateParams.historyId, id: id}, function(result) {
                $scope.familyHistory = result;
                $('#deleteFamilyHistoryConfirmation').modal('show');
            });
        };
        $scope.confirmDelete = function (id) {
        	FamilyHistoryService.delete({history_id: $stateParams.historyId, id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFamilyHistoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };
        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };
        $scope.clear = function () {
            $scope.familyHistory = {date: null, description: null, illness: null, id: null};
        };
        
        
        $scope.save = function () {
            if ($scope.familyHistory.id != null) {
                FamilyHistoryService.update({history_id: $stateParams.historyId}, $scope.familyHistory, onUpdateFinished);
            } else {            	
                FamilyHistoryService.save({history_id: $stateParams.historyId}, $scope.familyHistory, onSaveFinished);
            }
        };
        
        $scope.loadFamilyHistory = function(familyHistory){
        	if(familyHistory.date && !(familyHistory.date instanceof Date)) {
        		familyHistory.date = DateUtils.convertLocaleDateFromServer(familyHistory.date);
        	}
        	$scope.familyHistory = familyHistory;
        };

    });
}(window.angular));