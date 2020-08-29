(function(){
	'use strict';


angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('provincia', {
                parent: 'entity',
                url: '/provincias',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.provincia.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/provincia/provincias.html',
                        controller: 'ProvinciaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('provincia');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('provincia.detail', {
                parent: 'entity',
                url: '/provincia/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.provincia.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/provincia/provincia-detail.html',
                        controller: 'ProvinciaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('provincia');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProvinciaResource', function($stateParams, ProvinciaResource) {
                        return ProvinciaResource.get({id : $stateParams.id});
                    }]
                }
            })
            .state('provincia.new', {
                parent: 'provincia',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/provincia/provincia-dialog.html',
                        controller: 'ProvinciaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('provincia', null, { reload: true });
                    }, function() {
                        $state.go('provincia');
                    })
                }]
            })
            .state('provincia.edit', {
                parent: 'provincia',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/provincia/provincia-dialog.html',
                        controller: 'ProvinciaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProvinciaResource', function(ProvinciaResource) {
                                return ProvinciaResource.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('provincia', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
}(window.angular));