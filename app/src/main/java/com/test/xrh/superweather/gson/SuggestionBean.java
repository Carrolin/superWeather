package com.test.xrh.superweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017/9/25.
 */

public class SuggestionBean {
    /**
     * air : {"brf":"中","txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"}
     * comf : {"brf":"较舒适","txt":"白天天气晴好，您在这种天气条件下，会感觉早晚凉爽、舒适，午后偏热。"}
     * cw : {"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"}
     * drsg : {"brf":"热","txt":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。"}
     * flu : {"brf":"少发","txt":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。"}
     * sport : {"brf":"较不宜","txt":"阴天，且天气较热，请减少运动时间并降低运动强度。"}
     * trav : {"brf":"适宜","txt":"天气较好，温度适宜，总体来说还是好天气哦，这样的天气适宜旅游，您可以尽情地享受大自然的风光。"}
     * uv : {"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}
     */
    @SerializedName("comf")
    public Comfort comfort;
    @SerializedName("cw")
    public CarWash carwash;
    public Sport sport;
    public class Comfort {
        @SerializedName("txt")
        public String info;
    }
    public class CarWash{
        @SerializedName("txt")
        public String info;
    }
    public class Sport{
        @SerializedName("txt")
        public String info;
    }
}