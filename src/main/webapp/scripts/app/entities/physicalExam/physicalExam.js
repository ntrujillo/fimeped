'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('physicalExam', {
                parent: 'entity',
                url: '/physicalExams',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.physicalExam.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/physicalExam/physicalExams.html',
                        controller: 'PhysicalExamController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('physicalExam');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('physicalExam.detail', {
                parent: 'entity',
                url: '/physicalExam/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.physicalExam.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/physicalExam/physicalExam-detail.html',
                        controller: 'PhysicalExamDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('physicalExam');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PhysicalExam', function($stateParams, PhysicalExam) {
                        return PhysicalExam.get({id : $stateParams.id});
                    }]
                }
            })
            .state('physicalExam.new', {
                parent: 'physicalExam',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/physicalExam/physicalExam-dialog.html',
                        controller: 'PhysicalExamDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {description: null, bodyPart: null, weNe: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('physicalExam', null, { reload: true });
                    }, function() {
                        $state.go('physicalExam');
                    })
                }]
            })
            .state('physicalExam.edit', {
                parent: 'physicalExam',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/physicalExam/physicalExam-dialog.html',
                        controller: 'PhysicalExamDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PhysicalExam', function(PhysicalExam) {
                                return PhysicalExam.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('physicalExam', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
