
package com.jelly.weather.city;

import android.net.Uri;
import android.provider.BaseColumns;

public interface CityColumns extends BaseColumns {
    static final Uri CONTENT_URI = Uri
            .parse("content://com.jelly.city/city");

    static final String PROVINCE = "province";
    static final String NAME = "name";
    static final String NUMBER = "number";
    static final String PINYIN = "pinyin";
    static final String PY = "py";
    static final String SELECTED = "selected";
    static final String RANK = "rank";
    static final String FLAG = "flag";

    static final String[] CITY_PROJECTION = {
            CityColumns._ID,
            CityColumns.PROVINCE,
            CityColumns.NAME,
            CityColumns.NUMBER,
            CityColumns.PINYIN,
            CityColumns.PY,
            CityColumns.SELECTED,
            CityColumns.RANK
    };

    public static final int ID_COMLUM = 0;
    public static final int PROVINCE_COMLUM = 1;
    public static final int NAME_COMLUM = 2;
    public static final int NUMBER_COMLUM = 3;
    public static final int PINYIN_COMLUM = 4;
    public static final int PY_COMLUM = 5;
    public static final int SELECTED_COMLUNS = 6;
    public static final int RANK_COMLUNS = 7;

}
