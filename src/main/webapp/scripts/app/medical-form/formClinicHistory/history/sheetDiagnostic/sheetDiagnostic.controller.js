(function(){
	'use strict';

angular.module('fimepedApp')
    .controller('SheetDiagnosticController', function ($scope,$modal,$stateParams, DiagnosticService,CatalogResource, ParseLinks) {
    	var onSaveFinished = function (result) {
            $scope.clear();
            $scope.diagnostics.push(result);
        };
  	
        var onUpdateFinished = function (result) {
            $scope.clear();
            
        };
        $scope.diagnostics = [];
        $scope.page = 1;
        $scope.loadAll = function() {
        	DiagnosticService.query({episode_id : $stateParams.episodeId, page: $scope.page, per_page: 5}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.diagnostics = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();            
        };
        $scope.loadAll();
       
        
        $scope.loadCatalogPreDef = function(){
        	
        	CatalogResource.query({tab_id:'cl_002_8',sort:"value"},function(result){
        		$scope.catalogPreDef = result;        	
        	});
        };        
        
        $scope.loadCatalogPreDef();
        
        

        $scope.delete = function (id) {
        	DiagnosticService.get({episode_id : $stateParams.episodeId, id: id}, function(result) {
                $scope.diagnostic = result;
                $('#deleteDiagnosticConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
        	DiagnosticService.delete({episode_id : $stateParams.episodeId, id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDiagnosticConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.diagnostic = {description: null, cie: null, preDef: null, id: null};
        };
        $scope.clear();
        
        $scope.showModalCie = function(){
        	var modalInstance = $modal.open({
				 templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetDiagnostic/search-icd.html',
                controller: 'SearchCieController',
                size: 'lg',
                backdrop : 'static',
                animation:true					
			});
			
			modalInstance.result.then(function(selectedCie){				
				$scope.diagnostic.cie = selectedCie.code;
				
			});	
        };
        
        
        $scope.save = function () {
            if ($scope.diagnostic.id != null) {
            	DiagnosticService.update({episode_id:$stateParams.episodeId}, $scope.diagnostic, onUpdateFinished);
            } else {            	
            	DiagnosticService.save({episode_id:$stateParams.episodeId}, $scope.diagnostic, onSaveFinished);
            }
        };      
        
        $scope.loadDiagnostic = function(diagnostic){        	       	
        	$scope.diagnostic = diagnostic;
        };
    });

}(window.angular));
