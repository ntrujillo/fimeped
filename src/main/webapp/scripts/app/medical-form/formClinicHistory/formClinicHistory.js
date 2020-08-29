(function (angular) {
    'use strict';
    angular.module('fimepedApp')    
        .config(function ($stateProvider) {
        	$stateProvider.state('fichas.form', {                	
                parent: 'fichas',                    
                url: '/clinic-history/{historyId}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.form.title'
                },               
                views: {                    	
                    'content@': {
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/formClinicHistory.html',
                        controller: 'FormClinicHistoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader',
                                             function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('formClinicHistory');
                            $translatePartialLoader.addPart('global');
                            return $translate.refresh();
                        	}
                     ], entityClinicHistory: ['$stateParams', 'ClinicHistory', function($stateParams, ClinicHistory) {
                         return ClinicHistory.get({id : $stateParams.historyId});
                     }]
                },onExit:['$rootScope', function($rootScope){
                	$rootScope.episode = null;                	
                }]
            })
    });
}(window.angular));