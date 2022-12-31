package com.expresstemplate.dictionary;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static String DB_PATH;
    public final Context context;
    public SQLiteDatabase database;
//    public static String DB_NAME = "database.sqlite";
    public static String DB_NAME = "Database";
    //public static String DB_NAME = "Database.zip";

    public SQLiteDatabase getDb() {
        return this.database;
    }

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        DB_PATH = String.format("//data//data//%s//databases//", context.getPackageName());
        openDataBase();
    }

    public void createDataBase() {
        if (checkDataBase()) {
            Log.i(getClass().toString(), "database.sqlite already exists");
            return;
        }
        getReadableDatabase();
        try {
            copyDataBase();
        } catch (IOException e) {
            Log.e(getClass().toString(), "Copying error");
            throw new Error("Error copying database!");
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        try {
            checkDb = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, 1);
        } catch (SQLException e) {
            Log.e(getClass().toString(), "Error while checking db");
        }
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;
    }

    private void copyDataBase() throws IOException {
        InputStream externalDbStream = this.context.getAssets().open(DB_NAME);
        OutputStream localDbStream = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] buffer = new byte[1024];
        while (true) {
            int bytesRead = externalDbStream.read(buffer);
            if (bytesRead > 0) {
                localDbStream.write(buffer, 0, bytesRead);
            } else {
                localDbStream.close();
                externalDbStream.close();
                return;
            }
        }
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (this.database == null) {
            createDataBase();
            this.database = SQLiteDatabase.openDatabase(path, null, 0);
        }
        return this.database;
    }

    public synchronized void close() {
        if (this.database != null) {
            this.database.close();
        }
        super.close();
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
