package com.info.controls;

import android.content.Context;

import com.info.Helpers.DiveStyleSpinner;
import com.info.sqlite.helper.ArmstandPlatformDatabase;
import com.info.sqlite.helper.BackDatabase;
import com.info.sqlite.helper.BackPlatformDatabase;
import com.info.sqlite.helper.ForwardDatabase;
import com.info.sqlite.helper.ForwardPlatformDatabase;
import com.info.sqlite.helper.InwardDatabase;
import com.info.sqlite.helper.InwardPlatformDatabase;
import com.info.sqlite.helper.ReverseDatabase;
import com.info.sqlite.helper.ReversePlatformDatabase;
import com.info.sqlite.helper.TwistDatabase;
import com.info.sqlite.helper.TwistPlatformDatabase;

import java.util.ArrayList;

public class DiveChooseController {

    public double GetMultiplier(String stringId, int diveType, int divePosition, double boardType, Context context){

        double multiplier = 0;
        int diveId;

        switch (diveType) {
            case 1:
                ForwardDatabase fdb = new ForwardDatabase(context);
                diveId = fdb.getDiveId(stringId);
                multiplier = fdb.getDOD(diveId, divePosition, boardType);
                break;
            case 2:
                BackDatabase bdb = new BackDatabase(context);
                diveId = bdb.getDiveId(stringId);
                multiplier = bdb.getDOD(diveId, divePosition, boardType);
                break;
            case 3:
                ReverseDatabase rdb = new ReverseDatabase(context);
                diveId = rdb.getDiveId(stringId);
                multiplier = rdb.getDOD(diveId, divePosition, boardType);
                break;
            case 4:
                InwardDatabase idb = new InwardDatabase(context);
                diveId = idb.getDiveId(stringId);
                multiplier = idb.getDOD(diveId, divePosition, boardType);
                break;
            case 5:
                TwistDatabase tdb = new TwistDatabase(context);
                diveId = tdb.getDiveId(stringId);
                multiplier = tdb.getDOD(diveId, divePosition, boardType);
                break;
            case 6:
                ForwardPlatformDatabase fpdb = new ForwardPlatformDatabase(context);
                diveId = fpdb.getDiveId(stringId);
                multiplier = fpdb.getDOD(diveId, divePosition, boardType);
                break;
            case 7:
                BackPlatformDatabase bpdb = new BackPlatformDatabase(context);
                diveId = bpdb.getDiveId(stringId);
                multiplier = bpdb.getDOD(diveId, divePosition, boardType);
                break;
            case 8:
                ReversePlatformDatabase rpdb = new ReversePlatformDatabase(context);
                diveId = rpdb.getDiveId(stringId);
                multiplier = rpdb.getDOD(diveId, divePosition, boardType);
                break;
            case 9:
                InwardPlatformDatabase ipdb = new InwardPlatformDatabase(context);
                diveId = ipdb.getDiveId(stringId);
                multiplier = ipdb.getDOD(diveId, divePosition, boardType);
                break;
            case 10:
                TwistPlatformDatabase tpdb = new TwistPlatformDatabase(context);
                diveId = tpdb.getDiveId(stringId);
                multiplier = tpdb.getDOD(diveId, divePosition, boardType);
                break;
            case 11:
                ArmstandPlatformDatabase apdb = new ArmstandPlatformDatabase(context);
                diveId = apdb.getDiveId(stringId);
                multiplier = apdb.getDOD(diveId, divePosition, boardType);
                break;
        }

        return multiplier;
    }

