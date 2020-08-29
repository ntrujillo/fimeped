'use strict';

angular.module('fimepedApp')
    .controller('VitalSignController', function ($scope, VitalSign, ParseLinks) {
        $scope.vitalSigns = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            VitalSign.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.vitalSigns = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            VitalSign.get({id: id}, function(result) {
                $scope.vitalSign = result;
                $('#deleteVitalSignConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            VitalSign.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteVitalSignConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.vitalSign = {bloodPressure: null, heartRate: null, breathingFrecuency: null, oralTemperature: null, axillaryTemperature: null, weight: null, size: null, headCircumference: null, bodyMass: null, id: null};
        };
    });
