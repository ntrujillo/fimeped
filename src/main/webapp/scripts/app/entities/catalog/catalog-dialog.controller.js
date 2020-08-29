(function(angular){
	'use strict';

angular.module('fimepedApp').controller('CatalogDialogController',
    ['$scope','$rootScope', '$stateParams', '$modalInstance', 'catalog', 'CatalogResource', 'ProvinciaResource',
        function($scope,$rootScope,  $stateParams, $modalInstance, catalog, CatalogResource, ProvinciaResource) {

        $scope.catalog = catalog;       
        
        $rootScope.$on('fimepedApp:catalogUpdate', function(event, result) {
            $scope.catalog = result;
        });
        

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:catalogUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.catalog.code != null) {
                CatalogResource.update({tab_id:$stateParams.tabId}, $scope.catalog, onSaveFinished);
            } else {
                CatalogResource.save({tab_id:$stateParams.tabId}, $scope.catalog, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
}(window.angular));