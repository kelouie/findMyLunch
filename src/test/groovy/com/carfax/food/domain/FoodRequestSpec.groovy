package com.carfax.food.domain

import spock.lang.Specification

/**
 * Created by praveengnanasundram on 4/20/17.
 */
class FoodRequestSpec extends Specification {

    def "CreateServeDate"() {
        when:
        Date result = FoodRequest.createServeDate('2017-04-20T04:00:00Z', '9:30  AM')

        then:
        result
    }
}
