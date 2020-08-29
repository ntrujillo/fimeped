'use strict';

angular.module('fimepedApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
