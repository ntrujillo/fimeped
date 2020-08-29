'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('vitalSign', {
                parent: 'entity',
                url: '/vitalSigns',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.vitalSign.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vitalSign/vitalSigns.html',
                        controller: 'VitalSignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vitalSign');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('vitalSign.detail', {
                parent: 'entity',
                url: '/vitalSign/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.vitalSign.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vitalSign/vitalSign-detail.html',
                        controller: 'VitalSignDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vitalSign');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'VitalSign', function($stateParams, VitalSign) {
                        return VitalSign.get({id : $stateParams.id});
                    }]
                }
            })
            .state('vitalSign.new', {
                parent: 'vitalSign',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vitalSign/vitalSign-dialog.html',
                        controller: 'VitalSignDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {bloodPressure: null, heartRate: null, breathingFrecuency: null, oralTemperature: null, axillaryTemperature: null, weight: null, size: null, headCircumference: null, bodyMass: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('vitalSign', null, { reload: true });
                    }, function() {
                        $state.go('vitalSign');
                    })
                }]
            })
            .state('vitalSign.edit', {
                parent: 'vitalSign',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vitalSign/vitalSign-dialog.html',
                        controller: 'VitalSignDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['VitalSign', function(VitalSign) {
                                return VitalSign.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('vitalSign', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