    public ArrayList<Double> GetTheDD(String stringId, int diveType, double boardType, Context context) {

        ArrayList<Double> ddList = new ArrayList<>();
        int diveId;
        double testS = 0.0;
        double testP = 0.0;
        double testT = 0.0;
        double testF = 0.0;

        switch (diveType) {
            case 1:
                ForwardDatabase fdb = new ForwardDatabase(context);
                diveId = fdb.getDiveId(stringId);
                testS = fdb.getDOD(diveId, 1, boardType);
                testP = fdb.getDOD(diveId, 2, boardType);
                testT = fdb.getDOD(diveId, 3, boardType);
                testF = fdb.getDOD(diveId, 4, boardType);
                break;
            case 2:
                BackDatabase bdb = new BackDatabase(context);
                diveId = bdb.getDiveId(stringId);
                testS = bdb.getDOD(diveId, 1, boardType);
                testP = bdb.getDOD(diveId, 2, boardType);
                testT = bdb.getDOD(diveId, 3, boardType);
                testF = bdb.getDOD(diveId, 4, boardType);
                break;
            case 3:
                ReverseDatabase rdb = new ReverseDatabase(context);
                diveId = rdb.getDiveId(stringId);
                testS = rdb.getDOD(diveId, 1, boardType);
                testP = rdb.getDOD(diveId, 2, boardType);
                testT = rdb.getDOD(diveId, 3, boardType);
                testF = rdb.getDOD(diveId, 4, boardType);
                break;
            case 4:
                InwardDatabase idb = new InwardDatabase(context);
                diveId = idb.getDiveId(stringId);
                testS = idb.getDOD(diveId, 1, boardType);
                testP = idb.getDOD(diveId, 2, boardType);
                testT = idb.getDOD(diveId, 3, boardType);
                testF = idb.getDOD(diveId, 4, boardType);
                break;
            case 5:
                TwistDatabase tdb = new TwistDatabase(context);
                diveId = tdb.getDiveId(stringId);
                testS = tdb.getDOD(diveId, 1, boardType);
                testP = tdb.getDOD(diveId, 2, boardType);
                testT = tdb.getDOD(diveId, 3, boardType);
                testF = tdb.getDOD(diveId, 4, boardType);
                break;
            case 6:
                ForwardPlatformDatabase fpdb = new ForwardPlatformDatabase(context);
                diveId = fpdb.getDiveId(stringId);
                testS = fpdb.getDOD(diveId, 1, boardType);
                testP = fpdb.getDOD(diveId, 2, boardType);
                testT = fpdb.getDOD(diveId, 3, boardType);
                testF = fpdb.getDOD(diveId, 4, boardType);
                break;
            case 7:
                BackPlatformDatabase bpdb = new BackPlatformDatabase(context);
                diveId = bpdb.getDiveId(stringId);
                testS = bpdb.getDOD(diveId, 1, boardType);
                testP = bpdb.getDOD(diveId, 2, boardType);
                testT = bpdb.getDOD(diveId, 3, boardType);
                testF = bpdb.getDOD(diveId, 4, boardType);
                break;
            case 8:
                ReversePlatformDatabase rpdb = new ReversePlatformDatabase(context);
                diveId = rpdb.getDiveId(stringId);
                testS = rpdb.getDOD(diveId, 1, boardType);
                testP = rpdb.getDOD(diveId, 2, boardType);
                testT = rpdb.getDOD(diveId, 3, boardType);
                testF = rpdb.getDOD(diveId, 4, boardType);
                break;
            case 9:
                InwardPlatformDatabase ipdb = new InwardPlatformDatabase(context);
                diveId = ipdb.getDiveId(stringId);
                testS = ipdb.getDOD(diveId, 1, boardType);
                testP = ipdb.getDOD(diveId, 2, boardType);
                testT = ipdb.getDOD(diveId, 3, boardType);
                testF = ipdb.getDOD(diveId, 4, boardType);
                break;
            case 10:
                TwistPlatformDatabase tpdb = new TwistPlatformDatabase(context);
                diveId = tpdb.getDiveId(stringId);
                testS = tpdb.getDOD(diveId, 1, boardType);
                testP = tpdb.getDOD(diveId, 2, boardType);
                testT = tpdb.getDOD(diveId, 3, boardType);
                testF = tpdb.getDOD(diveId, 4, boardType);
                break;
            case 11:
                ArmstandPlatformDatabase apdb = new ArmstandPlatformDatabase(context);
                diveId = apdb.getDiveId(stringId);
                testS = apdb.getDOD(diveId, 1, boardType);
                testP = apdb.getDOD(diveId, 2, boardType);
                testT = apdb.getDOD(diveId, 3, boardType);
                testF = apdb.getDOD(diveId, 4, boardType);
                break;
        }

        ddList.add(testS);
        ddList.add(testP);
        ddList.add(testT);
        ddList.add(testF);

        return ddList;
    }

