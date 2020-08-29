(function(angular){
	'use strict';

angular.module('fimepedApp').controller('SheetDiagnosticDialogController',
    ['$scope', '$stateParams', '$modal', '$modalInstance', 'entityEpisode', 'diagnostic', 'DiagnosticService', 'Catalog',
        function($scope, $stateParams,$modal, $modalInstance, entityEpisode, diagnostic, DiagnosticService, Catalog) {

        $scope.diagnostic = diagnostic;       
        $scope.load = function(id) {
        	DiagnosticService.get({episode_id:entityEpisode.id, id : id}, function(result) {
                $scope.diagnostic = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:diagnosticUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.diagnostic.id != null) {
            	DiagnosticService.update({episode_id:entityEpisode.id}, $scope.diagnostic, onSaveFinished);
            } else {
            	DiagnosticService.save({episode_id:entityEpisode.id}, $scope.diagnostic, onSaveFinished);
            }
        };
        
        $scope.loadCatalogPreDef = function(){
        	
        	Catalog.get({tabla:'cl_002_8'},function(result){
        		$scope.catalogPreDef = result;        	
        	});
        };        
        
        $scope.loadCatalogPreDef();
        
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
        }

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
}(window.angular));