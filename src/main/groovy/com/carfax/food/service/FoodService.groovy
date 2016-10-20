package com.carfax.food.service

import com.carfax.food.domain.FoodRequest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.apache.tomcat.util.codec.binary.Base64

import javax.annotation.Resource


@Component
@Slf4j
class FoodService {

    private static final String URL = 'https://intranet.carfax.net/Department/VA/OfficeAdmin/_api/lists/'

    @Resource(name = 'restTemplate')
    RestTemplate foodRequestRestTemplate

    @Value('${katies.password}')
    String password

    List<FoodRequest> getFoodRequests(){
//        final URI uri = UriComponentsBuilder.fromUriString('https://intranet.carfax.net/Department/VA/OfficeAdmin/_api/lists/')
//                .build().encode().toUri()

        try {
            HttpHeaders httpHeaders = createHeaders('KatharineLouie', password)
            HttpEntity request = new HttpEntity<String>(httpHeaders)

//            List<FoodRequest> requests = foodRequestRestTemplate.exchange(
//                    URL,
//                    HttpMethod.GET,
//                    request,
//                    FoodRequest
//
//            )

            List<FoodRequest> requests = foodRequestRestTemplate.getForObject(URL, FoodRequest)
                    //getForObject(URL, FoodRequest.class)
            println 'requests ' + requests
            return requests
        } catch (HttpClientErrorException e) {
            log.error(e.message)
            return null
        }
    }

    static HttpHeaders createHeaders(String username, String password) {
        String auth = "$username:$password"
        byte[] encodedAuth = Base64.encodeBase64(auth.bytes)
        String encodedAuthString = new String(encodedAuth)

        HttpHeaders httpHeaders = new HttpHeaders()
        httpHeaders.add('Authorization', "Basic $encodedAuthString")

        return httpHeaders
    }
}



