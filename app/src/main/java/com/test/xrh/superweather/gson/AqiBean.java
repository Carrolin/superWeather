package com.test.xrh.superweather.gson;

/**
 * Created by admin on 2017/9/25.
 */

public  class AqiBean {
    /**
     * city : {"aqi":"95","co":"1","no2":"49","o3":"55","pm10":"136","pm25":"71","qlty":"良","so2":"10"}
     */
    public AqiCity city;
    public class AqiCity{
        public String aqi;
        public String pm25;
    }
}