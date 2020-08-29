'use strict';

angular.module('fimepedApp')
    .controller('CurrentIllnessController', function ($scope, CurrentIllness, ParseLinks) {
        $scope.currentIllnesss = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            CurrentIllness.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.currentIllnesss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            CurrentIllness.get({id: id}, function(result) {
                $scope.currentIllness = result;
                $('#deleteCurrentIllnessConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            CurrentIllness.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCurrentIllnessConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.currentIllness = {description: null, id: null};
        };
    });
