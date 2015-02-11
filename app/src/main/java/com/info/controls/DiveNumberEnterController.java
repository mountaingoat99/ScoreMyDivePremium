package com.info.controls;


import android.content.Context;

import com.info.sqlite.helper.AllPlatformDatabase;
import com.info.sqlite.helper.AllSpringboardDatabase;

public class DiveNumberEnterController {

    public double GetMultiplier(int id, int divePosition, double boardType, Context context) {

        double multiplier = 0;

        if (boardType == 1.0 || boardType == 3.0) {

            AllSpringboardDatabase db = new AllSpringboardDatabase(context);
            multiplier = db.getDOD(id, divePosition, boardType);

        } else {

            AllPlatformDatabase db = new AllPlatformDatabase(context);
            multiplier = db.getDOD(id, divePosition, boardType);
        }
        return multiplier;
    }

    public String GetDiveType(int id, double boardType) {

        int testDiveType = 0;
        String diveType = null;

        if (boardType == 1.0 || boardType == 3.0) {

            if (testDiveType < 1 || testDiveType > 5) {
                testDiveType = firstDigit(id);
                switch (testDiveType) {
                    case 1:
                        diveType = "Forward Dive";
                        break;
                    case 2:
                        diveType = "Back Dive";
                        break;
                    case 3:
                        diveType = "Reverse Dive";
                        break;
                    case 4:
                        diveType = "Inward Dive";
                        break;
                    case 5:
                        diveType = "Twist Dive";
                        break;
                }
            } else {
                return "Not Valid";
            }

        } else {

            testDiveType = firstDigit(id);
            if (testDiveType < 1 || testDiveType > 6) {

                switch (testDiveType) {
                    case 1:
                        diveType = "Front Platform Dives";
                        break;
                    case 2:
                        diveType = "Back Platform Dives";
                        break;
                    case 3:
                        diveType = "Reverse Platform Dives";
                        break;
                    case 4:
                        diveType = "Inward Platform Dives";
                        break;
                    case 5:
                        diveType = "Twist Platform Dives";
                        break;
                    case 6:
                        diveType = "Armstand Platform Dives";
                        break;
                }
            } else {
                return "Not Valid";
            }
        }
        return diveType;
    }

    public String GetDiveName(int id, double boardType, Context context) {

        String diveName;

        if(boardType == 1.0 || boardType == 3.0){

            AllSpringboardDatabase db = new AllSpringboardDatabase(context);
            diveName = db.getDiveName(id);

        } else {

            AllPlatformDatabase db = new AllPlatformDatabase(context);
            diveName = db.getDiveName(id);

        }
        return diveName;
    }

    public static int firstDigit(int n) {
        while (n < -9 || 9 < n) n /= 10;
        return Math.abs(n);
    }
}
