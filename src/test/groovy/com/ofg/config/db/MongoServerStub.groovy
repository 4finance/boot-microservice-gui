package com.ofg.config.db

import de.bwaldvogel.mongo.MongoServer
import de.bwaldvogel.mongo.backend.MongoBackend
import groovy.transform.TypeChecked

@TypeChecked
class MongoServerStub extends MongoServer {
    
    private final Integer port 

    MongoServerStub(MongoBackend backend, Integer port) {
        super(backend)
        this.port = port
    }

    void bindPort() {
        bind(new InetSocketAddress(port))
    }
    
}
