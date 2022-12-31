package com.expresstemplate.dictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataManager {
    DataBaseHelper myDbHelper;
    SQLiteDatabase db;

    public DataManager(Context context) {
        myDbHelper = new DataBaseHelper(context);
        myDbHelper.createDataBase();
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        db = myDbHelper.getReadableDatabase();
    }

    public List<Word> getAllData() {
        Cursor cur = db.rawQuery("select * from tblDictionary LIMIT 5000;", null);
        List<Word> item_data = new ArrayList<Word>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Word obj = new Word();
                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.word = cur.getString(cur.getColumnIndex("word"));
                    obj.is_fav = cur.getString(cur.getColumnIndex("is_favourite"));
                    if (obj.is_fav != null) {
                    } else {
                        obj.is_fav = "0";
                    }
                    obj.meaning = cur.getString(cur.getColumnIndex("meaning"));

                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        return item_data;
    }

    public List<Word> getAllSearchData(String search) {
        Cursor cur = db.rawQuery("SELECT * FROM tblDictionary WHERE Word LIKE '" + search + "%' LIMIT 30", null);
        List<Word> item_data = new ArrayList<Word>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Word obj = new Word();
                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.word = cur.getString(cur.getColumnIndex("word"));
                    obj.is_fav = cur.getString(cur.getColumnIndex("is_favourite"));
                    if (obj.is_fav != null) {
                    } else {
                        obj.is_fav = "0";
                    }
                    obj.meaning = cur.getString(cur.getColumnIndex("meaning"));

                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        return item_data;
    }

    public Boolean InsertWord(String strWord, String strMeaning, String strAddWord) {
        String insertQuery = "INSERT INTO tblDictionary(Word,Meaning,is_added) values('" + strWord + "','"
                + strMeaning + "','" + strAddWord + "')";
        db.execSQL(insertQuery);
        //db.close();
        return true;
    }


    public Word DayOfWord() {
        Cursor cur = db.rawQuery("SELECT * FROM tblDictionary ORDER BY RANDOM() LIMIT 1 ;", null);
        Word objWord = new Word();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    objWord.meaning = cur.getString(cur.getColumnIndex("id"));
                    objWord.word = cur.getString(cur.getColumnIndex("word"));
                    objWord.meaning = cur.getString(cur.getColumnIndex("meaning"));
                    objWord.is_fav = cur.getString(cur.getColumnIndex("is_favourite"));
                    if (objWord.is_fav != null) {
                    } else {
                        objWord.is_fav = "0";
                    }


                } while (cur.moveToNext());
            }
        }
        return objWord;
    }

    public void FavouriteWord(String id) {
        String updateQuery = "UPDATE tblDictionary SET is_favourite='" + 1 + "' WHERE Id=" + id;
        SQLiteDatabase db = this.myDbHelper.getWritableDatabase();
        db.execSQL(updateQuery);
        db.close();
    }

    public void UnFavouriteWord(String id) {
        String updateQuery = "UPDATE tblDictionary SET is_favourite='' WHERE Id=" + id;
        SQLiteDatabase db = this.myDbHelper.getWritableDatabase();
        db.execSQL(updateQuery);
        db.close();
    }

    public List<Word> getAllFavouriteData() {
        Cursor cur = db.rawQuery("select * from tblDictionary where is_favourite=1;", null);
        List<Word> item_data = new ArrayList<Word>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Word obj = new Word();
                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.word = cur.getString(cur.getColumnIndex("word"));
                    if (obj.is_fav != null) {
                    } else {
                        obj.is_fav = "1";
                    }
                    obj.meaning = cur.getString(cur.getColumnIndex("meaning"));

                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        return item_data;
    }

    public void InsertHistoryWord(String word, String meaning) {
        String insertQuery = "INSERT INTO tblHistory(word,meaning) values('" + word + "','" + meaning + "')";
        db.execSQL(insertQuery);
        db.close();
    }

    public List<Word> getAllHistoryData() {
        Cursor cur = db.rawQuery("select * from tblHistory;", null);
        List<Word> item_data = new ArrayList<Word>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Word obj = new Word();
                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.word = cur.getString(cur.getColumnIndex("word"));
                    obj.meaning = cur.getString(cur.getColumnIndex("meaning"));

                    item_data.add(obj);
                    Collections.reverse(item_data);
                } while (cur.moveToNext());
            }
        }
        return item_data;
    }

    public List<Word> getDeleteHistoryData() {
        List<Word> item_data = new ArrayList<Word>();
        String deleteQuery = "delete from tblHistory";
        db.execSQL(deleteQuery);
        db.close();
        return item_data;
    }

    public List<Word> Quiz() {
        Cursor cur = db.rawQuery("SELECT * FROM tblDictionary ORDER BY RANDOM() LIMIT 4 ;", null);
        List<Word> item_data = new ArrayList<Word>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Word obj = new Word();
                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.word = cur.getString(cur.getColumnIndex("word"));
                    obj.meaning = cur.getString(cur.getColumnIndex("meaning"));

                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        return item_data;
    }

    public List<Word> getDeleteFavouriteData() {

        List<Word> item_data = new ArrayList<Word>();
        String deleteQuery = "delete from tblDictionary where is_favourite";
        db.execSQL(deleteQuery);
        db.close();
        return item_data;
    }

    public List<Word> getAllAddWord() {
        Cursor cur = db.rawQuery("select * from tblDictionary where is_added=1;", null);
        List<Word> item_data = new ArrayList<Word>();
        if (cur.getCount() != 0) {
            if (cur.moveToFirst()) {
                do {
                    Word obj = new Word();
                    obj.id = cur.getString(cur.getColumnIndex("id"));
                    obj.word = cur.getString(cur.getColumnIndex("word"));
                    obj.is_fav = cur.getString(cur.getColumnIndex("is_favourite"));
                    if (obj.is_fav != null) {
                    } else {
                        obj.is_fav = "0";
                    }
                    obj.meaning = cur.getString(cur.getColumnIndex("meaning"));

                    obj.is_add = cur.getString(cur.getColumnIndex("is_added"));
                    if (obj.is_add != null) {
                    } else {
                        obj.is_add = "1";
                    }
                    item_data.add(obj);
                } while (cur.moveToNext());
            }
        }
        return item_data;
    }

    public Boolean DeleteWord(String id) {
        String updateQuery = "delete from tblDictionary WHERE Id=" + id;
        SQLiteDatabase db = this.myDbHelper.getWritableDatabase();
        db.execSQL(updateQuery);
        db.close();
        return true;
    }

    public Boolean UpdateAddWord(String strWord, String strMeaning, String id) {
        String updateQuery = "UPDATE tblDictionary SET word='" + strWord + "' , meaning='" + strMeaning + "' WHERE Id=" + id;
        SQLiteDatabase db = this.myDbHelper.getWritableDatabase();
        db.execSQL(updateQuery);
        db.close();
        return true;
    }

    public int getCount() {
        String countQuery = "SELECT  * FROM " + "tblDictionary";
        SQLiteDatabase db = this.myDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public List<Word> getDeleteAddedWord() {
        List<Word> item_data = new ArrayList<Word>();
        String deleteQuery = "delete from tblDictionary where is_added";
        db.execSQL(deleteQuery);
        db.close();
        return item_data;

    }
}
