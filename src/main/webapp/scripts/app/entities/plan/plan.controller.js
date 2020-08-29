'use strict';

angular.module('fimepedApp')
    .controller('PlanController', function ($scope, Plan, ParseLinks) {
        $scope.plans = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Plan.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.plans = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Plan.get({id: id}, function(result) {
                $scope.plan = result;
                $('#deletePlanConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Plan.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePlanConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.plan = {description: null, id: null};
        };
    });
