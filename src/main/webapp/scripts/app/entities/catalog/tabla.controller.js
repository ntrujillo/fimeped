(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('TablaController', function ($scope, TablaResource, ParseLinks) {
        $scope.tablas = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            TablaResource.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tablas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TablaResource.get({id: id}, function(result) {
                $scope.tabla = result;
                $('#deleteTablaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TablaResource.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTablaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.tabla = {tabId: null, description: null};
        };
    });
}(window.angular));