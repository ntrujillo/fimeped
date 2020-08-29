'use strict';

angular.module('fimepedApp')
    .controller('PhysicalExamDetailController', function ($scope, $rootScope, $stateParams, entity, PhysicalExam, Episode) {
        $scope.physicalExam = entity;
        $scope.load = function (id) {
            PhysicalExam.get({id: id}, function(result) {
                $scope.physicalExam = result;
            });
        };
        $rootScope.$on('fimepedApp:physicalExamUpdate', function(event, result) {
            $scope.physicalExam = result;
        });
    });
