'use strict';

angular.module('fimepedApp')
    .controller('EpisodeController', function ($scope, Episode, ParseLinks) {
        $scope.episodes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Episode.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.episodes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Episode.get({id: id}, function(result) {
                $scope.episode = result;
                $('#deleteEpisodeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Episode.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEpisodeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.episode = {date: null, description: null, id: null};
        };
    });
