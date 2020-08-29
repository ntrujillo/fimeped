'use strict';

angular.module('fimepedApp')
    .controller('ClinicHistoryController', function ($scope, ClinicHistory, ParseLinks) {
        $scope.clinicHistorys = [];      
        $scope.page = 1;
        $scope.loadAll = function() {
            ClinicHistory.query({page: $scope.page, per_page: 5},function(result, headers) {
            	$scope.links = ParseLinks.parse(headers('link'));
            	$scope.clinicHistorys = result;
            });
        };
        
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            ClinicHistory.get({id: id}, function(result) {
                $scope.clinicHistory = result;
                $('#deleteClinicHistoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ClinicHistory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteClinicHistoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.clinicHistory = {createDate: null, id: null};
        };
    });
