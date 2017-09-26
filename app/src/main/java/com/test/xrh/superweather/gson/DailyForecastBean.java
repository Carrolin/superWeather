package com.test.xrh.superweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/9/25.
 */

public class DailyForecastBean {
    /**
     * astro : {"mr":"10:44","ms":"21:15","sr":"06:05","ss":"18:07"}
     * cond : {"code_d":"104","code_n":"104","txt_d":"阴","txt_n":"阴"}
     * date : 2017-09-25
     * hum : 50
     * pcpn : 0.0
     * pop : 5
     * pres : 1012
     * tmp : {"max":"28","min":"19"}
     * uv : 6
     * vis : 20
     * wind : {"deg":"84","dir":"东风","sc":"微风","spd":"5"}
     */
    public String date;
    @SerializedName("tmp")
    public Temperature temperatrue;
    @SerializedName("cond")
    public More more;
    public class Temperature{
        public String max;
        public String min;
    }
    public class More{
        @SerializedName("txt_d")
        public String info;
    }
}
