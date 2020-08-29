(function(angular){
	'use strict';

angular.module('fimepedApp').controller('TablaDialogController',
    ['$scope','$rootScope', '$stateParams', '$modalInstance', 'tabla', 'TablaResource', 
        function($scope, $rootScope, $stateParams, $modalInstance, tabla, TablaResource) {

        $scope.tabla = tabla;      

        $rootScope.$on('fimepedApp:tablaUpdate', function(event, result) {
            $scope.tabla = result;
        });
        
        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:tablaUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.tabla.tabId != null) {
                TablaResource.update($scope.tabla, onSaveFinished);
            } else {
                TablaResource.save($scope.tabla, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
}(window.angular));