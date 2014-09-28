package com.ofg.twitter.controller.place.extractor

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import com.ofg.microservice.Collaborators
import groovy.transform.CompileStatic

@CompileStatic
class ColleratorClient {

    private final ServiceRestClient serviceRestClient

    ColleratorClient(ServiceRestClient serviceRestClient) {
        this.serviceRestClient = serviceRestClient
    }

    void populatePlaces(long pairId, String places) {
        serviceRestClient.forService(Collaborators.COLLERATOR_DEPENDENCY_NAME)
                .post()
                .onUrl("/$pairId")
                .body(places)
                .anObject()
                .ofType(String)
    }

}
