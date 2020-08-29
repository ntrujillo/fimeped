(function(angular) {
	'use strict';
	angular.module('fimepedApp').controller('GraphicsController',function($scope,$stateParams, VitalSignResource, entityClinicHistory, ParseLinks, DateUtils) {		
				var onSaveFinished = function (result) {
		            $scope.clear();
		            $scope.vitalSigns.push(result);
		        };
		  	
		        var onUpdateFinished = function (result) {
		            $scope.clear();
		            
		        };
		        $scope.vitalSigns = [];
		        $scope.page = 1;
		        $scope.per_page = 5;
		        $scope.seriesGraphics = [];
		        
				
				$scope.graphics = [ {					
					"title" : 'fimepedApp.graphics.lbl_head_circunference',
					"state" :"fichas.form.graphics.head_circunference"
				}, {
					"title" : 'fimepedApp.graphics.lbl_length_height',
					"state" :"fichas.form.graphics.length_height"
				}, {
					"title" : 'fimepedApp.graphics.lbl_weight',
					"state" :"fichas.form.graphics.weight"
				} ];			
				
		        $scope.loadAll = function() {
		        	VitalSignResource.query({history_id : $stateParams.historyId, page: $scope.page, per_page: $scope.per_page, sort:"-date"}, function(result, headers) {
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
		        	VitalSignService.get({history_id : $stateParams.historyId, id: id}, function(result) {
		                $scope.vitalSign = result;
		                $('#deleteVitalSignConfirmation').modal('show');
		            });
		        };

		        $scope.confirmDelete = function (id) {
		        	VitalSignService.delete({history_id : $stateParams.historyId, id: id},
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
		            $scope.vitalSign = {bloodPressure: null, heartRate: null, breathingFrecuency: null, oralTemperature: null, axillaryTemperature: null, weight: null, size: null, headCircumference: null, bodyMass: null,age:null, id: null};
		        };
		        
		        $scope.save = function () {
		            if ($scope.vitalSign.id != null) {
		            	VitalSignResource.update({history_id:$stateParams.historyId}, $scope.vitalSign, onUpdateFinished);
		            } else {            	
		            	VitalSignResource.save({history_id:$stateParams.historyId}, $scope.vitalSign, onSaveFinished);
		            }
		        };  
		        
		        $scope.loadVitalSign = function(vitalSign){
		        	if(vitalSign.date && !(vitalSign.date instanceof Date)) {
		        		vitalSign.date = DateUtils.convertLocaleDateFromServer(vitalSign.date);
		        	}
		        	$scope.vitalSign = vitalSign;
		        }
		        
		        $scope.changeDate = function(date){
		        	$scope.vitalSign.age = DateUtils.dateDiffInMonths(entityClinicHistory.person.birthDate,date);
		        };

			});
}(window.angular));