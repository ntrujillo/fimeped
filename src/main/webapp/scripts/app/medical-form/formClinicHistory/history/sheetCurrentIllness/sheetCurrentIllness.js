'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fichas.form.history.current_illness', {
                parent: 'fichas.form.history',
                url: '/current_illness',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.currentIllness.home.title'
                },
                views: {
                    'content.sheet': {
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetCurrentIllness/sheetCurrentIllness.html',
                        controller: 'SheetCurrentIllnessController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('currentIllness');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            }).state('fichas.form.history.current_illness.new', {
                parent: 'fichas.form.history.current_illness',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetCurrentIllness/sheetCurrentIllness-dialog.html',
                        controller: 'SheetCurrentIllnessDialogController',
                        size: 'lg',
                        resolve: {                        	
                            currentIllness: function () {
                                return {id: null, description: null, episode : {id:$stateParams.episodeId} };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.current_illness', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            }).state('fichas.form.history.current_illness.edit', {
                parent: 'fichas.form.history.current_illness',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetCurrentIllness/sheetCurrentIllness-dialog.html',
                        controller: 'SheetCurrentIllnessDialogController',
                        size: 'lg',
                        resolve: {                        	
                            currentIllness: ['CurrentIllness', function(CurrentIllnessService) {
                                return CurrentIllnessService.get({episode_id:$stateParams.episodeId, id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.current_illness', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
