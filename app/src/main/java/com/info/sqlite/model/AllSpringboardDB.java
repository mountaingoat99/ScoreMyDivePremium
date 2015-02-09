package com.info.sqlite.model;

public class AllSpringboardDB {

    private int id;
    private int oneMeter;
    private int threeMeter;
    private String name;
    private double oneA;
    private double oneB;
    private double oneC;
    private double oneD;
    private double threeA;
    private double threeB;
    private double threeC;
    private double threeD;

    public AllSpringboardDB(){}

    public AllSpringboardDB(int id, int oneMeter, int threeMeter, String name, double oneA,
                  double oneB, double oneC, double oneD, double threeA,
                  double threeB, double threeC, double threeD){
        this.id = id;
        this.oneMeter = oneMeter;
        this.threeMeter = threeMeter;
        this.name = name;
        this.oneA = oneA;
        this.oneB = oneB;
        this.oneC = oneC;
        this.oneD = oneD;
        this.threeA = threeA;
        this.threeB = threeB;
        this.threeC = threeC;
        this.threeD = threeD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOneMeter() {
        return oneMeter;
    }

    public void setOneMeter(int oneMeter) {
        this.oneMeter = oneMeter;
    }

    public int getThreeMeter() {
        return threeMeter;
    }

    public void setThreeMeter(int threeMeter) {
        this.threeMeter = threeMeter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOneA() {
        return oneA;
    }

    public void setOneA(double oneA) {
        this.oneA = oneA;
    }

    public double getOneB() {
        return oneB;
    }

    public void setOneB(double oneB) {
        this.oneB = oneB;
    }

    public double getOneC() {
        return oneC;
    }

    public void setOneC(double oneC) {
        this.oneC = oneC;
    }

    public double getOneD() {
        return oneD;
    }

    public void setOneD(double oneD) {
        this.oneD = oneD;
    }

    public double getThreeA() {
        return threeA;
    }

    public void setThreeA(double threeA) {
        this.threeA = threeA;
    }

    public double getThreeB() {
        return threeB;
    }

    public void setThreeB(double threeB) {
        this.threeB = threeB;
    }

    public double getThreeC() {
        return threeC;
    }

    public void setThreeC(double threeC) {
        this.threeC = threeC;
    }

    public double getThreeD() {
        return threeD;
    }

    public void setThreeD(double threeD) {
        this.threeD = threeD;
    }
}
