'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('clinicHistoryAddInf', {
                parent: 'entity',
                url: '/clinicHistoryAddInfs',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.clinicHistoryAddInf.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/clinicHistoryAddInf/clinicHistoryAddInfs.html',
                        controller: 'ClinicHistoryAddInfController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('clinicHistoryAddInf');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('clinicHistoryAddInf.detail', {
                parent: 'entity',
                url: '/clinicHistoryAddInf/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.clinicHistoryAddInf.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/clinicHistoryAddInf/clinicHistoryAddInf-detail.html',
                        controller: 'ClinicHistoryAddInfDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('clinicHistoryAddInf');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ClinicHistoryAddInf', function($stateParams, ClinicHistoryAddInf) {
                        return ClinicHistoryAddInf.get({id : $stateParams.id});
                    }]
                }
            })
            .state('clinicHistoryAddInf.new', {
                parent: 'clinicHistoryAddInf',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/clinicHistoryAddInf/clinicHistoryAddInf-dialog.html',
                        controller: 'ClinicHistoryAddInfDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {menarcheAge: null, menopauseAge: null, cycles: null, sexuallyActive: null, feat: null, deliveries: null, abortions: null, caesareans: null, livingChildren: null, lastMenarche: null, lastDelivery: null, lastCitology: null, biopsy: null, protectionMethod: null, hormoneTerapy: null, colposcopy: null, mammografhy: null, borrar: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('clinicHistoryAddInf', null, { reload: true });
                    }, function() {
                        $state.go('clinicHistoryAddInf');
                    })
                }]
            })
            .state('clinicHistoryAddInf.edit', {
                parent: 'clinicHistoryAddInf',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/clinicHistoryAddInf/clinicHistoryAddInf-dialog.html',
                        controller: 'ClinicHistoryAddInfDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ClinicHistoryAddInf', function(ClinicHistoryAddInf) {
                                return ClinicHistoryAddInf.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('clinicHistoryAddInf', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
