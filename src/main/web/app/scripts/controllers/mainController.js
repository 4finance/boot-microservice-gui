'use strict';

/**
 * @ngdoc function
 * # MainCtrl
 */
angular.module('BootstrapApplication.controllers')
        .controller('MainCtrl', ['$scope', 'CityService', function ($scope, CityService) {
            $scope.awesomeThings = [
                'HTML5 Boilerplate',
                'AngularJS',
                'Karma'
            ];

            $scope.lat = '52.232222';
            $scope.lon = '21.008333';

            $scope.alerts = [];

            $scope.city = '';

            $scope.findCity = function() {
                CityService.findCity($scope.lat, $scope.lon, function(data) {
                    $scope.city = data;
                });
            };

            $scope.closeAlert = function (index) {
                $scope.alerts.splice(index, 1);
            };
        }]);
