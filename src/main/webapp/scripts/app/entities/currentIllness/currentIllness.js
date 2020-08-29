'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('currentIllness', {
                parent: 'entity',
                url: '/currentIllnesss',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.currentIllness.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/currentIllness/currentIllnesss.html',
                        controller: 'CurrentIllnessController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('currentIllness');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('currentIllness.detail', {
                parent: 'entity',
                url: '/currentIllness/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.currentIllness.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/currentIllness/currentIllness-detail.html',
                        controller: 'CurrentIllnessDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('currentIllness');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CurrentIllness', function($stateParams, CurrentIllness) {
                        return CurrentIllness.get({id : $stateParams.id});
                    }]
                }
            })
            .state('currentIllness.new', {
                parent: 'currentIllness',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/currentIllness/currentIllness-dialog.html',
                        controller: 'CurrentIllnessDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {description: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('currentIllness', null, { reload: true });
                    }, function() {
                        $state.go('currentIllness');
                    })
                }]
            })
            .state('currentIllness.edit', {
                parent: 'currentIllness',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/currentIllness/currentIllness-dialog.html',
                        controller: 'CurrentIllnessDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CurrentIllness', function(CurrentIllness) {
                                return CurrentIllness.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('currentIllness', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
