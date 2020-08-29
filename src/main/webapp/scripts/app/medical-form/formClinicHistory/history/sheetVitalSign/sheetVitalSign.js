(function(angular){
		'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fichas.form.history.vital_sign', {
                parent: 'fichas.form.history',
                url: '/vital_sign',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.vitalSign.home.title'
                },
                views: {
                    'content.sheet': {
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/history/sheetVitalSign/sheetVitalSigns.html',
                        controller: 'SheetVitalSignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vitalSign');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
    });
}(window.angular));
