(function(angular){
	'use strict';

angular.module('fimepedApp')
    .factory('ClinicHistoryAddInf', function ($resource, DateUtils) {
        return $resource('api/clinicHistoryAddInfs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.lastMenarche = DateUtils.convertLocaleDateFromServer(data.lastMenarche);
                    data.lastDelivery = DateUtils.convertLocaleDateFromServer(data.lastDelivery);
                    data.lastCitology = DateUtils.convertLocaleDateFromServer(data.lastCitology);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.lastMenarche = DateUtils.convertLocaleDateToServer(data.lastMenarche);
                    data.lastDelivery = DateUtils.convertLocaleDateToServer(data.lastDelivery);
                    data.lastCitology = DateUtils.convertLocaleDateToServer(data.lastCitology);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.lastMenarche = DateUtils.convertLocaleDateToServer(data.lastMenarche);
                    data.lastDelivery = DateUtils.convertLocaleDateToServer(data.lastDelivery);
                    data.lastCitology = DateUtils.convertLocaleDateToServer(data.lastCitology);
                    return angular.toJson(data);
                }
            }
        });
    });
}(window.angular));