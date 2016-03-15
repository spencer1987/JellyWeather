
package com.jelly.weather.city;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CityDatabaseLoader {
    private static final String DATABASE_PATH = "data/data/com.jelly.weather/databases";
    private static final String DATABASE_FILE_NAME = "city.db";
    private AsyncTask<Void, Void, Void> mDatabaseLoadTask = new AsyncTask<Void, Void, Void>() {

        @Override
        protected Void doInBackground(Void... params) {
            File path = new File(DATABASE_PATH);
            if (!path.exists()) {
                path.mkdir();
            }

            File database = new File(DATABASE_PATH + File.pathSeparator + DATABASE_FILE_NAME);
            InputStream in = null;
            OutputStream out = null;
            try {
                if (!database.exists()) {
                    database.createNewFile();
                    in = mContext.getAssets().open("city.db");
                    out = new FileOutputStream(database);
                    byte[] buffer = new byte[1024 * 512];
                    int count;
                    while ((count = in.read(buffer)) != -1) {
                        out.write(buffer, 0, count);
                    }
                    out.flush();
                }

            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {

                try {
                    if (in != null) {
                        in.close();
                    }

                    if (out != null) {
                        out.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        protected void onPostExecute(Void result) {
            mListener.onLoadCompelete();
        };
    };

    Context mContext;
    LoadCompeleteListener mListener;

    public CityDatabaseLoader(Context context, LoadCompeleteListener loadCompeleteListener) {
        mContext = context;
        mListener = loadCompeleteListener;
    }

    public void load() {
        mDatabaseLoadTask.execute();
    }

}
