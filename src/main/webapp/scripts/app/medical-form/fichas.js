(function (angular) {
    'use strict';

    angular.module('fimepedApp')
        .config(function ($stateProvider) {
            $stateProvider
                .state('fichas', {
                    abstract: true,
                    parent: 'site'
                });
            
        });
}(window.angular));