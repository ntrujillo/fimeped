(function(angular){
	'use strict';

angular.module('fimepedApp')
    .factory('CurrentIllness', function ($resource) {
        return $resource('api/currentIllnesss/:id', {}, {
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
    }).factory('CurrentIllnessService', function ($resource) {
        return $resource('api/episodes/:episode_id/currentIllnesss/:id', {episode_id:'@episode_id'}, {
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