package com.example.foodon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "FoodOn.db";
    public DBHelper(Context context) {
        super(context, "FoodOn.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table if not exists users(ID INTEGER primary key autoincrement,Emailid TEXT, Fname TEXT, Lname TEXT, Password TEXT, Mobilenumber TEXT, Location TEXT, Area TEXT)");
        MyDB.execSQL("create Table if not exists reviews(IMAGE_ID INTEGER primary key autoincrement, Image_URI TEXT, Description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists reviews");

        onCreate(MyDB);
    }

    public Boolean insertData(String Emailid, String Fname, String Lname, String Password, String Mobilenumber, String Location, String Area ){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("Emailid", Emailid);
        contentValues.put("Fname", Fname);
        contentValues.put("Lname", Lname);
        contentValues.put("Password", Password);
        contentValues.put("Mobilenumber", Mobilenumber);
        contentValues.put("Location", Location);
        contentValues.put("Area", Area);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1)
        {
            return false;
        }
        else
            {
            return true;
            }
    }

    public Boolean insertReviews(String img_uri, String description){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
//        contentValues.put("Image_Name", img_name);
        contentValues.put("Image_URI",img_uri);
        contentValues.put("Description", description);
        long result = MyDB.insert("reviews", null, contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public Boolean checkEmailid(String Emailid) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where Emailid = ?", new String[]{Emailid});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkEmailpassword(String Emailid, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor =  MyDB.rawQuery("Select * from users where Emailid = ? and password = ?", new String[] {Emailid,password});

        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }


    public String getLoginData(String email,String password)
    {
        SQLiteDatabase sql=this.getReadableDatabase();
        String query=" select * from users "+ "where Emailid ='"+email+"' and Password='"+password+"'";
        Cursor cursor =sql.rawQuery(query,null);
        cursor.moveToFirst();
        String count = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)));
        return count;

    }
}

