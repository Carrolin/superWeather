package com.test.xrh.superweather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.xrh.superweather.db.City;
import com.test.xrh.superweather.db.County;
import com.test.xrh.superweather.db.Province;
import com.test.xrh.superweather.util.HttpUtil;
import com.test.xrh.superweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2017/9/25.
 */

public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String COUNTY = "county";
    public static final String baseUrl="http://guolin.tech/api/china";
    private TextView title;
    private Button back;
    private ListView mLv;
    private List<String> dataList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    //省列表
    private List<Province> mProvinceList;
    //市列表
    private List<City> mCityList;
    //县列表
    private List<County> mCountyList;
    //选中的省份
    private Province selectedProvince;
    //选中的城市
    private City selectedCity;
    //当前选中的级别
    private int currentLevel;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area,container,false);
        title = (TextView) view.findViewById(R.id.tittle_text);
        back = (Button) view.findViewById(R.id.back_button);
        mLv = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        mLv.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel == LEVEL_PROVINCE){
                    selectedProvince = mProvinceList.get(i);
                    queryCities();
                }else if(currentLevel == LEVEL_CITY){
                    selectedCity = mCityList.get(i);
                    queryCounties();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentLevel == LEVEL_COUNTY){
                    queryCities();
                }else if(currentLevel == LEVEL_CITY){
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    private void queryProvinces() {
        title.setText("中国");
        back.setVisibility(View.GONE);
        mProvinceList = DataSupport.findAll(Province.class);
        if (mProvinceList.size() > 0){
            dataList.clear();
            for (Province province :mProvinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            //光标默认在第一个
            mLv.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        }else{
          queryFromServe(baseUrl,PROVINCE);
        }
    }

    private void queryCities() {
        title.setText(selectedProvince.getProvinceName());
        back.setVisibility(View.VISIBLE);
        mCityList = DataSupport.where("provinceid = ?",String.valueOf(selectedProvince.getId())).find(City.class);
        if (mCityList.size() > 0){
            dataList.clear();
            for(City city : mCityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            mLv.setSelection(0);
            currentLevel = LEVEL_CITY;
        }else{
            String url = baseUrl + "/" +selectedProvince.getProvinceCode();
            queryFromServe(url,CITY);
        }
    }

    private void queryCounties() {
        title.setText(selectedCity.getCityName());
        back.setVisibility(View.VISIBLE);
        mCountyList = DataSupport.where("cityid = ?",String.valueOf(selectedCity.getId())).find(County.class);
        if (mCountyList.size() > 0){
            dataList.clear();
            for (County county : mCountyList){
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            mLv.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        }else{
            String url = baseUrl + "/" +selectedProvince.getProvinceCode() + "/" + selectedCity.getCityCode();
            queryFromServe(url,COUNTY);
        }
    }

    private void queryFromServe(String url, final String type) {
        HttpUtil.sendOkhttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if (PROVINCE.equals(type)){
                    result = Utility.handleProvinceRespondse(responseText);
                }else if (CITY.equals(type)){
                    result = Utility.handleCityRespondse(responseText,selectedProvince.getId());
                }else if (COUNTY.equals(type)){
                    result = Utility.handleCountyRespondse(responseText,selectedCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if (PROVINCE.equals(type)){
                               queryProvinces();
                            }else if (CITY.equals(type)){
                               queryCities();
                            }else if (COUNTY.equals(type)){
                               queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败...",Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }


}
