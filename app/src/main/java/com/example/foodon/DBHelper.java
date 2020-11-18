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
        MyDB.execSQL("create Table if not exists reviews(IMAGE_ID INTEGER primary key autoincrement, Image_URI TEXT, Description TEXT, Rating TEXT)");
        MyDB.execSQL("create Table if not exists orders(Order_ID INTEGER primary key autoincrement, Email_id TEXT, Orders TEXT, Delivery_Address TEXT, PRICE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists reviews");
        MyDB.execSQL("drop Table if exists orders");


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

    public Boolean insertReviews(String img_uri, String description, String Rating){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
//        contentValues.put("Image_Name", img_name);
        contentValues.put("Image_URI",img_uri);
        contentValues.put("Description", description);
        contentValues.put("Rating", Rating);
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

    public Boolean insertOrders(String orders, String price, String email_id){
        String delivery_address;

        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor c  = DB.rawQuery("select Location, Area from users where Emailid = ?",new String[]{email_id});
        c.moveToFirst();
        delivery_address = c.getString(0) + " " + c.getString(1);
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("Email_id", email_id);
        contentValues.put("Orders",orders);
        contentValues.put("Delivery_Address", delivery_address);
        contentValues.put("Price", price);
        long result = MyDB.insert("orders", null, contentValues);
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

    public Boolean updatePassword(String password, String emailid){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("Password", password);
        long result = MyDB.update("users", contentValues, "Emailid = ?", new String[]{emailid});

        if(result == -1) return false;
        else return true;
    }

    public Boolean updateContactNumber(String number, String emailid){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("Mobilenumber", number);
        long result = MyDB.update("users", contentValues, "Emailid = ?", new String[]{emailid});

        if(result == -1) return false;
        else return true;
    }

    public Boolean updateAddress(String Address, String emailid){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("Area", Address);
        long result = MyDB.update("users", contentValues, "Emailid = ?", new String[]{emailid});

        if(result == -1) return false;
        else return true;
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

