'use strict';

angular.module('BootstrapApplication.services')
    .factory('CityService', ['$http', function($http) {
        var cityService = {};
        cityService.findCity = function (lat, lon, successFn) {
            $http({
                url: '/city/'+lat+'/'+lon,
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
