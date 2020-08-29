(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('SheetPhysicalExamDetailController', function ($scope, $rootScope, $stateParams, entity, PhysicalExamService) {
        $scope.physicalExam = entity;
        $scope.load = function (id) {
            PhysicalExamService.get({episode_id:$stateParams.episodeId, id: id}, function(result) {
                $scope.physicalExam = result;
            });
        };
        $rootScope.$on('fimepedApp:physicalExamUpdate', function(event, result) {
            $scope.physicalExam = result;
        });
    });
}(window.angular));