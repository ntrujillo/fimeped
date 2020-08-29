(function(angular){
	'use strict';
	angular.module('fimepedApp').controller('InformationController',function($scope,$rootScope,CatalogResource,CountryResource,CantonResource,ProvinciaResource,DateUtils){
		
			$scope.clinicHistory = $rootScope.clinicHistory;	
			$scope.person = $scope.clinicHistory.person;	
			$scope.catalogCountry = [];
			$scope.catalogCanton = [];
			$scope.catalogProvincia = [];
			$scope.catalogGender = [];	
			
			if($scope.person.birthDate && !($scope.person.birthDate instanceof Date)) {
				$scope.person.birthDate = DateUtils.convertLocaleDateFromServer($scope.person.birthDate);
			}
			if($scope.person.admissionDate && !($scope.person.admissionDate instanceof Date)) {
				$scope.person.admissionDate = DateUtils.convertLocaleDateFromServer($scope.person.admissionDate);		
			}
			$scope.loadCatalogGender= function(){	        	
		        	CatalogResource.query({tab_id:'cl_gender',sort:'value'},function(result){	        		
		        		$scope.catalogGender = result;		        	       	
		        	});
		     };		        
		    $scope.loadCatalogGender();	     
		        
		    $scope.loadCatalogCountry= function(){		        	
		        	CountryResource.query({},function(result){	        		
		        			$scope.catalogCountry=result;		        		       	
		        	});
		     };	        
		     $scope.loadCatalogCountry();
		     
		         $scope.loadCatalogProvincia= function(){		        	
	        	ProvinciaResource.query({},function(result){
	        		$scope.catalogProvincia = result;        		    	
	        	});
		     }; 	        
		     $scope.loadCatalogProvincia();
		     
		      $scope.loadCatalogCanton= function(){
		        	
		        	CantonResource.query({},function(result){		        		
		        		$scope.catalogCanton = result;	        		       	
		        	});
		     };  	        
		     $scope.loadCatalogCanton();
	});
}(window.angular));