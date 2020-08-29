'use strict';

angular.module('fimepedApp')
    .controller('CurrentRevisionDetailController', function ($scope, $rootScope, $stateParams, entity, CurrentRevision, Episode) {
        $scope.currentRevision = entity;
        $scope.load = function (id) {
            CurrentRevision.get({id: id}, function(result) {
                $scope.currentRevision = result;
            });
        };
        $rootScope.$on('fimepedApp:currentRevisionUpdate', function(event, result) {
            $scope.currentRevision = result;
        });
    });
