package com.carfax.food.domain

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
/**
 * Created by katharinelouie on 6/10/16.
 */
@XmlRootElement(name = 'content')
@XmlAccessorType( XmlAccessType.NONE )
class FoodRequest {

    @XmlElement(name = 'properties', namespace = 'http://schemas.microsoft.com/ado/2007/08/dataservices/metadata')
    List feed

//    String getFeed() {
//        feed
//    }
//
//    void setFeed(String feed) {
//        this.feed = feed
//    }
}
