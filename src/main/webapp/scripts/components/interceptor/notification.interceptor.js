 'use strict';

angular.module('fimepedApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-fimepedApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-fimepedApp-params')});
                }
                return response;
            },
        };
    });