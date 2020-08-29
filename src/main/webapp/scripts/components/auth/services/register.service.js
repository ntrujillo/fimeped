'use strict';

angular.module('fimepedApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


