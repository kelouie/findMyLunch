package com.carfax.food.service

import spock.lang.Specification

class FoodServiceSpec extends Specification {


    def "GetFoodRequests"() {
        when:
        new FoodService(username: '', password: '').getFoodRequests()

        then:
        true
    }
}
