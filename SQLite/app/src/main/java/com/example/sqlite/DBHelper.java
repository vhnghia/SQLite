    package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    DBHelper(Context context){
        super(context,"book_list", null,1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Book(id Integer primary key , "+" title text, author text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Book");
    }

    public boolean InsertBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",book.getId());
        contentValues.put("title",book.getTitle());
        contentValues.put("author",book.getAuthor());
        db.insert("Book",null,contentValues);
        return true;
    }

    public Book getBook(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Book where id = " +id,null);
        cursor.moveToFirst();
        Book book = new Book(cursor.getString(0),cursor.getString(1),cursor.getString(2));
        cursor.close();
        db.close();
        return book;
    }

    public ArrayList<Book> AllBook(){
        ArrayList<Book> bookArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Book",null);
        if(cursor!=null)
            cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            bookArrayList.add(new Book(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return bookArrayList;
    }

    public boolean Delete(String id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Book",null);
        int i = cursor.getCount();
        db.execSQL("delete from Book where id="+id);
        Cursor cursor1 = db.rawQuery("select * from Book",null);
        int k = cursor1.getCount();

        if(i==k)
            return false;
        else
            return true;
    }

    public boolean Update(String author,String id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Book SET author='"+author+"' WHERE id=' "+id+" ' ");
        return true;
    }

}
