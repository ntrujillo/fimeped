'use strict';

angular.module('fimepedApp')
    .controller('EvolutionPrescriptionController', function ($scope, EvolutionPrescription, ParseLinks) {
        $scope.evolutionPrescriptions = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            EvolutionPrescription.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.evolutionPrescriptions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            EvolutionPrescription.get({id: id}, function(result) {
                $scope.evolutionPrescription = result;
                $('#deleteEvolutionPrescriptionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            EvolutionPrescription.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEvolutionPrescriptionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.evolutionPrescription = {evolution: null, prescription: null, medicines: null, id: null};
        };
    });