    public int SetDiveTypeNumber(int position, double boardType) {

        int diveType = 0;

        if(boardType == 1.0 || boardType == 3.0) {
            switch (position) {
                case 1:
                    diveType = 1;
                    break;
                case 2:
                    diveType = 2;
                    break;
                case 3:
                    diveType = 3;
                    break;
                case 4:
                    diveType = 4;
                    break;
                case 5:
                    diveType = 5;
                    break;
            }
        } else {
            switch (position) {
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
    
    public ArrayList<DiveStyleSpinner> GetDiveCat(int diveType, double boardType, Context context) {
        
        ArrayList<DiveStyleSpinner> searchDives = null;

        switch (diveType) {
            // Springboard Dives
            case 1:
                ForwardDatabase dbf = new ForwardDatabase(context);
                if (boardType == 1) {
                    searchDives = dbf.getForwardOneNames();
                    break;
                } else {
                    searchDives = dbf.getForwardThreeNames();
                    break;
                }
            case 2:
                BackDatabase dbb = new BackDatabase(context);
                if (boardType == 1) {
                    searchDives = dbb.getBackOneNames();
                    break;
                } else {
                    searchDives = dbb.getBackThreeNames();
                    break;
                }
            case 3:
                ReverseDatabase dbr = new ReverseDatabase(context);
                if (boardType == 1) {
                    searchDives = dbr.getReverseOneNames();
                    break;
                } else {
                    searchDives = dbr.getReverseThreeNames();
                    break;
                }
            case 4:
                InwardDatabase dbi = new InwardDatabase(context);
                if (boardType == 1) {
                    searchDives = dbi.getInwardOneNames();
                    break;
                } else {
                    searchDives = dbi.getInwardThreeNames();
                    break;
                }
            case 5:
                TwistDatabase dbt = new TwistDatabase(context);
                if (boardType == 1) {
                    searchDives = dbt.getTwistOneNames();
                    break;
                } else {
                    searchDives = dbt.getTwistThreeNames();
                    break;
                }
                //platform dives
            case 6:
                ForwardPlatformDatabase dbfp = new ForwardPlatformDatabase(context);
                if (boardType == 10) {
                    searchDives = dbfp.getFrontPlatformTenNames();
                    break;
                } else if (boardType == 7.5) {
                    searchDives = dbfp.getFrontPlatformSevenFiveNames();
                    break;
                } else {
                    searchDives = dbfp.getFrontPlatformFiveNames();
                    break;
                }
            case 7:
                BackPlatformDatabase dbbp = new BackPlatformDatabase(context);
                if (boardType == 10) {
                    searchDives = dbbp.getBackPlatformTenNames();
                    break;
                } else if (boardType == 7.5) {
                    searchDives = dbbp.getBackPlatformSevenFiveNames();
                    break;
                } else {
                    searchDives = dbbp.getBackPlatformFiveNames();
                    break;
                }
            case 8:
                ReversePlatformDatabase dbrp = new ReversePlatformDatabase(context);
                if (boardType == 10) {
                    searchDives = dbrp.getReversePlatformTenNames();
                    break;
                } else if (boardType == 7.5) {
                    searchDives = dbrp.getReversePlatformSevenFiveNames();
                    break;
                } else {
                    searchDives = dbrp.getReversePlatformFiveNames();
                    break;
                }
            case 9:
                InwardPlatformDatabase dbip = new InwardPlatformDatabase(context);
                if (boardType == 10) {
                    searchDives = dbip.getInwardPlatformTenNames();
                    break;
                } else if (boardType == 7.5) {
                    searchDives = dbip.getInwardPlatformSevenFiveNames();
                    break;
                } else {
                    searchDives = dbip.getInwardPlatformFiveNames();
                    break;
                }
            case 10:
                TwistPlatformDatabase dbtp = new TwistPlatformDatabase(context);
                if (boardType == 10) {
                    searchDives = dbtp.getTwistPlatformTenNames();
                    break;
                } else if (boardType == 7.5) {
                    searchDives = dbtp.getTwistPlatformSevenFiveNames();
                    break;
                } else {
                    searchDives = dbtp.getTwistPlatformFiveNames();
                    break;
                }
            case 11:
                ArmstandPlatformDatabase dbap = new ArmstandPlatformDatabase(context);
                if (boardType == 10) {
                    searchDives = dbap.getArmstandTenNames();
                    break;
                } else if (boardType == 7.5) {
                    searchDives = dbap.getArmstandSevenFiveNames();
                    break;
                } else {
                    searchDives = dbap.getArmstandFiveNames();
                    break;
                }
        }

        return searchDives;

    }
}
