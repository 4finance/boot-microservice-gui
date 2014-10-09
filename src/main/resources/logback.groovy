import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import ch.qos.logback.core.status.OnConsoleStatusListener

import static com.ofg.config.BasicProfiles.PRODUCTION
import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME

//for more details about groovy conf, see http://logback.qos.ch/manual/groovy.html
statusListener(OnConsoleStatusListener)

String rollingFile = "FILE"
String console = "CONSOLE"
String logPattern = "%d{yyyy-MM-dd HH:mm:ss.SSSZ, Europe/Warsaw} | %-5level | %X{correlationId} | %thread | %logger{1} | %m%n"
List whereToLog = [rollingFile, console]
String scanTime = "1 minutes"
String currentLogFile = "logs/application.log"
String rollingFileNamePattern = "logs/application.%d{yyyy-MM-dd}.log.zip"
int rollingFileMaxHistory = 7

println """Starting logback
To set your own log file, start it with
  java -Dlogback.configurationFile=/path/to/my-logback.groovy
ScanTime is set to $scanTime
Logging to: $whereToLog
Current log file is $currentLogFile
Rolling file name pattern is $rollingFileNamePattern
Max number of rolling files is $rollingFileMaxHistory
"""

scan(scanTime)

appender(rollingFile, RollingFileAppender) {
    file = currentLogFile
    append = true
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = rollingFileNamePattern
        maxHistory = rollingFileMaxHistory
    }
    encoder(PatternLayoutEncoder) {
        pattern = logPattern
    }
}

appender(console, ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = logPattern
    }
}

root(INFO, whereToLog)
if (System.getProperty(ACTIVE_PROFILES_PROPERTY_NAME) == PRODUCTION) {
    logger("com.ofg", INFO)
} else {
    logger("com.ofg", DEBUG)
}



