package com.carfax.food.service

import com.carfax.food.domain.FoodRequest
import spock.lang.Specification

class FoodServiceSpec extends Specification {

    def "GetFoodRequests"() {
        given:


        when:
        List<FoodRequest> result = new FoodService(username: '', password: '').getFoodRequests()

        then:
        result.size() > 0
    }
}
