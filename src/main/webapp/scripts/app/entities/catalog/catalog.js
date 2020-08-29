(function(angular){
	'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tabla.catalog', {
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
                                return {catalogId :{
                                			tabId:null,
                                			catId:null
                                		}, 
                                		value: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tabla.catalog', null, { reload: true });
                    }, function() {
                        $state.go('catalog');
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
                                return CountryResource.get({tabId : $stateParams.tabId, id:$stateParams.id});
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