package com.ofg.base

import com.ofg.infrastructure.base.MvcIntegrationSpec
import com.ofg.microservice.Application
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

import static com.ofg.config.BasicProfiles.TEST

@ContextConfiguration(classes = [Application], loader = SpringApplicationContextLoader)
@ActiveProfiles(TEST)
class MicroserviceMvcIntegrationSpec extends MvcIntegrationSpec {


}
