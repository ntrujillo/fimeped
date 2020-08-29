(function(angular) {
	'use strict';

	angular.module('fimepedApp').controller('ScheduledDatesController',
			function($scope,$state, ReservationResource,DateUtils, ParseLinks, ClinicHistoryPerson) {
				$scope.reservations = [];
				$scope.page = 1;
				$scope.per_page = 5;
				
				$scope.loadAll = function() {
					ReservationResource.query({page: $scope.page, per_page: $scope.per_page, sort:'turn', 
						date :DateUtils.convertLocaleDateToServer(new Date()).toISOString().substring(0,10)}, function(result, headers) {
		                $scope.links = ParseLinks.parse(headers('link'));
		                $scope.reservations = result;
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
				
				$scope.openClinicHistory = function(reservation){
					
					ClinicHistoryPerson.query({person_id:reservation.person.id}, function(result) {		               
		                console.log(result);
		                $state.go('fichas.form', {historyId:result[0].id}, { reload: true });
		            });
					
				}
			});
}(window.angular));