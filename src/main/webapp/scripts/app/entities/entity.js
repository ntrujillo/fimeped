(function(angular){
'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('entity', {
                abstract: true,
                parent: 'site'
            });
    });
}(window.angular));