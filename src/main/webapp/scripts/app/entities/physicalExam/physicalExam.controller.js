'use strict';

angular.module('fimepedApp')
    .controller('PhysicalExamController', function ($scope, PhysicalExam, ParseLinks) {
        $scope.physicalExams = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            PhysicalExam.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.physicalExams = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PhysicalExam.get({id: id}, function(result) {
                $scope.physicalExam = result;
                $('#deletePhysicalExamConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PhysicalExam.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePhysicalExamConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.physicalExam = {description: null, bodyPart: null, weNe: null, id: null};
        };
    });
