'use strict';

angular.module('fimepedApp')
    .controller('ReasonController', function ($scope, Reason, ParseLinks) {
        $scope.reasons = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Reason.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.reasons = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Reason.get({id: id}, function(result) {
                $scope.reason = result;
                $('#deleteReasonConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Reason.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteReasonConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.reason = {description: null, id: null};
        };
    });
