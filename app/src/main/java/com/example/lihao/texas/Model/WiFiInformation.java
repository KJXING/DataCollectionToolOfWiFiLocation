package com.example.lihao.texas.Model;

import io.realm.RealmObject;

public class WiFiInformation extends RealmObject {

    long timeStamp;

    double location_x;
    double location_y;

    String wifiInformation;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getLocation_x() {
        return location_x;
    }

    public void setLocation_x(double location_x) {
        this.location_x = location_x;
    }

    public double getLocation_y() {
        return location_y;
    }

    public void setLocation_y(double location_y) {
        this.location_y = location_y;
    }

    public String getWifiInformation() {
        return wifiInformation;
    }

    public void setWifiInformation(String wifiInformation) {
        this.wifiInformation = wifiInformation;
    }
}
