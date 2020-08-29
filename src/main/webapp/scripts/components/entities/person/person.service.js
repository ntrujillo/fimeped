(function(angular){
	'use strict';

angular.module('fimepedApp')
    .factory('Person', function ($resource, DateUtils) {
        return $resource('api/persons/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.birthDate = DateUtils.convertLocaleDateFromServer(data.birthDate);
                    data.admissionDate = DateUtils.convertLocaleDateFromServer(data.admissionDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.birthDate = DateUtils.convertLocaleDateToServer(data.birthDate);
                    data.admissionDate = DateUtils.convertLocaleDateToServer(data.admissionDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.birthDate = DateUtils.convertLocaleDateToServer(data.birthDate);
                    data.admissionDate = DateUtils.convertLocaleDateToServer(data.admissionDate);
                    return angular.toJson(data);
                }
            }
        });
    });
}(window.angular));