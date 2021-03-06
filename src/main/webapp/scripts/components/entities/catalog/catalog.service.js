(function(angular){
		'use strict';

angular.module('fimepedApp')
    .factory('CatalogResource', function ($resource) {
        return $resource('api/tablas/:tab_id/catalogs/:id', {}, {
        	'query' : {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);                   
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {                   
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {                   
                    return angular.toJson(data);
                }
            }
        });
    });
}(window.angular));
