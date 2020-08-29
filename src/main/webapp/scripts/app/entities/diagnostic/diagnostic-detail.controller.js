'use strict';

angular.module('fimepedApp')
    .controller('DiagnosticDetailController', function ($scope, $rootScope, $stateParams, entity, Diagnostic, Episode) {
        $scope.diagnostic = entity;
        $scope.load = function (id) {
            Diagnostic.get({id: id}, function(result) {
                $scope.diagnostic = result;
            });
        };
        $rootScope.$on('fimepedApp:diagnosticUpdate', function(event, result) {
            $scope.diagnostic = result;
        });
    });
