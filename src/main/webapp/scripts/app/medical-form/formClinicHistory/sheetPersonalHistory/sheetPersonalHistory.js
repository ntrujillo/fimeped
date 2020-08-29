(function(angular){
		'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {    	
        $stateProvider.state('fichas.form.personal_history', {
            parent: 'fichas.form',
            url: '/personalHistorys',
            data: {
                roles: ['ROLE_USER'],
                pageTitle: 'fimepedApp.personalHistory.title'
            },
            views: {
                'content.form': {
                    templateUrl: 'scripts/app/medical-form/formClinicHistory/sheetPersonalHistory/sheetPersonalHistorys.html',
                    controller: 'SheetPersonalHistoryController'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personalHistory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }            
        }).state('fichas.form.personal_history.new', {
                parent: 'fichas.form.personal_history',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal',function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/sheetPersonalHistory/sheetPersonalHistory-dialog.html',
                        controller: 'SheetPersonalHistoryDialogController',
                        size: 'lg',
                        backdrop : 'static',
                        resolve: {
                        	
                            personalHistory: function () {
                                return {date: null, description: null, illness: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.personal_history', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })            
            .state('fichas.form.personal_history.edit', {
                parent: 'fichas.form.personal_history',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/sheetPersonalHistory/sheetPersonalHistory-dialog.html',
                        controller: 'SheetPersonalHistoryDialogController',
                        size: 'lg',
                        backdrop : 'static',
                        resolve: {                        	
                            personalHistory: ['PersonalHistoryService', function(PersonalHistoryService) {
                                return PersonalHistoryService.get({history_id:$stateParams.historyId,id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.personal_history', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
}(window.angular));
