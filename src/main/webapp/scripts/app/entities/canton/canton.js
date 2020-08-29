'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('canton', {
                parent: 'entity',
                url: '/cantons',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.canton.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/canton/cantons.html',
                        controller: 'CantonController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('canton');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('canton.detail', {
                parent: 'entity',
                url: '/canton/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.canton.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/canton/canton-detail.html',
                        controller: 'CantonDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('canton');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CantonResource', function($stateParams, CantonResource) {
                        return CantonResource.get({id : $stateParams.id});
                    }]
                }
            })
            .state('canton.new', {
                parent: 'canton',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/canton/canton-dialog.html',
                        controller: 'CantonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('canton', null, { reload: true });
                    }, function() {
                        $state.go('canton');
                    })
                }]
            })
            .state('canton.edit', {
                parent: 'canton',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/canton/canton-dialog.html',
                        controller: 'CantonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CantonResource', function(CantonResource) {
                                return CantonResource.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('canton', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
