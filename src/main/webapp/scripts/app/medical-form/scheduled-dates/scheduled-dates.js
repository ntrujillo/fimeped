(function (angular) {
    'use strict';
    angular.module('fimepedApp')
        .config(function ($stateProvider) {
            $stateProvider
                .state('fichas.scheduled', {
                    parent: 'fichas',
                    url: '/scheduled-dates',
                    data: {
                        roles: ['ROLE_USER'],
                        pageTitle: 'fimepedApp.scheduled.title'
                    },                    
                    views: {
                        'content@': {
                            templateUrl: 'scripts/app/medical-form/scheduled-dates/scheduled-dates.html',
                            controller: 'ScheduledDatesController'
                        }
                    },
                    resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader',
                                                 function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('scheduled');
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            }
                                                ]
                    }
                });
        });
}(window.angular));