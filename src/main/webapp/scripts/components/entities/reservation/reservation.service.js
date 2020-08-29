(function(angular){
	'use strict';

angular.module('fimepedApp')
    .factory('ReservationResource', function ($resource, DateUtils) {
        return $resource('api/reservations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date = DateUtils.convertLocaleDateFromServer(data.date);
                    return data;
                }
            }
        });
    });
}(window.angular));