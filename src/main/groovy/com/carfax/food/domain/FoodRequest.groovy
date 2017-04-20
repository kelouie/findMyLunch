package com.carfax.food.domain

import groovy.util.logging.Slf4j

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by katharinelouie on 6/10/16.
 */
@Slf4j
class FoodRequest {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a")

    Date serveDate
    Integer deliveryLocation
    String businessPurpose
    Integer numberOfPeople
    Integer typeOfFood
    String dietaryConcerns
    String notes
    String vendorName

    FoodRequest() {}

    FoodRequest(properties) {
        serveDate = createServeDate("${properties.Serve_x0020_Date}", "${properties.Serve_x0020_Time}")
        deliveryLocation = Integer.parseInt("${properties.Delivery_x0020_LocationId}")
        businessPurpose = properties.Business_x0020_Purpose
        numberOfPeople = Integer.parseInt("${properties.Number_x0020_of_x0020_People}")
        typeOfFood = Integer.parseInt("${properties.Type_x0020_Of_x0020_FoodId}")
        dietaryConcerns = properties.Dietary_x0020_Concerns_x002F_sug
        notes = properties.Notes
        vendorName = properties.Vendor_x0020_Name
    }

    protected static Date createServeDate(String serveDate, String serveTime) {
        String datePart = serveDate.split('T')[0]

        StringBuilder builder = new StringBuilder(datePart)
        builder.append(" ${serveTime.replaceAll('  ', ' ')}")

        try {
            return dateFormat.parse(builder.toString())
        } catch (ParseException e) {
            log.info("Couldn't parse date:  ${builder.toString()}")
            return null
        }
    }
}
