(function(angular){
		'use strict';

angular.module('fimepedApp')
    .factory('CatalogCie10', function ($resource, DateUtils) {
        return $resource('api/icd10', {}, {
        	'get' : {method: 'GET', isArray: true}
        });
    });
}(window.angular));
