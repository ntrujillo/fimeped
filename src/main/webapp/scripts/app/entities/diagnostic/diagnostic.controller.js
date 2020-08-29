'use strict';

angular.module('fimepedApp')
    .controller('DiagnosticController', function ($scope, Diagnostic, ParseLinks) {
        $scope.diagnostics = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Diagnostic.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.diagnostics = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Diagnostic.get({id: id}, function(result) {
                $scope.diagnostic = result;
                $('#deleteDiagnosticConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Diagnostic.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDiagnosticConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.diagnostic = {description: null, cie: null, preDef: null, id: null};
        };
    });
