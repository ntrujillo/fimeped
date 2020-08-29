(function(angular){
	'use strict';

angular.module('fimepedApp')
    .factory('EvolutionPrescription', function ($resource) {
        return $resource('api/evolutionPrescriptions/:id', {}, {
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
    }).factory('EvolutionPrescriptionService', function ($resource) {
        return $resource('api/episodes/:episode_id/evolutionPrescriptions/:id', {}, {
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