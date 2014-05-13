package com.ofg.config.db

import de.bwaldvogel.mongo.backend.memory.MemoryBackend
import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@TypeChecked
@Configuration
class MockDbConfiguration {
    
    @Bean(initMethod = 'bindPort', destroyMethod = 'shutdownNow')
    MongoServerStub mongoServer(@Value('${mongodb.port:27017}') Integer port) {
        return new MongoServerStub(new MemoryBackend(), port);
    }
}
