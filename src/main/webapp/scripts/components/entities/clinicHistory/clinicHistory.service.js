(function(angular){
	'use strict';

angular.module('fimepedApp')
    .factory('ClinicHistory', function ($resource, DateUtils) {
        return $resource('api/clinicHistorys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('ClinicHistoryPerson',function($resource){
    	return $resource ('api/persons/:person_id/clinicHistorys',{},{'query': { method: 'GET', isArray: true}}); 
    });
}(window.angular));