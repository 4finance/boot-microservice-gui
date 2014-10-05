'use strict';

angular.module('BootstrapApplication.services')
    .factory('CityService', ['$http', function($http) {
        var cityService = {};
        cityService.findCity = function (coords, successFn) {
            $http({
                url: '/city/'+coords.lat+'/'+coords.lon,
                dataType: 'json',
                method: 'POST',
                data: '',
                headers: {
                    'Content-Type': 'application/vnd.com.ofg.twitter-places-analyzer.v1+json'
                }
            }).success(function (data) {
                successFn(data);
            });
        };

        return cityService;
    }
]);
