(function(angular){
	'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fichas.form.history.plan', {
                parent: 'fichas.form.history',
                url: '/plan',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.plan.home.title'
                },
                views: {
                    'content.sheet': {
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetPlan/sheetPlan.html',
                        controller: 'SheetPlanController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plan');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })           
            .state('fichas.form.history.plan.new', {
                parent: 'fichas.form.history.plan',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetPlan/sheetPlan-dialog.html',
                        controller: 'SheetPlanDialogController',
                        size: 'lg',
                        resolve: {
                            plan: function () {
                                return {id: null,  description: null, episode:{id:$stateParams.episodeId}};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.plan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('fichas.form.history.plan.edit', {
                parent: 'fichas.form.history.plan',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetPlan/sheetPlan-dialog.html',
                        controller: 'SheetPlanDialogController',
                        size: 'lg',
                        resolve: {
                            plan: ['PlanService', function(PlanService) {
                                return PlanService.get({episode_id:$stateParams.episodeId, id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.plan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
}(window.angular));