'use strict';

angular.module('fimepedApp')
    .controller('ClinicHistoryController', function ($scope, ClinicHistory) {
        $scope.clinicHistorys = [];
        $scope.loadAll = function() {
            ClinicHistory.query(function(result) {
               $scope.clinicHistorys = result;
            });
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
