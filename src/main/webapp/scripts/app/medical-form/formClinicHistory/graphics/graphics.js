(function (angular) {
    'use strict';
    angular.module('fimepedApp')    
        .config(function ($stateProvider) {
            $stateProvider.state('fichas.form.graphics', {                	
                parent: 'fichas.form',                    
                url: '/graphic',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.form.title'
                },
                views: {
                    'content.form': {
                        templateUrl: 'scripts/app/medical-form/formClinicHistory/graphics/graphics.html',
                        controller: 'GraphicsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader',
                                             function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('formClinicHistory');  
                            $translatePartialLoader.addPart('vitalSign'); 
                            $translatePartialLoader.addPart('global'); 
                            return $translate.refresh();
                        }], entityClinicHistory : ["$stateParams","ClinicHistory",function($stateParams, ClinicHistory ){
                        	return ClinicHistory.get({history_id:$stateParams.historyId});
                        }]
                }                    
            }).state('fichas.form.graphics.head_circunference', {                	
                    parent: 'fichas.form.graphics',                    
                    url: '/head_circunference/',
                    data: {
                        roles: ['ROLE_USER'],
                        pageTitle: 'fimepedApp.form.title'
                    },
                    onEnter: ['$stateParams', '$state', '$modal',  function($stateParams, $state, $modal){                      	
                          	$modal.open({
                                    templateUrl: 'scripts/app/medical-form/formClinicHistory/graphics/head_circunference.html',
                                    controller: 'HeadCircunferenceController',
                                    size: 'lg',
                                    backdrop : 'static'
                                }).result.then(function(result) {                        	
                                    $state.go('fichas.form.graphics', {}, { reload: true });
                                }, function() {
                                    $state.go('fichas.form.graphics');
                                });                    
                    }]
                }).state('fichas.form.graphics.length_height', {                	
                    parent: 'fichas.form.graphics',                    
                    url: '/length_height',
                    data: {
                        roles: ['ROLE_USER'],
                        pageTitle: 'fimepedApp.form.title'
                    },
                    onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal){                      	
                      	$modal.open({
                                templateUrl: 'scripts/app/medical-form/formClinicHistory/graphics/length_height.html',
                                controller: 'LengthHeightController',
                                size: 'lg',
                                backdrop : 'static'
                            }).result.then(function(result) {                        	
                                $state.go('fichas.form.graphics', {}, { reload: true });
                            }, function() {
                                $state.go('fichas.form.graphics');
                            });                    
                    }]
                    
                }).state('fichas.form.graphics.weight', {                	
                    parent: 'fichas.form.graphics',                    
                    url: '/weight',
                    data: {
                        roles: ['ROLE_USER'],
                        pageTitle: 'fimepedApp.form.title'
                    },
                    onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal){                    	
                      	$modal.open({
                                templateUrl: 'scripts/app/medical-form/formClinicHistory/graphics/weight.html',
                                controller: 'WeightController',
                                size: 'lg',
                                backdrop : 'static'
                            }).result.then(function(result) {                        	
                                $state.go('fichas.form.graphics', {}, { reload: true });
                            }, function() {
                                $state.go('fichas.form.graphics');
                            });                    
                    }]                    
                });

        });

}(window.angular));