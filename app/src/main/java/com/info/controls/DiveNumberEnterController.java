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

    public double GetDiveType(int id, double boardType) {

        int testDiveType = 0;
        int diveType = 0;

        if (boardType == 1.0 || boardType == 3.0) {

            testDiveType = firstDigit(id);
            if (testDiveType < 1 || testDiveType > 5)
                return 0;
            diveType = testDiveType;

        } else {

            testDiveType = firstDigit(id);
            if (testDiveType < 1 || testDiveType > 6)
                return 0;
            switch (testDiveType) {
                case 1:
                    diveType = 6;
                    break;
                case 2:
                    diveType = 7;
                    break;
                case 3:
                    diveType = 8;
                    break;
                case 4:
                    diveType = 9;
                    break;
                case 5:
                    diveType = 10;
                    break;
                case 6:
                    diveType = 11;
                    break;
            }
        }
        return diveType;
    }

    public String GetDiveName(int id, double boardType, Context context) {

        String diveName = null;

        if(boardType == 1.0 || boardType == 3.0){

            AllSpringboardDatabase db = new AllSpringboardDatabase(context);
            diveName = db.getDiveName(id);

        } else {

            AllPlatformDatabase db = new AllPlatformDatabase(context);
            diveName = db.getDiveName(id);

        }
        return diveName;
    }

    private static int firstDigit(int n) {
        while (n < -9 || 9 < n) n /= 10;
        return Math.abs(n);
    }
}
