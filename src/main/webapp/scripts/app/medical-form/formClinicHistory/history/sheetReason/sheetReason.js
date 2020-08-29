(function(angular){
		'use strict';
angular.module('fimepedApp')
    .config(function ($stateProvider) {    	
        $stateProvider.state('fichas.form.history.reason', {
            parent: 'fichas.form.history',
            url: '/reason',
            data: {
                roles: ['ROLE_USER'],
                pageTitle: 'fimepedApp.reason.title'
            },
            views: {
                'content.sheet': {
                    templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetReason/sheetReason.html',
                    controller: 'SheetReasonController'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('reason');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }], entityEpisode : ['$stateParams',function($stateParams){
                	return {id:$stateParams.episodeId}
                }]
            }
        }).state('fichas.form.history.reason.new', {
                parent: 'fichas.form.history.reason',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetReason/sheetReason-dialog.html',
                        controller: 'SheetReasonDialogController',
                        size: 'lg',
                        resolve: {
                        	entityEpisode: function () {
                                return {id:$stateParams.episodeId}
                            },
                            reason: function () {
                                return {id: null,  description: null, episode:{id:$stateParams.episodeId}};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.reason', {}, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            }).state('fichas.form.history.reason.edit', {
                parent: 'fichas.form.history.reason',
                url: '/{reasonId}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetReason/sheetReason-dialog.html',
                        controller: 'SheetReasonDialogController',
                        size: 'lg',
                        resolve: {
                        	entityEpisode: function () {
                                return {id:$stateParams.episodeId}
                            },
                            reason: ['ReasonService', function(ReasonService) {
                                return ReasonService.get({episode_id:$stateParams.episodeId, id : $stateParams.reasonId});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.reason', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
}(window.angular));
