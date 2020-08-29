'use strict';

angular.module('fimepedApp')
    .controller('EpisodeDetailController', function ($scope, $rootScope, $stateParams, entity, Episode, User, ClinicHistory, Reason, CurrentIllness, CurrentRevision, VitalSign, PhysicalExam, Diagnostic, Plan, EvolutionPrescription) {
        $scope.episode = entity;
        $scope.load = function (id) {
            Episode.get({id: id}, function(result) {
                $scope.episode = result;
            });
        };
        $rootScope.$on('fimepedApp:episodeUpdate', function(event, result) {
            $scope.episode = result;
        });
    });
