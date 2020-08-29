'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('personalHistory', {
                parent: 'entity',
                url: '/personalHistorys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.personalHistory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/personalHistory/personalHistorys.html',
                        controller: 'PersonalHistoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('personalHistory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('personalHistory.detail', {
                parent: 'entity',
                url: '/personalHistory/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.personalHistory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/personalHistory/personalHistory-detail.html',
                        controller: 'PersonalHistoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('personalHistory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PersonalHistory', function($stateParams, PersonalHistory) {
                        return PersonalHistory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('personalHistory.new', {
                parent: 'personalHistory',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/personalHistory/personalHistory-dialog.html',
                        controller: 'PersonalHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {date: null, description: null, illness: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('personalHistory', null, { reload: true });
                    }, function() {
                        $state.go('personalHistory');
                    })
                }]
            })
            .state('personalHistory.edit', {
                parent: 'personalHistory',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/personalHistory/personalHistory-dialog.html',
                        controller: 'PersonalHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PersonalHistory', function(PersonalHistory) {
                                return PersonalHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('personalHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
