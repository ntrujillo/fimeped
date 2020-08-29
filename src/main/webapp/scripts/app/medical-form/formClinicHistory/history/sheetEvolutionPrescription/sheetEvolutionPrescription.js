'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fichas.form.history.evolution_prescription', {
                parent: 'fichas.form.history',
                url: '/evolution_prescription',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.evolutionPrescription.home.title'
                },
                views: {
                    'content.sheet': {
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetEvolutionPrescription/sheetEvolutionPrescriptions.html',
                        controller: 'SheetEvolutionPrescriptionController'
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
            .state('fichas.form.history.evolution_prescription.new', {
                parent: 'fichas.form.history.evolution_prescription',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetEvolutionPrescription/sheetEvolutionPrescription-dialog.html',
                        controller: 'SheetEvolutionPrescriptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {evolution: null, prescription: null, medicines: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.evolution_prescription', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('fichas.form.history.evolution_prescription.edit', {
                parent: 'fichas.form.history.evolution_prescription',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetEvolutionPrescription/sheetEvolutionPrescription-dialog.html',
                        controller: 'SheetEvolutionPrescriptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['EvolutionPrescriptionService', function(EvolutionPrescriptionService) {
                                return EvolutionPrescriptionService.get({episode_id:$stateParams.episodeId, id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.evolution_prescription', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
