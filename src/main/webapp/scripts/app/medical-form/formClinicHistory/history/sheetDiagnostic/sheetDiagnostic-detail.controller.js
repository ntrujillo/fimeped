(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('SheetDiagnosticDetailController', function ($scope, $rootScope, $stateParams, entityEpisode, diagnostic, DiagnosticService) {
        $scope.diagnostic = diagnostic;
        $scope.load = function (id) {
            DiagnosticService.get({episode_id:$stateParams.episodeId, id: id}, function(result) {
                $scope.diagnostic = result;
            });
        };
        $rootScope.$on('fimepedApp:diagnosticUpdate', function(event, result) {
            $scope.diagnostic = result;
        });
    });
}(window.angular));