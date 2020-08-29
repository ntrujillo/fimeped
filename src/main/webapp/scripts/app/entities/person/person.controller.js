'use strict';

angular.module('fimepedApp')
    .controller('PersonController', function ($scope, Person, ParseLinks) {
        $scope.persons = [];
        $scope.filter = {};
        $scope.page = 1;
		$scope.per_page =10;
		
        $scope.loadAll = function() {
            Person.query({page: $scope.page, per_page: $scope.per_page},function(result,headers) {
               $scope.links = ParseLinks.parse(headers('link'));
               $scope.persons = result;
            });
        };
        
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        
        $scope.loadAll();

        $scope.delete = function (id) {
            Person.get({id: id}, function(result) {
                $scope.person = result;
                $('#deletePersonConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Person.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePersonConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.person = {systemInstitution: null, operatingUnit: null, operatingUnitCode: null, personType: null, personFaculty: null, personCareer: null, personSemester: null, firstName: null, secondName: null, paternalSurname: null, maternalSurname: null, cedula: null, address: null, phone: null, birthDate: null, birthPlace: null, nationality: null, age: null, gender: null, maritalStatus: null, approvedInstructionYear: null, admissionDate: null, occupation: null, companyWork: null, insuranceType: null, referred: null, kinshipName: null, kinship: null, kinshipAddress: null, kinshipPhone: null, neighborhood: null, parroquia: null, canton: null, provincia: null, zone: null, culturalGroup: null, id: null};
        };
        
        
        $scope.search = function() {

			if ($scope.filter.id && $scope.filter.id != '') {
				
				Person.get({id : $scope.filter.id}, function(result){
					$scope.persons = [result];
				});

			} else {
				Person.query({
					page : $scope.page,
					per_page : $scope.per_page,
					firstName : $scope.filter.firstName,
					secondName : $scope.filter.secondName,
					paternalSurname : $scope.filter.paternalSurname,
					maternalSurname : $scope.filter.maternalSurname,
					cedula : $scope.filter.cedula
				}, function(result, headers) {
					$scope.links = ParseLinks.parse(headers('link'));
					$scope.persons = result;
				});
			}
		};
    });
