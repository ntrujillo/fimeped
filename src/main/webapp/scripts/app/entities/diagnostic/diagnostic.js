'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('diagnostic', {
                parent: 'entity',
                url: '/diagnostics',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.diagnostic.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/diagnostic/diagnostics.html',
                        controller: 'DiagnosticController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('diagnostic');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('diagnostic.detail', {
                parent: 'entity',
                url: '/diagnostic/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.diagnostic.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/diagnostic/diagnostic-detail.html',
                        controller: 'DiagnosticDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('diagnostic');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Diagnostic', function($stateParams, Diagnostic) {
                        return Diagnostic.get({id : $stateParams.id});
                    }]
                }
            })
            .state('diagnostic.new', {
                parent: 'diagnostic',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/diagnostic/diagnostic-dialog.html',
                        controller: 'DiagnosticDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {description: null, cie: null, preDef: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('diagnostic', null, { reload: true });
                    }, function() {
                        $state.go('diagnostic');
                    })
                }]
            })
            .state('diagnostic.edit', {
                parent: 'diagnostic',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/diagnostic/diagnostic-dialog.html',
                        controller: 'DiagnosticDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Diagnostic', function(Diagnostic) {
                                return Diagnostic.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('diagnostic', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
