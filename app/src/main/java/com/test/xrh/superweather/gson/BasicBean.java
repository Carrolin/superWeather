package com.test.xrh.superweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/9/25.
 */

public class BasicBean {
        /**
         * city : 北京
         * cnty : 中国
         * id : CN101010100
         * lat : 39.90498734
         * lon : 116.40528870
         * update : {"loc":"2017-09-25 12:46","utc":"2017-09-25 04:46"}
         */
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    public Update update;
    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
    }
