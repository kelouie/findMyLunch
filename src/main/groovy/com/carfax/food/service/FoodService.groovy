package com.carfax.food.service

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
        WebDriver driver = new ChromeDriver()
        driver.get("https://$username:$password@intranet.carfax.net")

        driver.get('https://intranet.carfax.net/Department/VA/OfficeAdmin/_api/lists/getbytitle(\'Food Service Request storage\')/items')
        WebElement element = driver.findElement(By.tagName('pre'))
        String result = element.getAttribute('innerHTML')

        return result
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



