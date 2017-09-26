package com.test.xrh.superweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/9/25.
 */

public class NowBean {
    /**
     * cond : {"code":"104","txt":"阴"}
     * fl : 26
     * hum : 63
     * pcpn : 0
     * pres : 1012
     * tmp : 26
     * vis : 7
     * wind : {"deg":"113","dir":"东南风","sc":"微风","spd":"8"}
     */

    @SerializedName("tmp")
    public String temperature;
    @SerializedName("cond")
    public More more;
    public class More {
        @SerializedName("text")
        public String info;
    }

}
