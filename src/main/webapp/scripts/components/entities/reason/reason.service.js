(function(angular){
	'use strict';

angular.module('fimepedApp')
    .factory('Reason', function ($resource) {
        return $resource('api/reasons/:id', {}, {
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
    }) .factory('ReasonService', function ($resource) {
        return $resource('api/episodes/:episode_id/reasons/:id', {episode_id:'@episode_id'}, {
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