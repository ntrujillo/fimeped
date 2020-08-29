'use strict';

angular.module('fimepedApp')
    .controller('CurrentRevisionController', function ($scope, CurrentRevision, ParseLinks) {
        $scope.currentRevisions = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            CurrentRevision.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.currentRevisions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            CurrentRevision.get({id: id}, function(result) {
                $scope.currentRevision = result;
                $('#deleteCurrentRevisionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            CurrentRevision.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCurrentRevisionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.currentRevision = {description: null, organ: null, weNe: null, id: null};
        };
    });
