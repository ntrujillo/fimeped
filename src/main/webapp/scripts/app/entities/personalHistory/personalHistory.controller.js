'use strict';

angular.module('fimepedApp')
    .controller('PersonalHistoryController', function ($scope, PersonalHistory, ParseLinks) {
        $scope.personalHistorys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            PersonalHistory.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.personalHistorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PersonalHistory.get({id: id}, function(result) {
                $scope.personalHistory = result;
                $('#deletePersonalHistoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PersonalHistory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePersonalHistoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.personalHistory = {date: null, description: null, illness: null, id: null};
        };
    });
