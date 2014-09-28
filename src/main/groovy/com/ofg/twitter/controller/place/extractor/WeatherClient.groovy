package com.ofg.twitter.controller.place.extractor

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import groovy.transform.CompileStatic

@CompileStatic
class WeatherClient {

    private final ServiceRestClient serviceRestClient
    private final String cityFindingServiceUrl

    WeatherClient(ServiceRestClient serviceRestClient, String cityFindingServiceUrl) {
        this.serviceRestClient = serviceRestClient
        this.cityFindingServiceUrl = cityFindingServiceUrl
    }


    String findCity(Double latitude, Double longitude) {
        return serviceRestClient.forExternalService()
                .get()
                .onUrl("$cityFindingServiceUrl?lat=${latitude}&lon=${longitude}")
                .anObject()
                .ofType(String)
    }

    String isCityExistent(String cityNameToCheck) {
        return serviceRestClient.forExternalService()
                .get()
                .onUrl("$cityFindingServiceUrl?q=$cityNameToCheck")
                .anObject()
                .ofType(String)
    }


}
