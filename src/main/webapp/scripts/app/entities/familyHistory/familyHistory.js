'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('familyHistory', {
                parent: 'entity',
                url: '/familyHistorys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.familyHistory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/familyHistory/familyHistorys.html',
                        controller: 'FamilyHistoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('familyHistory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('familyHistory.detail', {
                parent: 'entity',
                url: '/familyHistory/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.familyHistory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/familyHistory/familyHistory-detail.html',
                        controller: 'FamilyHistoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('familyHistory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'FamilyHistory', function($stateParams, FamilyHistory) {
                        return FamilyHistory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('familyHistory.new', {
                parent: 'familyHistory',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/familyHistory/familyHistory-dialog.html',
                        controller: 'FamilyHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {date: null, description: null, illness: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('familyHistory', null, { reload: true });
                    }, function() {
                        $state.go('familyHistory');
                    })
                }]
            })
            .state('familyHistory.edit', {
                parent: 'familyHistory',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/familyHistory/familyHistory-dialog.html',
                        controller: 'FamilyHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['FamilyHistory', function(FamilyHistory) {
                                return FamilyHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('familyHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
