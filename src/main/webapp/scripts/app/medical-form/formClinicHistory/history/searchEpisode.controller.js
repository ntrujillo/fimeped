(function(angular) {
	'use strict';

	angular.module('fimepedApp').controller('SearchEpisodeController',			
			function($scope,$modalInstance,clinicHistoryId, DateUtils, EpisodeService, ParseLinks) {
				$scope.clinicHistorys = [];
				$scope.page = 1;
				$scope.per_page = 5;
				
				
				$scope.loadAll = function() {
					EpisodeService.query({ history_id:clinicHistoryId, page: $scope.page, per_page: $scope.per_page, sort:"-date"}, function(result, headers) {
		                $scope.links = ParseLinks.parse(headers('link'));
		                $scope.episodes = result;
		            });
		        };
		        $scope.loadPage = function(page) {
		            $scope.page = page;
		            $scope.loadAll();
		        };
		        $scope.loadAll();			        

				$scope.search = function() {				
					EpisodeService.query({
							history_id:clinicHistoryId,
							page : $scope.page,
							per_page : $scope.per_page,							
							date_start : DateUtils.convertLocaleDateToServer($scope.filter.dateStart).toISOString().substring(0,10),
							date_end: DateUtils.convertLocaleDateToServer($scope.filter.dateEnd).toISOString().substring(0,10)
						}, function(result, headers) {
							$scope.links = ParseLinks.parse(headers('link'));
							$scope.episodes = result;
						});
					
				};				
				
				$scope.selectEpisode = function(episode){			
					$modalInstance.close(episode);
				}
				
				 $scope.clear = function() {
			            $modalInstance.dismiss('cancel');
			     };
			});
}(window.angular));