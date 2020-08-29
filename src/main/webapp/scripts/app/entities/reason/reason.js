'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('reason', {
                parent: 'entity',
                url: '/reasons',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.reason.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reason/reasons.html',
                        controller: 'ReasonController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reason');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('reason.detail', {
                parent: 'entity',
                url: '/reason/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.reason.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reason/reason-detail.html',
                        controller: 'ReasonDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reason');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Reason', function($stateParams, Reason) {
                        return Reason.get({id : $stateParams.id});
                    }]
                }
            })
            .state('reason.new', {
                parent: 'reason',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/reason/reason-dialog.html',
                        controller: 'ReasonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {description: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('reason', null, { reload: true });
                    }, function() {
                        $state.go('reason');
                    })
                }]
            })
            .state('reason.edit', {
                parent: 'reason',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/reason/reason-dialog.html',
                        controller: 'ReasonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Reason', function(Reason) {
                                return Reason.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('reason', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
