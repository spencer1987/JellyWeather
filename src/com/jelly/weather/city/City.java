
package com.jelly.weather.city;

import android.content.Context;
import android.database.Cursor;

public class City {

    private static final String SELECTED_WHREE_CLAUSE = CityColumns.SELECTED + " =? ";

    public static Cursor getCityList(Context context) {
        return context.getContentResolver().query(CityColumns.CONTENT_URI,
                CityColumns.CITY_PROJECTION, null, null,
                CityColumns.PY);
    }

    public static Cursor getSelectedCityList(Context context) {
        return context.getContentResolver().query(CityColumns.CONTENT_URI,
                CityColumns.CITY_PROJECTION, SELECTED_WHREE_CLAUSE, new String[] {
                    "1"
                },
                CityColumns.RANK);
    }
}
