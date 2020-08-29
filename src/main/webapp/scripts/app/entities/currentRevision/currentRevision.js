'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('currentRevision', {
                parent: 'entity',
                url: '/currentRevisions',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.currentRevision.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/currentRevision/currentRevisions.html',
                        controller: 'CurrentRevisionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('currentRevision');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('currentRevision.detail', {
                parent: 'entity',
                url: '/currentRevision/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.currentRevision.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/currentRevision/currentRevision-detail.html',
                        controller: 'CurrentRevisionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('currentRevision');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CurrentRevision', function($stateParams, CurrentRevision) {
                        return CurrentRevision.get({id : $stateParams.id});
                    }]
                }
            })
            .state('currentRevision.new', {
                parent: 'currentRevision',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/currentRevision/currentRevision-dialog.html',
                        controller: 'CurrentRevisionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {description: null, organ: null, weNe: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('currentRevision', null, { reload: true });
                    }, function() {
                        $state.go('currentRevision');
                    })
                }]
            })
            .state('currentRevision.edit', {
                parent: 'currentRevision',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/currentRevision/currentRevision-dialog.html',
                        controller: 'CurrentRevisionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CurrentRevision', function(CurrentRevision) {
                                return CurrentRevision.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('currentRevision', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
