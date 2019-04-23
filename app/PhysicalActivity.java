package com.example.ledzi.application_v3.app;

public class PhysicalActivity {



    

    private int typeActivity;
    private double distanse;
    private double speed;
    private double kacl;
    private String duration;
    private int dateD;
    private int dateM;
    private int dateR;
    private int timeM;
    private int timeH;
    private int idInData;

    public PhysicalActivity(){}

    public PhysicalActivity(int typeActivity_, double distanse_, double speed, double kacl_, String duration_, int dateD_, int dateM_, int dateR_, int timeM_, int timeH_)
    {
        typeActivity=typeActivity_;
        distanse =distanse_;
        this.speed =speed;
        kacl=kacl_;
        duration=duration_;
        dateD =dateD_;
        dateM =dateM_;
        dateR =dateR_;
        timeM =timeM_;
        timeH =timeH_;
    }
    public PhysicalActivity(int typeActivity_, double distanse_, double speed, double kacl_, String duration_, int dateD_, int dateM_, int dateR_, int timeM_, int timeH_, int idInData_)
    {
        typeActivity=typeActivity_;
        distanse =distanse_;
        this.speed =speed;
        kacl=kacl_;
        duration=duration_;
        dateD =dateD_;
        dateM =dateM_;
        dateR =dateR_;
        timeM =timeM_;
        timeH =timeH_;
        idInData =idInData_;
    }


    public int getTypeActivity() { return typeActivity; }
    public double getDistanse() {
        return distanse;
    }
    public double getSpeed() {
        return speed;
    }
    public double getKacl() {
        return kacl;
    }
    public String getDuration() {
        return duration;
    }
    public int getDateD() { return dateD; }
    public int getDateM() { return dateM; }
    public int getDateR() { return dateR; }
    public int getTimeM() { return timeM; }
    public int getTimeH() { return timeH; }
    public int getIdInData() { return idInData; }

    public void setDistanse(double distanse) {
        this.distanse = distanse;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public void setKacl(double kacl) {
        this.kacl = kacl;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public void setDateD(int dateD) { this.dateD = dateD; }
    public void setDateM(int dateM) { this.dateM = dateM; }
    public void setDateR(int dateR) { this.dateR = dateR; }
    public void setTimeM(int timeM) { this.timeM = timeM; }
    public void setTimeH(int timeH) { this.timeH = timeH; }
    public void setTypeActivity(int typeActivity) { this.typeActivity = typeActivity; }
    public void setIdInData(int idInData) { this.idInData = idInData; }
}
