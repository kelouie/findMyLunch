package com.carfax.food.controller

import com.carfax.food.domain.Day
import com.carfax.food.domain.FoodRequest
import com.carfax.food.service.FoodService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
/**
 * Created by katharinelouie on 6/9/16.
 */
@Controller
class FoodController {

    @Autowired
    FoodService foodService

    @RequestMapping("/findMyLunch")
    public String findMyLunch(@RequestParam(value="room",required=false) String room, Model model) {

        List<Day> days = []
        List<FoodRequest> foodRequests = foodService.getFoodRequests()

        foodRequests.each { FoodRequest foodRequest ->
            Day day = days.find { it.calendar.get(Calendar.DAY_OF_YEAR) == foodRequest.dayOfYear }
            if (day) {
                day.foodRequests.add(foodRequest)
            } else {
                days.add(new Day(date: foodRequest.serveDate, foodRequests: [foodRequest]))
            }
        }

        model.addAttribute("days", days)

        return "findMyLunch";
    }
}
