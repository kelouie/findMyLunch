package com.carfax.food.service

import com.carfax.food.domain.ConferenceRoom
import com.carfax.food.domain.FoodRequest
import groovy.util.logging.Slf4j
import org.apache.tomcat.util.codec.binary.Base64
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Component
@Slf4j
class FoodService {

    @Value('${username}')
    String username

    @Value('${password}')
    String password

    List<FoodRequest> getFoodRequests(){
        File file = new File("/Users/katharinelouie/Downloads/chromedriver 2");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

        WebDriver driver = new ChromeDriver()
        driver.get("https://$username:$password@intranet.carfax.net")

        driver.get('https://intranet.carfax.net/Department/VA/OfficeAdmin/_api/lists/getbytitle(\'Food Service Request storage\')/items?$select=*&$filter=(Serve_x0020_Date ge datetime\'2017-04-20T00%3a00%3a00\') and (Serve_x0020_Date lt datetime\'2017-04-28T00%3a00%3a00\')')
        WebElement element = driver.findElement(By.tagName('pre'))
        String innerHtml = element.getAttribute('innerHTML')

        driver.quit()

        innerHtml = innerHtml.replace("&lt;", "<").replace("&gt;", ">")

        def result = new XmlSlurper().parseText(innerHtml).declareNamespace(
                d:"http://schemas.microsoft.com/ado/2007/08/dataservices/",
                m:"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata/",
                georss:"http://www.georss.org/georss/",
                gml:"http://www.opengis.net/gml/"
        )
        log.info("${result.entry[1].content.properties.RequestorId}")

        List<FoodRequest> foodRequestList = result.entry.collect {
            new FoodRequest(it.content.properties)
        }

        return foodRequestList
    }

    static HttpHeaders createHeaders(String username, String password) {
        String auth = "$username:$password"
        byte[] encodedAuth = Base64.encodeBase64(auth.bytes)
        String encodedAuthString = new String(encodedAuth)

        HttpHeaders httpHeaders = new HttpHeaders()
        httpHeaders.add('Authorization', "Basic $encodedAuthString")

        return httpHeaders
    }

    static List<ConferenceRoom> getConferenceRooms(){
        List<ConferenceRoom> rooms = [new ConferenceRoom(id: 27, roomName: 'VA 1-Owner Room', floor: 4)]
        rooms.add(new ConferenceRoom(id:37, roomName:'VA Acquire Room', floor:4))
        rooms.add(new ConferenceRoom(id:28, roomName:'VA Advantage Room', floor:4))
        rooms.add(new ConferenceRoom(id:35, roomName:'VA Advertise Room', floor:4))
        rooms.add(new ConferenceRoom(id:18, roomName:'VA Auction', floor:5))
        rooms.add(new ConferenceRoom(id:13, roomName:'VA Box', floor:6))
        rooms.add(new ConferenceRoom(id:17, roomName:'VA Brand', floor:6))
        rooms.add(new ConferenceRoom(id:31, roomName:'VA Buy Room', floor:4))
        rooms.add(new ConferenceRoom(id:2, roomName:'VA Buyback Room', floor:5))
        rooms.add(new ConferenceRoom(id:25, roomName:'VA CARFOX Lounge', floor:3))
        rooms.add(new ConferenceRoom(id:19, roomName:'VA CPO', floor:3))
        rooms.add(new ConferenceRoom(id:8, roomName:'VA EML Room', floor:5))
        rooms.add(new ConferenceRoom(id:10, roomName:'VA Flood Room (Training)', floor:6))
        rooms.add(new ConferenceRoom(id:16, roomName:'VA Garage', floor:5))
        rooms.add(new ConferenceRoom(id:3, roomName:'VA Junk Yard Room', floor:5))
        rooms.add(new ConferenceRoom(id:9, roomName:'VA Lemon Room', floor:5))
        rooms.add(new ConferenceRoom(id:22, roomName:'VA Lot', floor:4))
        rooms.add(new ConferenceRoom(id:5, roomName:'VA NAM Room', floor:5))
        rooms.add(new ConferenceRoom(id:24, roomName:'VA Nascar Cafe', floor:5))
        rooms.add(new ConferenceRoom(id:6, roomName:'VA Odometer Room', floor:5))
        rooms.add(new ConferenceRoom(id:38, roomName:'VA Own Room', floor:4))
        rooms.add(new ConferenceRoom(id:11, roomName:'VA Recall Room', floor:5))
        rooms.add(new ConferenceRoom(id:14, roomName:'VA Repair', floor:3))
        rooms.add(new ConferenceRoom(id:32, roomName:'VA Retail Room', floor:4))
        rooms.add(new ConferenceRoom(id:4, roomName:'VA Salvage Room', floor:6))
        rooms.add(new ConferenceRoom(id:34, roomName:'VA Sell Room', floor:4))
        rooms.add(new ConferenceRoom(id:20, roomName:'VA Service', floor:3))
        rooms.add(new ConferenceRoom(id:36, roomName:'VA Service Room', floor:4))
        rooms.add(new ConferenceRoom(id:33, roomName:'VA Shop Room', floor:4))
        rooms.add(new ConferenceRoom(id:21, roomName:'VA Show Me', floor:3))
        rooms.add(new ConferenceRoom(id:7, roomName:'VA Spare Parts Room', floor:6))
        rooms.add(new ConferenceRoom(id:23, roomName:'VA Tech', floor:5))
        rooms.add(new ConferenceRoom(id:39, roomName:'VA The Lot Room', floor:4))
        rooms.add(new ConferenceRoom(id:15, roomName:'VA Total Loss Room', floor:6))
        rooms.add(new ConferenceRoom(id:12, roomName:'VA VIN', floor:3))
        rooms.add(new ConferenceRoom(id:26, roomName:'VA Other', floor:0))
        rooms.add(new ConferenceRoom(id:30, roomName:'VA Well Maintained Room', floor:4))
        rooms.add(new ConferenceRoom(id:29, roomName:'VA Wisdom Room', floor:4))
        rooms.add(new ConferenceRoom(id:1, roomName:'VA Wreck Room', floor:6))
        rooms.add(new ConferenceRoom(id:-1, roomName:'Unknown', floor:0))

        return rooms
    }
}



