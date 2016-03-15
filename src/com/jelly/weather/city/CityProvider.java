
package com.jelly.weather.city;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import com.jelly.weather.city.CityDatabaseLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class CityProvider extends ContentProvider {
    private static final String AUTHORITY = "com.jelly.city";

    private static final int MATCH_CITY = 0;
    private static final int MATCH_CITY_ID = 1;
    private static final int SELECTED_MASK = 0x1;
    private static final int RANK_MASK = 0x11 << 1;

    private static final String TABLE_CITY = "city";
    private static final String SELECTED_MAPPING_STRING = CityColumns.FLAG + " & "
            + SELECTED_MASK;
    private static final String RANK_MAPPING_STRING = CityColumns.FLAG + " & "
            + RANK_MASK;

    static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static Map<String, String> sProjectionMap = new HashMap<String, String>();
    CityDatabseHelper mDbHelper;
    CountDownLatch mCountDownLatch;

    static {
        sUriMatcher.addURI(AUTHORITY, "city", MATCH_CITY);
        sUriMatcher.addURI(AUTHORITY, "city/#", MATCH_CITY_ID);
        sProjectionMap.put(CityColumns._ID, CityColumns._ID);
        sProjectionMap.put(CityColumns._COUNT, "count(" + CityColumns._ID);
        sProjectionMap.put(CityColumns.NAME, CityColumns.NAME);
        sProjectionMap.put(CityColumns.NUMBER, CityColumns.NUMBER);
        sProjectionMap.put(CityColumns.PY, CityColumns.PY);
        sProjectionMap.put(CityColumns.SELECTED, SELECTED_MAPPING_STRING);
        sProjectionMap.put(CityColumns.RANK, RANK_MAPPING_STRING);
    }

    @Override
    public boolean onCreate() {
        final Context context = getContext();
        mCountDownLatch = new CountDownLatch(1);
        CityDatabaseLoader loader = new CityDatabaseLoader(context, new LoadCompeleteListener() {

            @Override
            public void onLoadCompelete() {
                mDbHelper = new CityDatabseHelper(context);
                mCountDownLatch.countDown();

            }
        });
        loader.load();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        int match = sUriMatcher.match(uri);
        Cursor cursor = null;
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (match) {
            case MATCH_CITY:
                qb.setTables(TABLE_CITY);
                qb.setProjectionMap(sProjectionMap);
                cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Uri: " + uri + " is not supported");
        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = sUriMatcher.match(uri);
        Uri resutlt = null;
        SQLiteDatabase db = getWriteableDatabase();

        switch (match) {
            case MATCH_CITY:
                long id = db.insert(TABLE_CITY, null, values);
                resutlt = CityColumns.CONTENT_URI.buildUpon().appendPath("city")
                        .appendPath(String.valueOf(id)).build();
                break;
            default:
                throw new IllegalArgumentException("Uri: " + uri + " is not supported");
        }
        return resutlt;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        int count = 0;
        SQLiteDatabase db = getWriteableDatabase();
        switch (match) {
            case MATCH_CITY:
                count = db.delete(TABLE_CITY, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Uri: " + uri + " is not supported");
        }

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        int count = 0;
        SQLiteDatabase db = getWriteableDatabase();

        switch (match) {
            case MATCH_CITY:
                count = db.update(TABLE_CITY, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Uri: " + uri + " is not supported");
        }

        return count;
    }

    private void waitForInit() {
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private SQLiteDatabase getWriteableDatabase() {
        waitForInit();
        return mDbHelper.getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase() {
        waitForInit();
        return mDbHelper.getReadableDatabase();
    }
}
