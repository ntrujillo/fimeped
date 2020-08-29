(function (angular) {
    'use strict';
    angular.module('fimepedApp')
        .config(function ($stateProvider) {
        	var modal;
            $stateProvider
                .state('fichas.form.history', {                	
                    parent: 'fichas.form',
                    url: '/episode/{episodeId}',
                    data: {
                        roles: ['ROLE_USER'],                        
                        pageTitle: 'fimepedApp.search.title'
                    },                        
                    views: {
                        'content.form': {
                            templateUrl: 'scripts/app/medical-form/formClinicHistory/history/history.html',
                            controller: 'HistoryController'
                        }
                    },
                    resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {                           
                                								$translatePartialLoader.addPart('formClinicHistory');
                                								return $translate.refresh();
                            								}
                                                ],
                    	entityEpisode : ['EpisodeService','$stateParams', function(EpisodeService,$stateParams) {
                    		if($stateParams.episodeId){
                    			return EpisodeService.get({history_id:$stateParams.historyId, id : $stateParams.episodeId});
                    		}else {
                    			return {};
                    		}
                        }]
                    }
                }).state('fichas.form.history.find', {
                    parent: 'fichas.form.history',
                    url: '/find',
                    data: {
                        roles: ['ROLE_USER'],
                    },
                    onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                        $modal.open({
                            templateUrl: 'scripts/app/medical-form/formClinicHistory/history/searchEpisode.html',
                            controller: 'SearchEpisodeController',
                            size: 'lg',
                            resolve :{	                    	 
   	                    	 	clinicHistoryId : function () {
   	                    	 		return $stateParams.historyId;
   	                    	 	}   	                    	 
   	                     	}
                        }).result.then(function(result) {                        	
                            $state.go('fichas.form.history', {episodeId:result.id}, { reload: true });
                        }, function() {
                            $state.go('fichas.form.history');
                        })
                    }]
                });

        });

}(window.angular));