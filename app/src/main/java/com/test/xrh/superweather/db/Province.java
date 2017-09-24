package com.test.xrh.superweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by admin on 2017/9/24.
 */

public class Province extends DataSupport{
    //每个实体类中都应该有的字段
    private int id;
    //名字
    private String provinceName;
    //记录省的代号
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
