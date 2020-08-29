'use strict';

angular.module('fimepedApp')
    .controller('SheetReasonController', function ($scope,entityEpisode,  ReasonService, ParseLinks) {
    	
    	  var onSaveFinished = function (result) {
              $scope.clear();
              $scope.reasons.push(result);
          };
    	
          var onUpdateFinished = function (result) {
              $scope.clear();
              
          };
    	
        $scope.reasons = [];
        $scope.page = 1;
        $scope.loadAll = function() {
        	ReasonService.query({episode_id : entityEpisode.id, page: $scope.page, per_page: 5}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.reasons = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
        	ReasonService.get({episode_id:entityEpisode.id, id: id}, function(result) {
                $scope.reason = result;
                $('#deleteReasonConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
        	ReasonService.delete({episode_id:entityEpisode.id,id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteReasonConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.reason = {description: null, id: null};
        };
        
        
        
        $scope.save = function () {
            if ($scope.reason.id != null) {
            	ReasonService.update({episode_id:entityEpisode.id},$scope.reason, onUpdateFinished);
            } else {            
            	ReasonService.save({episode_id:entityEpisode.id}, $scope.reason, onSaveFinished);
            }
        };
        
        $scope.loadReason = function(reason){
        	$scope.reason = reason;        	
        }
        
       

    });
