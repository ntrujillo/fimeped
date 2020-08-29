(function(angular){
	'use strict';


angular.module('fimepedApp')
    .controller('ProvinciaController', function ($scope, ProvinciaResource, ParseLinks) {
        $scope.provincias = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            ProvinciaResource.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.provincias = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            ProvinciaResource.get({id: id}, function(result) {
                $scope.provincia = result;
                $('#deleteProvinciaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ProvinciaResource.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProvinciaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.provincia = {name: null, id: null};
        };
    });
}(window.angular));