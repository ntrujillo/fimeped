(function(angular){
	'use strict';
angular.module('fimepedApp')
    .factory('PhysicalExam', function ($resource) {
        return $resource('api/physicalExams/:id', {}, {
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
    }).factory('PhysicalExamService', function ($resource) {
        return $resource('api/episodes/:episode_id/physicalExams/:id', {}, {
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