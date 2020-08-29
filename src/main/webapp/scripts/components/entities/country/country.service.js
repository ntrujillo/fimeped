(function(angular){
	'use strict';

angular.module('fimepedApp')
    .factory('CountryResource', function ($resource, DateUtils) {
        return $resource('api/countrys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
}(window.angular));