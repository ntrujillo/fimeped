'use strict';

angular.module('fimepedApp')
    .controller('FamilyHistoryController', function ($scope, FamilyHistory, ParseLinks) {
        $scope.familyHistorys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            FamilyHistory.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.familyHistorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            FamilyHistory.get({id: id}, function(result) {
                $scope.familyHistory = result;
                $('#deleteFamilyHistoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            FamilyHistory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFamilyHistoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.familyHistory = {date: null, description: null, illness: null, id: null};
        };
    });
