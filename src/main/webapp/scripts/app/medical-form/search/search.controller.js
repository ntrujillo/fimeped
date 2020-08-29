(function(angular) {
	'use strict';

	angular.module('fimepedApp').controller('SearchClinicHistoryController',
			function($scope, ClinicHistory, ParseLinks) {
				$scope.clinicHistorys = [];
				$scope.page = 1;
				$scope.per_page = 5;
				
				$scope.loadAll = function() {
					ClinicHistory.query({page: $scope.page, per_page: $scope.per_page}, function(result, headers) {
		                $scope.links = ParseLinks.parse(headers('link'));
		                $scope.clinicHistorys = result;
		            });
		        };
		        $scope.loadPage = function(page) {
		            $scope.page = page;
		            $scope.loadAll();
		        };
		        $scope.loadAll();			

				$scope.search = function() {

					if ($scope.id && $scope.id != '') {
						
						ClinicHistory.get({id : $scope.id}, function(result){
							$scope.clinicHistorys = [result];
						});

					} else {
						ClinicHistory.query({
							page : $scope.page,
							per_page : $scope.per_page,
							firstName : $scope.firstName,
							secondName : $scope.secondName,
							paternalSurname : $scope.paternalSurname,
							maternalSurname : $scope.maternalSurname,
							cedula : $scope.cedula
						}, function(result, headers) {
							$scope.links = ParseLinks.parse(headers('link'));
							$scope.clinicHistorys = result;
						});
					}
				};
			});
}(window.angular));