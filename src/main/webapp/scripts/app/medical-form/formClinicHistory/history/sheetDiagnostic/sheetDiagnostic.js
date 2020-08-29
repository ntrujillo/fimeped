(function(angular){
	'use strict'
	
	angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fichas.form.history.diagnostic', {
                parent: 'fichas.form.history',
                url: '/diagnostic',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.diagnostic.home.title'
                },
                views: {
                    'content.sheet': {
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetDiagnostic/sheetDiagnostic.html',
                        controller: 'SheetDiagnosticController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('diagnostic');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]              
                }
            }).state('fichas.form.history.diagnostic.new', {
                parent: 'fichas.form.history.diagnostic',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetDiagnostic/sheetDiagnostic-dialog.html',
                        controller: 'SheetDiagnosticDialogController',
                        size: 'lg',
                        resolve: {
                            diagnostic: function () {
                                return {id: null,  description: null, cie:null, preDef: null, episode:{id:$stateParams.episodeId}};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.diagnostic', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            }).state('fichas.form.history.diagnostic.edit', {
                parent: 'fichas.form.history.diagnostic',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetDiagnostic/sheetDiagnostic-dialog.html',
                        controller: 'SheetDiagnosticDialogController',
                        size: 'lg',
                        resolve: {                        	
                            diagnostic: ['DiagnosticService', function(DiagnosticService) {
                                return DiagnosticService.get({episode_id:$stateParams.episodeId, id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.diagnostic', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
	
}(window.angular));