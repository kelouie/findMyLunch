package com.carfax.food.controller

import com.carfax.food.service.FoodService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

/**
 * Created by katharinelouie on 6/9/16.
 */
@Controller
class FoodController {

    @Autowired
    FoodService foodService

    @RequestMapping("/findMyLunch")
    public String findMyLunch(@RequestParam(value="room",required=false) String room, Model model) {
        model.addAttribute("room", room);
        foodService.getFoodRequests()
        return "findMyLunch";
    }
}
