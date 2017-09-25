package com.test.xrh.superweather.util;

import android.text.TextUtils;

import com.test.xrh.superweather.db.City;
import com.test.xrh.superweather.db.County;
import com.test.xrh.superweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2017/9/25.
 */

public class Utility {
    public static boolean handleProvinceRespondse(String respose){
        if (!TextUtils.isEmpty(respose)){
            try {
                JSONArray provinces =new JSONArray(respose);
                for (int i = 0; i < provinces.length(); i++) {
                    JSONObject provinceObject = provinces.getJSONObject(i);
                    Province province =new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCityRespondse(String respose,int ProvinceId){
        if (!TextUtils.isEmpty(respose)){
            try {
                JSONArray cities =new JSONArray(respose);
                for (int i = 0; i < cities.length(); i++) {
                    JSONObject cityObject = cities.getJSONObject(i);
                    City city =new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(ProvinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountyRespondse(String respose,int cityId){
        if (!TextUtils.isEmpty(respose)){
            try {
                JSONArray counties =new JSONArray(respose);
                for (int i = 0; i < counties.length(); i++) {
                    JSONObject countyObject = counties.getJSONObject(i);
                    County county =new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
