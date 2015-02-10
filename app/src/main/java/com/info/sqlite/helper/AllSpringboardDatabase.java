package com.info.sqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AllSpringboardDatabase extends DatabaseHelper {
    
    public AllSpringboardDatabase(Context context) {
        super(context);
    }

    //----------------------get the dod for the dive-------------------------------//
    public double getDOD(int diveid, int diveposition, double boardtype) {
        double dod = 0.0;
        String selectQuery = null;
        if(boardtype == 1) {
            switch (diveposition) {
                case 1:
                    selectQuery = "SELECT oneS FROM " +  TABLE_ALL_SPRINGBOARD_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 2:
                    selectQuery = "SELECT oneP FROM " + TABLE_ALL_SPRINGBOARD_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 3:
                    selectQuery = "SELECT oneT FROM " + TABLE_ALL_SPRINGBOARD_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 4:
                    selectQuery = "SELECT oneF FROM " + TABLE_ALL_SPRINGBOARD_DIVES
                            + " WHERE id= " + diveid;
                    break;
            }
        } else {
            switch (diveposition) {
                case 1:
                    selectQuery = "SELECT threeS FROM " + TABLE_ALL_SPRINGBOARD_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 2:
                    selectQuery = "SELECT threeP FROM " + TABLE_ALL_SPRINGBOARD_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 3:
                    selectQuery = "SELECT threeT FROM " + TABLE_ALL_SPRINGBOARD_DIVES
                            + " WHERE id= " + diveid;
                    break;
                case 4:
                    selectQuery = "SELECT threeF FROM " + TABLE_ALL_SPRINGBOARD_DIVES
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

        String selectQuery = "SELECT name FROM " + TABLE_ALL_SPRINGBOARD_DIVES
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
