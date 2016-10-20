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

import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller

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

        String testString = '''<?xml version="1.0" encoding="utf-8"?>
        <content type="application/xml">
            <m:properties>
                <d:FileSystemObjectType m:type="Edm.Int32">0</d:FileSystemObjectType>
                <d:Id m:type="Edm.Int32">1</d:Id>
                <d:ContentTypeId>0x0100D2243DBBF138CE46B8B935CFE769D76D00AC430B5E44C166459F803F33121C6FCC</d:ContentTypeId>
                <d:Title m:null="true" />
                <d:Serve_x0020_Date m:type="Edm.DateTime">2008-02-21T05:00:00Z</d:Serve_x0020_Date>
                <d:Delivery_x0020_LocationId m:type="Edm.Int32">10</d:Delivery_x0020_LocationId>
                <d:Business_x0020_Purpose>&lt;div&gt;Lunchtime Training Webinar&lt;/div&gt;</d:Business_x0020_Purpose>
                <d:Attendees>CarFax Internal</d:Attendees>
                <d:Number_x0020_of_x0020_People m:type="Edm.Double">5</d:Number_x0020_of_x0020_People>
                <d:Type_x0020_Of_x0020_FoodId m:type="Edm.Int32">7</d:Type_x0020_Of_x0020_FoodId>
                <d:Department_x0020_Charged_x0020_TId m:type="Edm.Int32">5</d:Department_x0020_Charged_x0020_TId>
                <d:Dietary_x0020_Concerns_x002F_sug>&lt;div&gt;&lt;/div&gt;</d:Dietary_x0020_Concerns_x002F_sug>
                <d:RequestorId m:type="Edm.Int32">68</d:RequestorId>
                <d:Serve_x0020_Time>12:00 PM</d:Serve_x0020_Time>
                <d:Action m:type="SP.FieldUrlValue">
                    <d:Description>Clarify</d:Description>
                    <d:Url>https://intranet.carfax.net/Department/VA/OfficeAdmin/Pages/FoodSerReqClarif.aspx.aspx?WebID=c8409976-1735-4247-9718-3ded5bcaa9ca&amp;ListID=4f6a99bd-6672-450e-9c67-2aea81c09d85&amp;ListItemID=6d861264-46bb-40b0-b989-69e2ee12746d</d:Url>
                </d:Action>
                <d:Coordinator m:null="true" />
                <d:Date_x0020_TimeStamp>2008-02-22T15:28:05Z</d:Date_x0020_TimeStamp>
                <d:Notes>&lt;div&gt;&lt;/div&gt;</d:Notes>
                <d:Status>Completed</d:Status>
                <d:Vendor_x0020_Contact_x0020_Infor m:null="true" />
                <d:Vendor_x0020_Name m:null="true" />
                <d:EditorId m:type="Edm.Int32">4</d:EditorId>
                <d:Order_x0020_Status m:null="true" />
                <d:NotifyOf m:null="true" />
                <d:Cost m:null="true" />
                <d:ID m:type="Edm.Int32">1</d:ID>
                <d:Modified m:type="Edm.DateTime">2008-02-22T15:28:05Z</d:Modified>
                <d:Created m:type="Edm.DateTime">2008-02-15T19:54:38Z</d:Created>
                <d:AuthorId m:type="Edm.Int32">68</d:AuthorId>
                <d:OData__UIVersionString>1.0</d:OData__UIVersionString>
                <d:Attachments m:type="Edm.Boolean">false</d:Attachments>
                <d:GUID m:type="Edm.Guid">f2744921-3e00-449e-993c-16790756b50b</d:GUID>
            </m:properties>
        </content>'''

        JAXBContext jaxbContext = JAXBContext.newInstance(FoodRequest)
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()
        InputStream inputStream = new ByteArrayInputStream(testString.getBytes())
        FoodRequest foodRequest = (FoodRequest)jaxbUnmarshaller.unmarshal(inputStream)

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



