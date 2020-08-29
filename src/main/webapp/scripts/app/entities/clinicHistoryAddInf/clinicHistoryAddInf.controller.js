'use strict';

angular.module('fimepedApp')
    .controller('ClinicHistoryAddInfController', function ($scope, ClinicHistoryAddInf) {
        $scope.clinicHistoryAddInfs = [];
        $scope.loadAll = function() {
            ClinicHistoryAddInf.query(function(result) {
               $scope.clinicHistoryAddInfs = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            ClinicHistoryAddInf.get({id: id}, function(result) {
                $scope.clinicHistoryAddInf = result;
                $('#deleteClinicHistoryAddInfConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ClinicHistoryAddInf.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteClinicHistoryAddInfConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.clinicHistoryAddInf = {menarcheAge: null, menopauseAge: null, cycles: null, sexuallyActive: null, feat: null, deliveries: null, abortions: null, caesareans: null, livingChildren: null, lastMenarche: null, lastDelivery: null, lastCitology: null, biopsy: null, protectionMethod: null, hormoneTerapy: null, colposcopy: null, mammografhy: null, borrar: null, id: null};
        };
    });
