'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('evolutionPrescription', {
                parent: 'entity',
                url: '/evolutionPrescriptions',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.evolutionPrescription.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/evolutionPrescription/evolutionPrescriptions.html',
                        controller: 'EvolutionPrescriptionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('evolutionPrescription');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('evolutionPrescription.detail', {
                parent: 'entity',
                url: '/evolutionPrescription/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.evolutionPrescription.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/evolutionPrescription/evolutionPrescription-detail.html',
                        controller: 'EvolutionPrescriptionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('evolutionPrescription');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EvolutionPrescription', function($stateParams, EvolutionPrescription) {
                        return EvolutionPrescription.get({id : $stateParams.id});
                    }]
                }
            })
            .state('evolutionPrescription.new', {
                parent: 'evolutionPrescription',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/evolutionPrescription/evolutionPrescription-dialog.html',
                        controller: 'EvolutionPrescriptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {evolution: null, prescription: null, medicines: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('evolutionPrescription', null, { reload: true });
                    }, function() {
                        $state.go('evolutionPrescription');
                    })
                }]
            })
            .state('evolutionPrescription.edit', {
                parent: 'evolutionPrescription',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/evolutionPrescription/evolutionPrescription-dialog.html',
                        controller: 'EvolutionPrescriptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['EvolutionPrescription', function(EvolutionPrescription) {
                                return EvolutionPrescription.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('evolutionPrescription', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
