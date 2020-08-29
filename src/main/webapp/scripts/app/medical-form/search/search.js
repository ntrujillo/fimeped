(function (angular) {
    'use strict';
    angular.module('fimepedApp')
        .config(function ($stateProvider) {
            $stateProvider
                .state('fichas.search', {
                    parent: 'fichas',
                    url: '/search',
                    data: {
                        roles: ['ROLE_USER'],
                        pageTitle: 'fimepedApp.search.title'
                    },                    
                    views: {
                        'content@': {
                            templateUrl: 'scripts/app/medical-form/search/search.html',
                            controller: 'SearchClinicHistoryController'
                        }
                    },
                    resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader',
                                                 function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('search');
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            }
                                                ]
                    }
                });
        });
}(window.angular));