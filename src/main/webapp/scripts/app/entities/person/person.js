'use strict';

angular.module('fimepedApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('person', {
                parent: 'entity',
                url: '/persons',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.person.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/person/persons.html',
                        controller: 'PersonController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('person');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('person.detail', {
                parent: 'entity',
                url: '/person/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'fimepedApp.person.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/person/person-detail.html',
                        controller: 'PersonDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('person');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Person', function($stateParams, Person) {
                        return Person.get({id : $stateParams.id});
                    }]
                }
            })
            .state('person.new', {
                parent: 'person',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/person/person-dialog.html',
                        controller: 'PersonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {systemInstitution: null, operatingUnit: null, operatingUnitCode: null, personType: null, personFaculty: null, personCareer: null, personSemester: null, firstName: null, secondName: null, paternalSurname: null, maternalSurname: null, cedula: null, address: null, phone: null, birthDate: null, birthPlace: null, nationality: null, age: null, gender: null, maritalStatus: null, approvedInstructionYear: null, admissionDate: null, occupation: null, companyWork: null, insuranceType: null, referred: null, kinshipName: null, kinship: null, kinshipAddress: null, kinshipPhone: null, neighborhood: null, parroquia: null, canton: null, provincia: null, zone: null, culturalGroup: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('person', null, { reload: true });
                    }, function() {
                        $state.go('person');
                    })
                }]
            })
            .state('person.edit', {
                parent: 'person',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/person/person-dialog.html',
                        controller: 'PersonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Person', function(Person) {
                                return Person.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('person', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
