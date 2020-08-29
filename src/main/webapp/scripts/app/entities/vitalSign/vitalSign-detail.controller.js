'use strict';

angular.module('fimepedApp')
    .controller('VitalSignDetailController', function ($scope, $rootScope, $stateParams, entity, VitalSign, Episode) {
        $scope.vitalSign = entity;
        $scope.load = function (id) {
            VitalSign.get({id: id}, function(result) {
                $scope.vitalSign = result;
            });
        };
        $rootScope.$on('fimepedApp:vitalSignUpdate', function(event, result) {
            $scope.vitalSign = result;
        });
    });
