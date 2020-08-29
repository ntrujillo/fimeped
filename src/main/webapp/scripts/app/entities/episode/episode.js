'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('episode', {
                parent: 'entity',
                url: '/episodes',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.episode.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/episode/episodes.html',
                        controller: 'EpisodeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('episode');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('episode.detail', {
                parent: 'entity',
                url: '/episode/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.episode.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/episode/episode-detail.html',
                        controller: 'EpisodeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('episode');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Episode', function($stateParams, Episode) {
                        return Episode.get({id : $stateParams.id});
                    }]
                }
            })
            .state('episode.new', {
                parent: 'episode',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/episode/episode-dialog.html',
                        controller: 'EpisodeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {date: null, description: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('episode', null, { reload: true });
                    }, function() {
                        $state.go('episode');
                    })
                }]
            })
            .state('episode.edit', {
                parent: 'episode',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/episode/episode-dialog.html',
                        controller: 'EpisodeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Episode', function(Episode) {
                                return Episode.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('episode', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
