'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('clinicHistory', {
                parent: 'entity',
                url: '/clinicHistorys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.clinicHistory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/clinicHistory/clinicHistorys.html',
                        controller: 'ClinicHistoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('clinicHistory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('clinicHistory.detail', {
                parent: 'entity',
                url: '/clinicHistory/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.clinicHistory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/clinicHistory/clinicHistory-detail.html',
                        controller: 'ClinicHistoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('clinicHistory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ClinicHistory', function($stateParams, ClinicHistory) {
                        return ClinicHistory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('clinicHistory.new', {
                parent: 'clinicHistory',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/clinicHistory/clinicHistory-dialog.html',
                        controller: 'ClinicHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {createDate: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('clinicHistory', null, { reload: true });
                    }, function() {
                        $state.go('clinicHistory');
                    })
                }]
            })
            .state('clinicHistory.edit', {
                parent: 'clinicHistory',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/clinicHistory/clinicHistory-dialog.html',
                        controller: 'ClinicHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ClinicHistory', function(ClinicHistory) {
                                return ClinicHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('clinicHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
