
package com.jelly.weather.city;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CityDatabseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "city.db";
    private static final int DB_VERSION = 100;

    public CityDatabseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
