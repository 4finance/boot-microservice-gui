package com.ofg.twitter.places
import com.github.tomakehurst.wiremock.client.UrlMatchingStrategy
import com.ofg.base.MicroserviceMvcWiremockSpec
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.jayway.awaitility.Awaitility.await
import static com.ofg.infrastructure.base.dsl.WireMockHttpRequestMapper.wireMockGet
import static com.ofg.twitter.controller.place.extractor.WeatherApiResponses.CITY_FOUND
import static com.ofg.twitter.tweets.Tweets.TWEET_WITH_COORDINATES
import static com.ofg.twitter.tweets.Tweets.TWEET_WITH_PLACE
import static java.util.concurrent.TimeUnit.SECONDS
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AcceptanceSpec extends MicroserviceMvcWiremockSpec {
       
    static final String ROOT_PATH = '/api'
    static final String PAIR_ID = '1'
    static final MediaType TWITTER_PLACES_ANALYZER_MICROSERVICE_V1 = new MediaType('application', 'vnd.com.ofg.twitter-places-analyzer.v1+json')
    static final String COLLERATOR_ENPOINT_URL = '/collerator'
    static final UrlMatchingStrategy COLLERATOR_URL_WITH_PAIR_ID = urlEqualTo("$COLLERATOR_ENPOINT_URL/$PAIR_ID")

    def "should find a place by verifying tweet's geolocation"() {
        given: 'a tweet with a place section filled in'
            String tweet = TWEET_WITH_PLACE
            stubInteraction(post(COLLERATOR_URL_WITH_PAIR_ID), aResponse().withStatus(HttpStatus.OK.value()))
        when: "trying to retrieve place from the tweet"
            mockMvc.perform(put("$ROOT_PATH/$PAIR_ID").contentType(TWITTER_PLACES_ANALYZER_MICROSERVICE_V1).content("[$tweet]"))
                    .andDo(print())
                   .andExpect(status().isOk())
        then: "user's location (place) will be extracted from that section"
            await().atMost(2, SECONDS).until({ wireMock.verifyThat(postRequestedFor(COLLERATOR_URL_WITH_PAIR_ID).withRequestBody(equalToJson('''
                                                                        [{
                                                                            "pair_id" : 1,
                                                                            "tweet_id" : "492967299297845248",
                                                                            "place" :
                                                                            {
                                                                                "name":"Washington",
                                                                                "country_code": "US"
                                                                            },
                                                                            "probability" : "2",
                                                                            "origin" : "twitter_place_section"
                                                                        }]
                                                                        ''')))})
    }

    def "should find a place by verifying tweet's coordinates"() {
        given: 'a tweet with a coordinates section filled in'
            String tweet = TWEET_WITH_COORDINATES
            stubInteraction(wireMockGet('/?lat=-75.14310264&lon=40.05701649'), aResponse().withBody(CITY_FOUND))
            stubInteraction(post(COLLERATOR_URL_WITH_PAIR_ID), aResponse().withStatus(HttpStatus.OK.value()))
        when: 'trying to retrieve place from the tweet'
            mockMvc.perform(put("$ROOT_PATH/$PAIR_ID").contentType(TWITTER_PLACES_ANALYZER_MICROSERVICE_V1).content("[$tweet]"))
                    .andExpect(status().isOk())
        then: "user's location (place) will be extracted from that section"
            await().atMost(2, SECONDS).until({ wireMock.verifyThat(postRequestedFor(COLLERATOR_URL_WITH_PAIR_ID).withRequestBody(equalToJson('''
                                                                            [{
                                                                                    "pair_id" : 1,
                                                                                    "tweet_id" : "492961315070439424",
                                                                                    "place" :
                                                                                    {
                                                                                        "name":"Tappahannock",
                                                                                        "country_code": "US"
                                                                                    },
                                                                                    "probability" : "2",
                                                                                    "origin" : "twitter_coordinates_section"
                                                                                }]
                                                                            ''')))})
    }

    // http://api.openweathermap.org/data/2.5/weather?q=London
    def "should try to find a place by cross referencing tweet's user mention with a city index if coordinates/places is not available"() {
        given: 'a tweet with a user mention section filled in (without coordinates)'
        when: 'trying to retrieve place from the tweet by cross referencing user mention with a city index'
        then: "user's location (place) will be the matching city"
    }

    def "should try to find a place by cross referencing tweet's hashtags with a city index if no user mention matches"() {
        given: 'a tweet with a hashtag section filled in (without coordinates/places and user mention)'
        when: 'trying to retrieve place from the tweet by cross referencing hash tag with a city index'
        then: "user's location (place) will be the matching city"
    }

    def "should try to find a place by cross referencing tweet's content with a city index if no user mention matches"() {
        given: 'a tweet without geolocation, places, user mention and hashtag sections'
        when: 'trying to retrieve place from the tweet by cross referencing tweet content with a city index'
        then: "user's location (place) will be the first matching city"
    }

}
