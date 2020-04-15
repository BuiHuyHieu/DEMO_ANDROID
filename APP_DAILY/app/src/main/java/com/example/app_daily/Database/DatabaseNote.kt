package com.example.app_daily.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.app_daily.Class.Note

public class DatabaseNote(context: Context) {
    private final val DATABASE_NAME="NOTE.db"
    private final val DATABASE_VERSION=1
    private final val TABLE_DATABSE="DataNote"
    private final val COLUMN_ID="id"
    private final val COLUMN_TITLE="title"
    private final val COLUMN_TIME="time"
    private final val COLUMN_PLACE="place"
    private final val COLUMN_DESCRIPTION="description"
    lateinit var dataBaseNote:DataBase
    lateinit var database:SQLiteDatabase

    init {
        dataBaseNote = DataBase(context,DATABASE_NAME,null,DATABASE_VERSION)
        database = dataBaseNote.openDatabase()
        val query = "CREATE TABLE IF NOT EXISTS $TABLE_DATABSE($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT, $COLUMN_TIME TEXT, $COLUMN_PLACE TEXT, $COLUMN_DESCRIPTION TEXT);"
        database.execSQL(query)
    }
    public fun insertData(title:String,time:String,place:String,des:String)
    {
        database = dataBaseNote.openDatabase()
        val contentValue = ContentValues()
        contentValue.put(COLUMN_TITLE,title)
        contentValue.put(COLUMN_TIME,time)
        contentValue.put(COLUMN_PLACE,place)
        contentValue.put(COLUMN_DESCRIPTION,des)
        database.insert(TABLE_DATABSE,null,contentValue)
        database.close()
    }

    public fun readData():ArrayList<Note>{
        var list: ArrayList<Note> = ArrayList()
        database = dataBaseNote.openDatabase()
        val query = "SELECT * FROM $TABLE_DATABSE"
        val cursor = database.rawQuery(query,null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast)
        {
            list.add(Note(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)))
            cursor.moveToNext()
        }
        database.close()
        return list
    }
    public fun deleteData(id: String)
    {
        database = dataBaseNote.openDatabase()
        database.delete(TABLE_DATABSE, "$COLUMN_ID =?", arrayOf(id))
        database.close()
    }
    public fun updateData(id:String,title: String,time: String,place: String,des: String)
    {
        val contentValue = ContentValues()
        contentValue.put(COLUMN_TITLE,title)
        contentValue.put(COLUMN_TIME,time)
        contentValue.put(COLUMN_PLACE,place)
        contentValue.put(COLUMN_DESCRIPTION,des)
        database  = dataBaseNote.openDatabase()
        database.update(TABLE_DATABSE,contentValue,"$COLUMN_ID=?", arrayOf(id))
        database.close()
    }

}