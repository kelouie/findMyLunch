package com.carfax.food.domain
/**
 * Created by katharinelouie on 6/10/16.
 */
class FoodRequest {
    String businessPurpose

    FoodRequest() {}

    FoodRequest(properties) {
        this.businessPurpose = properties.Business_x0020_Purpose
    }
}
