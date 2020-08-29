'use strict';

angular.module('fimepedApp')
    .controller('CantonController', function ($scope, CantonResource, ParseLinks) {
        $scope.cantons = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            CantonResource.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.cantons = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            CantonResource.get({id: id}, function(result) {
                $scope.canton = result;
                $('#deleteCantonConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            CantonResource.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCantonConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.canton = {name: null, id: null};
        };
    });
