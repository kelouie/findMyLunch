package com.carfax.food.domain

import com.carfax.food.FormattingUtils
import com.carfax.food.service.FoodService
import groovy.util.logging.Slf4j

import java.text.SimpleDateFormat
/**
 * Created by katharinelouie on 6/10/16.
 */
@Slf4j
class FoodRequest implements Comparable {

    private static SimpleDateFormat timeOnlyFormat = new SimpleDateFormat('HH:mm a')

    Date serveDate
    Integer deliveryLocation
    ConferenceRoom room
    String businessPurpose
    Integer numberOfPeople
    Integer typeOfFood
    String food
    String dietaryConcerns
    String notes
    String vendorName

    FoodRequest() {}

    FoodRequest(properties) {
        serveDate = FormattingUtils.createServeDate("${properties.Serve_x0020_Date}", "${properties.Serve_x0020_Time}")
        def rooms = FoodService.getConferenceRooms()

        try {
            deliveryLocation = Integer.parseInt("${properties.Delivery_x0020_LocationId}")
        } catch (NumberFormatException e) {
            deliveryLocation = -1
        }
        room = rooms.find(){it.id == deliveryLocation}
        businessPurpose = FormattingUtils.stripFormatting("${properties.Business_x0020_Purpose}")
        numberOfPeople = Integer.parseInt("${properties.Number_x0020_of_x0020_People}")
        typeOfFood = Integer.parseInt("${properties.Type_x0020_Of_x0020_FoodId}")
        food = FoodRequestTypes.foodTypeMapping.get(typeOfFood)
        dietaryConcerns = FormattingUtils.stripFormatting("${properties.Dietary_x0020_Concerns_x002F_sug}")
        notes = FormattingUtils.stripFormatting("${properties.Notes}")
        vendorName = properties.Vendor_x0020_Name

    }

    String getRoomDisplay(){
        room?.roomAndFloor
    }

    String getTime() {
        timeOnlyFormat.format(serveDate)
    }

    int getDayOfYear() {
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(serveDate)

        return calendar.get(Calendar.DAY_OF_YEAR)
    }

    @Override
    int compareTo(Object o) {
        serveDate.time - (o as FoodRequest).serveDate.time
    }
}
