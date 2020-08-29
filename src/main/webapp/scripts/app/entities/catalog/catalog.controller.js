(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('CatalogController', function ($scope, $rootScope, $state, $stateParams, CatalogResource) {
        $scope.catalogs = [];
        $scope.page = 1;
        $scope.per_page = 20;
        $scope.loadAll = function (id) {
            CatalogResource.query({tab_id:$stateParams.tabId, page: $scope.page, per_page: $scope.per_page}, function(result) {
                $scope.catalogs= result;
            });
        };       
      
        
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (code) {
            CatalogResource.get({tab_id:$stateParams.tabId, id: code}, function(result) {
                $scope.catalog = result;
                $('#deleteCatalogConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (code) {
            CatalogResource.delete({tab_id:$stateParams.tabId, id: code},
                function () {
                    $scope.loadAll();
                    $('#deleteCatalogConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.catalog = {catalogId: null, description: null};
        };
        
        $scope.back = function(){
        	$state.go('tabla', null, { reload: true });
        }
    });
}(window.angular));