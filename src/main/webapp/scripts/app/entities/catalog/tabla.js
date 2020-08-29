(function(angular){
	'use strict';


angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tabla', {
                parent: 'entity',
                url: '/catalogs',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.catalog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/catalog/tablas.html',
                        controller: 'TablaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('catalog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })            
            .state('tabla.new', {
                parent: 'tabla',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/catalog/tabla-dialog.html',
                        controller: 'TablaDialogController',
                        size: 'lg',
                        resolve: {
                            tabla: function () {
                                return {tabId: null, description: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tabla', null, { reload: true });
                    }, function() {
                        $state.go('tabla');
                    })
                }]
            })
            .state('tabla.edit', {
                parent: 'tabla',
                url: '/{tabId}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/catalog/tabla-dialog.html',
                        controller: 'TablaDialogController',
                        size: 'lg',
                        resolve: {
                            tabla: ['TablaResource', function(TablaResource) {
                                return TablaResource.get({id : $stateParams.tabId});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tabla', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            }).state('tabla.catalog', {
                parent: 'tabla',
                url: '/{tabId}/records',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.catalog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/catalog/catalogs.html',
                        controller: 'CatalogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('catalog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })            
            .state('tabla.catalog.new', {
                parent: 'tabla.catalog',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/catalog/catalog-dialog.html',
                        controller: 'CatalogDialogController',
                        size: 'lg',
                        resolve: {
                            catalog: function () {
                                return {code:null, value: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tabla.catalog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('tabla.catalog.edit', {
                parent: 'tabla.catalog',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/catalog/catalog-dialog.html',
                        controller: 'CatalogDialogController',
                        size: 'lg',
                        resolve: {
                            catalog: ['CatalogResource', function(CatalogResource) {
                                return CatalogResource.get({tab_id : $stateParams.tabId, id:$stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tabla.catalog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
}(window.angular));