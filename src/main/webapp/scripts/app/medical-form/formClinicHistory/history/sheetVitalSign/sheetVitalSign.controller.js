'use strict';

angular.module('fimepedApp')
    .controller('SheetVitalSignController', function ($scope,$stateParams, VitalSignService, ParseLinks) {
    	var onSaveFinished = function (result) {
            $scope.clear();
            $scope.vitalSigns.push(result);
        };
  	
        var onUpdateFinished = function (result) {
            $scope.clear();
            
        };
        $scope.vitalSigns = [];
        $scope.page = 1;
        $scope.loadAll = function() {
        	VitalSignService.query({episode_id : $stateParams.episodeId, page: $scope.page, per_page: 20}, function(result, headers) {
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
        	VitalSignService.get({episode_id : $stateParams.episodeId, id: id}, function(result) {
                $scope.vitalSign = result;
                $('#deleteVitalSignConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
        	VitalSignService.delete({episode_id : $stateParams.episodeId, id: id},
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
        
        $scope.save = function () {
            if ($scope.vitalSign.id != null) {
            	VitalSignService.update({episode_id:$stateParams.episodeId}, $scope.vitalSign, onUpdateFinished);
            } else {            	
            	VitalSignService.save({episode_id:$stateParams.episodeId}, $scope.vitalSign, onSaveFinished);
            }
        };      
        
        $scope.loadVitalSign = function(vitalSign){        	       	
        	$scope.vitalSign = vitalSign;
        };
    });
