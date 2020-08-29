(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('SearchCieController', function ($scope,$modalInstance, CatalogCie10, ParseLinks) {
        $scope.catalogCie = [];
        $scope.page = 1;
        $scope.loadAll = function() {
        	CatalogCie10.query({page: $scope.page, per_page: 10  , code:$scope.code, description:$scope.description}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.catalogCie = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();          

        $scope.selectCie = function(icd10){			
			$modalInstance.close(icd10);
		}
		
        
        $scope.clear = function () {
        	$modalInstance.dismiss('cancel');
        };
    });
}(window.angular));
