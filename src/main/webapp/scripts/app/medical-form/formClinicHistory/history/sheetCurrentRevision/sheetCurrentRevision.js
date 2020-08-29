'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fichas.form.history.current_revision', {
                parent: 'fichas.form.history',
                url: '/current_revision',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.currentRevision.home.title'
                },
                views: {
                    'content.sheet': {
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetCurrentRevision/sheetCurrentRevision.html',
                        controller: 'SheetCurrentRevisionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('currentRevision');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            }).state('fichas.form.history.current_revision.new', {
                parent: 'fichas.form.history.current_revision',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetCurrentRevision/sheetCurrentRevision-dialog.html',
                        controller: 'SheetCurrentRevisionDialogController',
                        size: 'lg',
                        resolve:  {                        	
                            currentRevision: function () {
                                return {id: null, description: null, organ:null, wene:null, episode : {id:$stateParams.episodeId} };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.current_revision', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('fichas.form.history.current_revision.edit', {
                parent: 'fichas.form.history.current_revision',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetCurrentRevision/sheetCurrentRevision-dialog.html',
                        controller: 'SheetCurrentRevisionDialogController',
                        size: 'lg',
                        resolve: {
                        	
                            currentRevision: ['CurrentRevisionService', function(CurrentRevisionService) {
                                return CurrentRevisionService.get({episode_id:$stateParams.episodeId, id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.current_revision', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
