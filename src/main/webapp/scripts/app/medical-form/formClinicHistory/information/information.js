(function (angular) {
    'use strict';
    angular.module('fimepedApp')
        .config(function ($stateProvider) {
            $stateProvider
            .state('fichas.form.information', {                	
                parent: 'fichas.form',                    
                url: '/person',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.form.title'
                },
                views: {
                    'content.form': {
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/information/information.html',
                        controller: 'InformationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('formClinicHistory');                              
                            return $translate.refresh();
                        }]
                }
            });
        });

}(window.angular));