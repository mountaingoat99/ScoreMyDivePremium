package com.info.controls;

import android.os.AsyncTask;

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

    public double GetMultiplier(String stringId, int diveType, int divePosition, double boardType){

        double multiplier = 0;
        int diveId;

        switch (diveType) {
            case 1:
                ForwardDatabase fdb = new ForwardDatabase();
                diveId = fdb.getDiveId(stringId);
                multiplier = fdb.getDOD(diveId, divePosition, boardType);
                break;
            case 2:
                BackDatabase bdb = new BackDatabase();
                diveId = bdb.getDiveId(stringId);
                multiplier = bdb.getDOD(diveId, divePosition, boardType);
                break;
            case 3:
                ReverseDatabase rdb = new ReverseDatabase();
                diveId = rdb.getDiveId(stringId);
                multiplier = rdb.getDOD(diveId, divePosition, boardType);
                break;
            case 4:
                InwardDatabase idb = new InwardDatabase();
                diveId = idb.getDiveId(stringId);
                multiplier = idb.getDOD(diveId, divePosition, boardType);
                break;
            case 5:
                TwistDatabase tdb = new TwistDatabase();
                diveId = tdb.getDiveId(stringId);
                multiplier = tdb.getDOD(diveId, divePosition, boardType);
                break;
            case 6:
                ForwardPlatformDatabase fpdb = new ForwardPlatformDatabase();
                diveId = fpdb.getDiveId(stringId);
                multiplier = fpdb.getDOD(diveId, divePosition, boardType);
                break;
            case 7:
                BackPlatformDatabase bpdb = new BackPlatformDatabase();
                diveId = bpdb.getDiveId(stringId);
                multiplier = bpdb.getDOD(diveId, divePosition, boardType);
                break;
            case 8:
                ReversePlatformDatabase rpdb = new ReversePlatformDatabase();
                diveId = rpdb.getDiveId(stringId);
                multiplier = rpdb.getDOD(diveId, divePosition, boardType);
                break;
            case 9:
                InwardPlatformDatabase ipdb = new InwardPlatformDatabase();
                diveId = ipdb.getDiveId(stringId);
                multiplier = ipdb.getDOD(diveId, divePosition, boardType);
                break;
            case 10:
                TwistPlatformDatabase tpdb = new TwistPlatformDatabase();
                diveId = tpdb.getDiveId(stringId);
                multiplier = tpdb.getDOD(diveId, divePosition, boardType);
                break;
            case 11:
                ArmstandPlatformDatabase apdb = new ArmstandPlatformDatabase();
                diveId = apdb.getDiveId(stringId);
                multiplier = apdb.getDOD(diveId, divePosition, boardType);
                break;
        }

        return multiplier;
    }

    public ArrayList<Double> GetTheDD(String stringId, int diveType, double boardType) {

        ArrayList<Double> ddList = null;
        int diveId;
        double testS = 0.0;
        double testP = 0.0;
        double testT = 0.0;
        double testF = 0.0;

        switch (diveType) {
            case 1:
                ForwardDatabase fdb = new ForwardDatabase();
                diveId = fdb.getDiveId(stringId);
                testS = fdb.getDOD(diveId, 1, boardType);
                testP = fdb.getDOD(diveId, 2, boardType);
                testT = fdb.getDOD(diveId, 3, boardType);
                testF = fdb.getDOD(diveId, 4, boardType);
                break;
            case 2:
                BackDatabase bdb = new BackDatabase();
                diveId = bdb.getDiveId(stringId);
                testS = bdb.getDOD(diveId, 1, boardType);
                testP = bdb.getDOD(diveId, 2, boardType);
                testT = bdb.getDOD(diveId, 3, boardType);
                testF = bdb.getDOD(diveId, 4, boardType);
                break;
            case 3:
                ReverseDatabase rdb = new ReverseDatabase();
                diveId = rdb.getDiveId(stringId);
                testS = rdb.getDOD(diveId, 1, boardType);
                testP = rdb.getDOD(diveId, 2, boardType);
                testT = rdb.getDOD(diveId, 3, boardType);
                testF = rdb.getDOD(diveId, 4, boardType);
                break;
            case 4:
                InwardDatabase idb = new InwardDatabase();
                diveId = idb.getDiveId(stringId);
                testS = idb.getDOD(diveId, 1, boardType);
                testP = idb.getDOD(diveId, 2, boardType);
                testT = idb.getDOD(diveId, 3, boardType);
                testF = idb.getDOD(diveId, 4, boardType);
                break;
            case 5:
                TwistDatabase tdb = new TwistDatabase();
                diveId = tdb.getDiveId(stringId);
                testS = tdb.getDOD(diveId, 1, boardType);
                testP = tdb.getDOD(diveId, 2, boardType);
                testT = tdb.getDOD(diveId, 3, boardType);
                testF = tdb.getDOD(diveId, 4, boardType);
                break;
            case 6:
                ForwardPlatformDatabase fpdb = new ForwardPlatformDatabase();
                diveId = fpdb.getDiveId(stringId);
                testS = fpdb.getDOD(diveId, 1, boardType);
                testP = fpdb.getDOD(diveId, 2, boardType);
                testT = fpdb.getDOD(diveId, 3, boardType);
                testF = fpdb.getDOD(diveId, 4, boardType);
                break;
            case 7:
                BackPlatformDatabase bpdb = new BackPlatformDatabase();
                diveId = bpdb.getDiveId(stringId);
                testS = bpdb.getDOD(diveId, 1, boardType);
                testP = bpdb.getDOD(diveId, 2, boardType);
                testT = bpdb.getDOD(diveId, 3, boardType);
                testF = bpdb.getDOD(diveId, 4, boardType);
                break;
            case 8:
                ReversePlatformDatabase rpdb = new ReversePlatformDatabase();
                diveId = rpdb.getDiveId(stringId);
                testS = rpdb.getDOD(diveId, 1, boardType);
                testP = rpdb.getDOD(diveId, 2, boardType);
                testT = rpdb.getDOD(diveId, 3, boardType);
                testF = rpdb.getDOD(diveId, 4, boardType);
                break;
            case 9:
                InwardPlatformDatabase ipdb = new InwardPlatformDatabase();
                diveId = ipdb.getDiveId(stringId);
                testS = ipdb.getDOD(diveId, 1, boardType);
                testP = ipdb.getDOD(diveId, 2, boardType);
                testT = ipdb.getDOD(diveId, 3, boardType);
                testF = ipdb.getDOD(diveId, 4, boardType);
                break;
            case 10:
                TwistPlatformDatabase tpdb = new TwistPlatformDatabase();
                diveId = tpdb.getDiveId(stringId);
                testS = tpdb.getDOD(diveId, 1, boardType);
                testP = tpdb.getDOD(diveId, 2, boardType);
                testT = tpdb.getDOD(diveId, 3, boardType);
                testF = tpdb.getDOD(diveId, 4, boardType);
                break;
            case 11:
                ArmstandPlatformDatabase apdb = new ArmstandPlatformDatabase();
                diveId = apdb.getDiveId(stringId);
                testS = apdb.getDOD(diveId, 1, boardType);
                testP = apdb.getDOD(diveId, 2, boardType);
                testT = apdb.getDOD(diveId, 3, boardType);
                testF = apdb.getDOD(diveId, 4, boardType);
                break;
        }

        ddList.add(0, testS);
        ddList.add(1, testP);
        ddList.add(2, testT);
        ddList.add(3, testF);

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
    
    public ArrayList<DiveStyleSpinner> GetDiveCat(int diveType, double boardType) {
        
        ArrayList<DiveStyleSpinner> searchDives = null;

        switch (diveType) {
            // Springboard Dives
            case 1:
                if (boardType == 1) {
                    GetForwardOneNames f1Name = new GetForwardOneNames();
                    searchDives = f1Name.doInBackground();
                    break;
                } else {
                    GetForwardThreeNames f3Name = new GetForwardThreeNames();
                    searchDives = f3Name.doInBackground();
                    break;
                }
            case 2:
                if (boardType == 1) {
                    GetBackOneNames b1Name = new GetBackOneNames();
                    searchDives = b1Name.doInBackground();
                    break;
                } else {
                    GetBackThreeNames b3Name = new GetBackThreeNames();
                    searchDives = b3Name.doInBackground();
                    break;
                }
            case 3:
                if (boardType == 1) {
                    GetReverseOneNames r1Name = new GetReverseOneNames();
                    searchDives = r1Name.doInBackground();
                    break;
                } else {
                    GetReverseThreeNames r3Name = new GetReverseThreeNames();
                    searchDives = r3Name.doInBackground();
                    break;
                }
            case 4:
                if (boardType == 1) {
                    GetInwardOneNames i1Name = new GetInwardOneNames();
                    searchDives = i1Name.doInBackground();
                    break;
                } else {
                    GetInwardThreeNames i3Name = new GetInwardThreeNames();
                    searchDives = i3Name.doInBackground();
                    break;
                }
            case 5:
                if (boardType == 1) {
                    GetTwistOneNames t1Name = new GetTwistOneNames();
                    searchDives = t1Name.doInBackground();
                    break;
                } else {
                    GetTwistThreeNames t3Name = new GetTwistThreeNames();
                    searchDives = t3Name.doInBackground();
                    break;
                }
                //platform dives
            case 6:
                if (boardType == 10) {
                    GetFrontTenNames f10 = new GetFrontTenNames();
                    searchDives = f10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetFrontSevenNames f7 = new GetFrontSevenNames();
                    searchDives = f7.doInBackground();
                    break;
                } else {
                    GetFrontFiveNames f5 = new GetFrontFiveNames();
                    searchDives = f5.doInBackground();
                    break;
                }
            case 7:
                if (boardType == 10) {
                    GetBackTenNames b10 = new GetBackTenNames();
                    searchDives = b10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetBackSevenNames b7 = new GetBackSevenNames();
                    searchDives = b7.doInBackground();
                    break;
                } else {
                    GetBackFiveNames b5 = new GetBackFiveNames();
                    searchDives = b5.doInBackground();
                    break;
                }
            case 8:
                if (boardType == 10) {
                    GetReverseTenNames r10 = new GetReverseTenNames();
                    searchDives = r10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetReverseSevenNames r7 = new GetReverseSevenNames();
                    searchDives = r7.doInBackground();
                    break;
                } else {
                    GetReverseFiveNames r5 = new GetReverseFiveNames();
                    searchDives = r5.doInBackground();
                    break;
                }
            case 9:
                if (boardType == 10) {
                    GetInwardTenNames ip10 = new GetInwardTenNames();
                    searchDives = ip10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetInwardSevenNames ip7 = new GetInwardSevenNames();
                    searchDives = ip7.doInBackground();
                    break;
                } else {
                    GetInwardFiveNames ip5 = new GetInwardFiveNames();
                    searchDives = ip5.doInBackground();
                    break;
                }
            case 10:
                if (boardType == 10) {
                    GetTwistTenNames tp10 = new GetTwistTenNames();
                    searchDives = tp10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetTwistSevenNames tp7 = new GetTwistSevenNames();
                    searchDives = tp7.doInBackground();
                    break;
                } else {
                    GetTwistFiveNames tp5 = new GetTwistFiveNames();
                    searchDives = tp5.doInBackground();
                    break;
                }
            case 11:
                if (boardType == 10) {
                    GetArmstandTenNames a10 = new GetArmstandTenNames();
                    searchDives = a10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetArmstandSevenNames a7 = new GetArmstandSevenNames();
                    searchDives = a7.doInBackground();
                    break;
                } else {
                    GetArmstandFiveNames a5 = new GetArmstandFiveNames();
                    searchDives = a5.doInBackground();
                    break;
                }
        }

        return searchDives;

    }


    // SpringBoard Dives
    private class GetForwardOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object> {
        ForwardDatabase db = new ForwardDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getForwardOneNames();
        }
    }

    private class GetForwardThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardDatabase db = new ForwardDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getForwardThreeNames();
        }
    }

    private class GetBackOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackDatabase db = new BackDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackOneNames();
        }
    }

    private class GetBackThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackDatabase db = new BackDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackThreeNames();
        }
    }

    private class GetReverseOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReverseDatabase db = new ReverseDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReverseOneNames();
        }
    }

    private class GetReverseThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReverseDatabase db = new ReverseDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReverseThreeNames();
        }
    }

    private class GetInwardOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardDatabase db = new InwardDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardOneNames();
        }
    }

    private class GetInwardThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardDatabase db = new InwardDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardThreeNames();
        }
    }

    private class GetTwistOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistDatabase db = new TwistDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistOneNames();
        }
    }

    private class GetTwistThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistDatabase db = new TwistDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistThreeNames();
        }
    }

    // Platform dives
    private class GetFrontTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardPlatformDatabase db = new ForwardPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getFrontPlatformTenNames();
        }
    }

    private class GetFrontSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardPlatformDatabase db = new ForwardPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getFrontPlatformSevenFiveNames();
        }
    }

    private class GetFrontFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardPlatformDatabase db = new ForwardPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getFrontPlatformFiveNames();
        }
    }

    private class GetBackTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackPlatformDatabase db = new BackPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackPlatformTenNames();
        }
    }

    private class GetBackSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackPlatformDatabase db = new BackPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackPlatformSevenFiveNames();
        }
    }
    private class GetBackFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackPlatformDatabase db = new BackPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackPlatformFiveNames();
        }
    }

    private class GetReverseTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReversePlatformDatabase db = new ReversePlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReversePlatformTenNames();
        }
    }

    private class GetReverseSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReversePlatformDatabase db = new ReversePlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReversePlatformSevenFiveNames();
        }
    }

    private class GetReverseFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReversePlatformDatabase db = new ReversePlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReversePlatformFiveNames();
        }
    }

    private class GetInwardTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardPlatformDatabase db = new InwardPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardPlatformTenNames();
        }
    }

    private class GetInwardSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardPlatformDatabase db = new InwardPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardPlatformSevenFiveNames();
        }
    }

    private class GetInwardFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardPlatformDatabase db = new InwardPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardPlatformFiveNames();
        }
    }

    private class GetTwistTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistPlatformDatabase db = new TwistPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistPlatformTenNames();
        }
    }

    private class GetTwistSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistPlatformDatabase db = new TwistPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistPlatformSevenFiveNames();
        }
    }

    private class GetTwistFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistPlatformDatabase db = new TwistPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistPlatformFiveNames();
        }
    }

    private class GetArmstandTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ArmstandPlatformDatabase db = new ArmstandPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getArmstandTenNames();
        }
    }

    private class GetArmstandSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ArmstandPlatformDatabase db = new ArmstandPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getArmstandSevenFiveNames();
        }
    }

    private class GetArmstandFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ArmstandPlatformDatabase db = new ArmstandPlatformDatabase();
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getArmstandFiveNames();
        }
    }
    
}
