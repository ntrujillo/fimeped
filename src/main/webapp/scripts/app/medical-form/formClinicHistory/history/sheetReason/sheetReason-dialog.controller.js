(function(angular){
		'use strict';

angular.module('fimepedApp').controller('SheetReasonDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entityEpisode','reason', 'ReasonService', 
        function($scope, $stateParams, $modalInstance,entityEpisode, reason, ReasonService) {

        $scope.reason = reason;
      
        $scope.load = function(id) {
        	ReasonService.get({episode_id:entityEpisode.id,id : id}, function(result) {
                $scope.reason = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fimepedApp:reasonUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.reason.id != null) {
            	ReasonService.update({episode_id:entityEpisode.id},$scope.reason, onSaveFinished);
            } else {
            	$scope.reason.episode = entityEpisode;
            	ReasonService.save({episode_id:entityEpisode.id}, $scope.reason, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
}(window.angular));