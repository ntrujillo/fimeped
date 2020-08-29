(function(angular){
	'use strict';
	angular.module('fimepedApp').controller('FormClinicHistoryController',function($scope,$rootScope,entityClinicHistory,ClinicHistory){
		
		$rootScope.clinicHistory = entityClinicHistory;				

	});
	
}(window.angular));