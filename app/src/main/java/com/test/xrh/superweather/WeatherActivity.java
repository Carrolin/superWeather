package com.test.xrh.superweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.xrh.superweather.gson.DailyForecastBean;
import com.test.xrh.superweather.gson.Weather;
import com.test.xrh.superweather.util.HttpUtil;
import com.test.xrh.superweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2017/9/25.
 */

public class WeatherActivity extends AppCompatActivity {
    private ScrollView weatherLayout;
    private TextView title;
    private TextView updateTimeText;
    private TextView degreeText;
    private TextView weatherInfo;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carwashText;
    private TextView sportText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        title = ((TextView) findViewById(R.id.title_city));
        updateTimeText = ((TextView) findViewById(R.id.time));
        degreeText = ((TextView) findViewById(R.id.degree_text));
        weatherInfo = ((TextView) findViewById(R.id.weather_text));
        forecastLayout = ((LinearLayout) findViewById(R.id.forecast_layout));
        aqiText = ((TextView) findViewById(R.id.aqi_text));
        pm25Text = ((TextView) findViewById(R.id.pm25_text));
        comfortText = ((TextView) findViewById(R.id.comfort_text));
        carwashText = ((TextView) findViewById(R.id.car_wash_text));
        sportText = ((TextView) findViewById(R.id.sport_text));
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = pref.getString("weather", null);
        if (weatherString != null){
            //有缓存是直接解析天气数据
            Weather mWeather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(mWeather);
        }else{
            //去服务器查询
            String weather_id = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weather_id);

        }
    }

    private void requestWeather(String weather_id) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weather_id + "&key=bc0418b57b2d4918819d3974ac1285d9";
        HttpUtil.sendOkhttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().toString();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)){
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("weather",responseText);
                            Log.d("weatherActivity",preferences.getString("weather",null));
                            editor.apply();
                            showWeatherInfo(weather);
                        }else{
                            Toast.makeText(WeatherActivity.this,"获取天气失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private void showWeatherInfo(Weather mWeather) {
        String cityName = mWeather.basic.cityName;
        String updateTime = mWeather.basic.update.updateTime.split(" ")[1];
        String degree = mWeather.now.temperature + "℃";
        String info = mWeather.now.more.info;
        title.setText(cityName);
        updateTimeText.setText(updateTime);
        degreeText.setText(degree);
        weatherInfo.setText(info);
        forecastLayout.removeAllViews();
        for(DailyForecastBean forecast: mWeather.daily_forecast){
            View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.forecast_item,forecastLayout ,false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperatrue.max);
            minText.setText(forecast.temperatrue.min);
            forecastLayout.addView(view);
        }
        if (mWeather.aqi != null){
            aqiText.setText(mWeather.aqi.city.aqi);
            pm25Text.setText(mWeather.aqi.city.pm25);
        }
        String comfort = "舒适度: " + mWeather.suggestion.comfort.info;
        String carwash = "洗车指数: " + mWeather.suggestion.carwash.info;
        String sport = "运动指数: " + mWeather.suggestion.sport.info;
        weatherLayout.setVisibility(View.VISIBLE);
    }
}
