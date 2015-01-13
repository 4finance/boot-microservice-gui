package com.ofg.base

import com.ofg.infrastructure.base.IntegrationSpec
import com.ofg.twitter.Application
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

import static com.ofg.config.BasicProfiles.TEST

@ContextConfiguration(classes = [Application], loader = SpringApplicationContextLoader)
@ActiveProfiles(TEST)
class MicroserviceIntegrationSpec extends IntegrationSpec {
}
