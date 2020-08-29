(function(angular) {
	'use strict';
	angular.module('fimepedApp').controller('HistoryController',
			function($scope, $rootScope,$state,$stateParams, $modal,entityEpisode, EpisodeService, Principal,Commons) {	
		
				$scope.episode = entityEpisode;
				$rootScope.episode = entityEpisode;
	
				$scope.areas = [ {					
					"title" : 'fimepedApp.sheet.lbl_reason',
					"state" :"fichas.form.history.reason({episodeId:episode.id})"
				}, {
					"title" : 'fimepedApp.sheet.lbl_current_illness',
					"state" :"fichas.form.history.current_illness({episodeId:episode.id})"
				}, {
					"title" : 'fimepedApp.sheet.lbl_current_revision',
					"state" :"fichas.form.history.current_revision({episodeId:episode.id})"
				} , {
					"title" : 'fimepedApp.sheet.lbl_physical_exam',
					"state" :"fichas.form.history.physical_exam({episodeId:episode.id})"
				} , {
					"title" : 'fimepedApp.sheet.lbl_diagnostic',
					"state" :"fichas.form.history.diagnostic({episodeId:episode.id})"
				} , {
					"title" : 'fimepedApp.sheet.lbl_plan',
					"state" :"fichas.form.history.plan({episodeId:episode.id})"
				} , {
					"title" : 'fimepedApp.sheet.lbl_evolution_prescription',
					"state" :"fichas.form.history.evolution_prescription({episodeId:episode.id})"
				} ];				
				 Principal.identity().then(function(account) {
			            $scope.account = account;
			            $scope.isAuthenticated = Principal.isAuthenticated;
			      });				
				$scope.createEpisode = function(){				
					$scope.episode.id = null;					
					$scope.episode.user = $scope.account.login;		
					$scope.episode = EpisodeService.save({history_id:$stateParams.historyId},$scope.episode);
									
				};					
					
				
				$scope.openReport = function(){
					Commons.openReport('/report/helloReport1?report.type=PDF&report.episodeId=7','_blank');
				}				
				
			});
}(window.angular));