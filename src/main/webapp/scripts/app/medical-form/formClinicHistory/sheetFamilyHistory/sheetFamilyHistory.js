(function(angular){'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider.state('fichas.form.family_history', {                	
            parent: 'fichas.form',                    
            url: '/family-history/{id}',
            data: {
                roles: ['ROLE_USER'],
                pageTitle: 'fimepedApp.form.title'
            },
            views: {                    	
                'content.form': {
                    templateUrl: 'scripts/app/medical-form/formClinicHistory/sheetFamilyHistory/sheetFamilyHistorys.html',
                    controller: 'SheetFamilyHistoryController'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader',
                                         function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('familyHistory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    	}
                 ]
            }
        }).state('fichas.form.family_history.new', {
                parent: 'fichas.form.family_history',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/sheetFamilyHistory/sheetFamilyHistory-dialog.html',
                        controller: 'SheetFamilyHistoryDialogController',
                        size: 'lg',
                        backdrop : 'static',
                        resolve: {                       	
                        	familyHistory: function () {
                                return {date: null, description: null, illness: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.family_history', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('fichas.form.family_history.edit', {
                parent: 'fichas.form.family_history',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/sheetFamilyHistory/sheetFamilyHistory-dialog.html',
                        controller: 'SheetFamilyHistoryDialogController',
                        size: 'lg',
                        backdrop : 'static',
                        resolve: {                        	
                            familyHistory: ['FamilyHistoryService', function(FamilyHistoryService) {
                                return FamilyHistoryService.get({history_id:$stateParams.historyId, id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fichas.form.family_history', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
}(window.angular));
