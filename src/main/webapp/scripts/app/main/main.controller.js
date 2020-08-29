(function(angular){
	'use strict';

angular.module('fimepedApp')
    .controller('MainController', function ($scope,$state, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });        
    });
}(window.angular));