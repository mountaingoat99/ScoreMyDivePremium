package com.info.sqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AllPlatformDatabase extends DatabaseHelper {

    public  AllPlatformDatabase(Context context) {
        super(context);
    }

    //----------------------get the dod for the dive-------------------------------//
    public double getDOD(int diveid, int diveposition, double boardtype) {
        double dod = 0.0;
        String selectQuery = null;
        if(boardtype == 10.0) {
            switch (diveposition) {
                case 1:
                    selectQuery = "SELECT tenS FROM " + TABLE_ALL_PLATFORM_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 2:
                    selectQuery = "SELECT tenP FROM " + TABLE_ALL_PLATFORM_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 3:
                    selectQuery = "SELECT tenT FROM " + TABLE_ALL_PLATFORM_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 4:
                    selectQuery = "SELECT tenF FROM " + TABLE_ALL_PLATFORM_DIVES
                            + " WHERE id= " + diveid;
                    break;
            }
        }
        if (boardtype == 7.5) {
            switch (diveposition) {
                case 1:
                    selectQuery = "SELECT seven_fiveS FROM " + TABLE_ALL_PLATFORM_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 2:
                    selectQuery = "SELECT seven_fiveP FROM " + TABLE_ALL_PLATFORM_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 3:
                    selectQuery = "SELECT seven_fiveT FROM " + TABLE_ALL_PLATFORM_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 4:
                    selectQuery = "SELECT seven_fiveF FROM " + TABLE_ALL_PLATFORM_DIVES
                            + " WHERE id= " + diveid;
                    break;
            }
        }
        if (boardtype == 5.0){
            switch (diveposition) {
                case 1:
                    selectQuery = "SELECT fiveS FROM " + TABLE_ALL_PLATFORM_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 2:
                    selectQuery = "SELECT fiveP FROM " + TABLE_ALL_PLATFORM_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 3:
                    selectQuery = "SELECT fiveT FROM " + TABLE_ALL_PLATFORM_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 4:
                    selectQuery = "SELECT fiveF FROM " + TABLE_ALL_PLATFORM_DIVES
                            + " WHERE id= " + diveid;
                    break;
            }
        }
        Log.e(getLog(), selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0 && c.moveToFirst()){
            do{
                dod = c.getDouble(0);
            }while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return  dod;
    }

    //------------------------get the dive name------------------------------------//
    public String getDiveName(int diveid){
        String diveName = null;

        String selectQuery = "SELECT name FROM " + TABLE_ALL_PLATFORM_DIVES
                + " WHERE id= " + diveid;

        Log.e(getLog(), selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0 && c.moveToFirst()){
            do{
                diveName = c.getString(0);
            }while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return  diveName;
    }
}
