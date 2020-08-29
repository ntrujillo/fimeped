(function(angular){
	'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fichas.form.history.physical_exam', {
                parent: 'fichas.form.history',
                url: '/physical_exam',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.physicalExam.home.title'
                },
                views: {
                    'content.sheet': {
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetPhysicalExam/sheetPhysicalExam.html',
                        controller: 'SheetPhysicalExamController'
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
            .state('fichas.form.history.physical_exam.new', {
                parent: 'fichas.form.history.physical_exam',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetPhysicalExam/sheetPhysicalExam-dialog.html',
                        controller: 'SheetPhysicalExamDialogController',
                        size: 'lg',
                        resolve: {                        	
                            physicalExam: function () {
                                return {description: null, bodyPart: null, weNe: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.physical_exam', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('fichas.form.history.physical_exam.edit', {
                parent: 'fichas.form.history.physical_exam',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetPhysicalExam/sheetPhysicalExam-dialog.html',
                        controller: 'SheetPhysicalExamDialogController',
                        size: 'lg',
                        resolve: {entityEpisode: function () {
                            return {id:$stateParams.episodeId}
                        },
                            physicalExam: ['PhysicalExamService', function(PhysicalExamService) {
                                return PhysicalExamService.get({episode_id:$stateParams.episodeId, id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.history.physical_exam', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
}(window.angular));
