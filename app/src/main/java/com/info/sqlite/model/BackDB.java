package com.info.sqlite.model;

public class BackDB {
	private int id;
    private int oneMeter;
    private int threeMeter;
	private String diveName;
	private double oneA;
	private double oneB;
	private double oneC;
    private double oneD;
	private double threeA;
	private double threeB;
	private double threeC;
	private double threeD;
	
	public BackDB(){}
	
	public BackDB(int id, int oneMeter, int threeMeter, String diveName, double oneA,
			double oneB, double oneC, double oneD, double threeA,
			double threeB, double threeC, double threeD){
		this.id = id;
        this.oneMeter = oneMeter;
        this.threeMeter = threeMeter;
		this.diveName = diveName;
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
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDiveName() {
		return this.diveName;
	}

	public String setDiveName(String diveName) {
		return this.diveName = diveName;
	}
	
	public void setOneA(double oneA) {
		this.oneA = oneA;
	}

	public double getOneA() {
		return this.oneA;
	}
	
	public void setOneB(double oneB) {
		this.oneB = oneB;
	}
	
	public double getOneB() {
		return this.oneB;
	}

	public double getOneC() {
		return this.oneC;
	}

	public void setOneC(double oneC) {
		this.oneC = oneC;
	}

	public double getOneD() {
		return this.oneD;
	}

	public void setOneD(double oneD) {
		this.oneD = oneD;
	}

	public double getThreeA() {
		return this.threeA;
	}

	public void setThreeA(double threeA) {
		this.threeA = threeA;
	}

	public double getThreeB() {
		return this.threeB;
	}

	public void setThreeB(double threeB) {
		this.threeB = threeB;
	}

	public double getThreeC() {
		return this.threeC;
	}

	public void setThreeC(double threeC) {
		this.threeC = threeC;
	}

	public double getThreeD() {
		return this.threeD;
	}

	public void setThreeD(double threeD) {
		this.threeD = threeD;
	}

    public int getOneMeter() { return oneMeter; }

    public void setOneMeter(int oneMeter) { this.oneMeter = oneMeter; }

    public int getThreeMeter() { return threeMeter; }

    public void setThreeMeter(int threeMeter) { this.threeMeter = threeMeter; }
}
